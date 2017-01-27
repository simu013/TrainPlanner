/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Simon
 */
public class DisponentHelper {

    private FahrtenHelper fahrtenHelper = new FahrtenHelper();

    /**
     * Plant einen Auftrag auf einem Zug ein
     *
     * @param auftrag Zu planender Auftrag
     * @param fahrplan Fahrplan in dem sich der Zug befindet dem ein Auftrag
     * eingeplant werden soll
     * @return true wenn Planung erfolgreich, false wenn Planung nicht möglich
     */
    public boolean planeAuftrag(Auftrag auftrag, Fahrplan fahrplan) {
        Fahrt fahrt = findeFahrt(auftrag, fahrplan);
        if (fahrt == null) {
            return false;
        }
        auftrag.setZugNr(fahrt.getZugNr());
        erstelleContainer(auftrag);
        verladeContainer(auftrag, fahrt.getGueterzug());
        return true;
    }

    /**
     * Fügt dem Auftrag alle benötigten Container hinzu
     *
     * @param auftrag Auftrag dem die Container zugefügt werden sollen
     */
    private void erstelleContainer(Auftrag auftrag) {
        for (int i = 0; i < auftrag.getAnzahlContainer(); i++) {
            auftrag.addContainer(new Container());
        }
    }
/**
 * Container werden auf Gèterzug verladen
 * @param auftrag Auftrag dessen Container verladen werden sollen
 * @param gueterzug Gèterzug auf den die Container verladen werden sollen. 
 */
    private void verladeContainer(Auftrag auftrag, Gueterzug gueterzug) {
        Iterator iterator = auftrag.getContainers().iterator();
        while (iterator.hasNext()) {
            for (int i = 0; i < auftrag.getAnzahlContainer(); i++) {
                for (Gueterwagon gueterwagon : gueterzug.getGueterwagons()) {
                    while(getMoeglicheContainerFuerGueterwagen(gueterwagon) > 0) {
                        if(iterator.hasNext()){
                            gueterwagon.addContainer((Container) iterator.next());
                        } else {
                            return;
                        }
                    }
                }
                if (iterator.hasNext()) {
                    gueterzug.addGueterwagon(new Gueterwagon());
                }
            }
        }
    }

    /**
     * Prüft ob ein Zug Platz hat den Auftrag aufzunehmen.
     * @param fahrplan Fahrplan in dem ein Zug gesucht werden soll
     * @param auftrag Auftrag der an einen Zug übergeben werden soll
     * @param startZeit Früheste Abfahrtszeit des Zuges
     * @return true wenn Platz vorhanden, false wenn kein Zug Platz für den
     * Auftrag hat.
     */
    private boolean zugHatPlatzfuerAuftrag(Fahrplan fahrplan, Auftrag auftrag, LocalTime startZeit) {
        return auftrag.getAnzahlContainer() <= getAnzahlFreieContainerPlaetze(getFahrtbeiStartzeit(startZeit, fahrplan).getGueterzug());
    }

    /**
     * Sucht einen Zug welcher die Güter des Auftrags aufnehmen kann
     *
     * @param auftrag Erwartet einen Auftrag für den ein Zug gesucht werden soll
     * @param fahrplan Erwartet den Fahrplan in dem der Zug fahren soll
     * @return Gibt den Zug zurück welcher den Auftrag aufnehmen kann
     */
    private Fahrt findeFahrt(Auftrag auftrag, Fahrplan fahrplan) {
        LocalTime startZeit = fahrtenHelper.planeFahrt(auftrag.getStartZeit(), fahrplan);
        while (!zugHatPlatzfuerAuftrag(fahrplan, auftrag, startZeit)) {
            if (startZeit.plusMinutes(1).equals(LocalTime.MIN)) {
                return null;
            }
            startZeit = fahrtenHelper.planeFahrt(startZeit.plusMinutes(1), fahrplan);
        }
        return getFahrtbeiStartzeit(startZeit, fahrplan);
    }

    /**
     * Sucht eine bestehende Fahrt mit angegebener Abfahrtszeit.
     *
     * @param startZeit Gewünschte Abfahrtszeit
     * @param zugtyp Typ des Zuges
     * @param fahrplan Fahrplan welcher nach Fahrt mit StartZeit dursucht werden
     * soll.
     * @return Fahrt wenn zur genannten Startzeit eine bereits eine Fahrt
     * eingeplant ist, null wenn diese Bedingung nicht erfüllt ist.
     */
    private Fahrt getFahrtbeiStartzeit(LocalTime startZeit, Fahrplan fahrplan) {
        for (Fahrt fahrt : fahrplan.getFahrtenByZugTyp(Zugtyp.GUETERZUG)) {
            if (fahrt.getStartZeit().equals(startZeit)) {
                return fahrt;
            }
        }
        Fahrt fahrt = new Fahrt(new Gueterzug(), Zugtyp.GUETERZUG, startZeit, startZeit.plusMinutes(Config.getIntProperty("GueterzugDauer")), fahrplan.getZugNr());
        fahrplan.addFahrt(fahrt);
        return fahrt;
    }

    /**
     * Ermittelt die noch verfügbare Restlänge und -gewicht einer
     * Transporteinheit
     *
     * @param parentUnit
     * @param childUnit
     * @return Gibt ein Array mit der Restlänge und Restgewicht
     */
    private float[] getVerfuegbareLaengeGewicht(Transporteinheit parentUnit, List<Transporteinheit> childUnit) {
        float verfuegbareLaenge = parentUnit.getLaenge();
        float verfuegbaresGewicht = parentUnit.getMaxLadung();

        for (Transporteinheit child : childUnit) {
            verfuegbareLaenge -= child.getLaenge();
            verfuegbaresGewicht -= child.getMaxGesamtGewicht();
        }
        return new float[]{verfuegbareLaenge, verfuegbaresGewicht};
    }

    /**
     * Liefert die mögliche Anzahl restlicher Transporteinheiten welche aufgrund
     * von Länge und Ladung noch platziert werden können
     *
     * @param parentUnit Transporteinheit welche auf mögliche weitere Einheiten
     * geprüft werden soll.
     * @param childUnit Liste aus Transporteinheiten welche das parentUNit
     * bereits enthält.
     * @param laenge Länge der childUnits
     * @param ladung Gewicht der childUnits
     * @return Anzahl möglicher Transporteinheiten
     */
    private int getMoeglicheRestlicheEinheiten(Transporteinheit parentUnit, List<Transporteinheit> childUnit, float laenge, float ladung) {
        float[] rest = getVerfuegbareLaengeGewicht(parentUnit, childUnit);
        int anzahlNachLaenge = (int) (rest[0] / laenge);
        int anzahlNachGewicht = (int) (rest[1] / ladung);

        if (anzahlNachLaenge <= anzahlNachGewicht) {
            return anzahlNachLaenge;
        }
        return anzahlNachGewicht;
    }

    /**
     * Gibt die Anzahl möglicher Güterwagen für den übergebenen Gueterzug an
     *
     * @param gueterzug
     * @return Anzahl möglicher Güterwagen
     */
    private int getMoeglicheGueterwagenFuerZug(Gueterzug gueterzug) {
        return getMoeglicheRestlicheEinheiten(gueterzug, new ArrayList<>(gueterzug.getGueterwagons()),
                Config.getFloatProperty("WagonLaenge"), Config.getFloatProperty("WagonMaxLadung") + Config.getFloatProperty("WagonLeerGewicht"));
    }

    /**
     * Gibt die mögliche Anzahl Container für einen Güterwagen zurück
     *
     * @param gueterwagen
     * @return Anzahl möglicher Container
     */
    private int getMoeglicheContainerFuerGueterwagen(Gueterwagon gueterwagen) {
        return getMoeglicheRestlicheEinheiten(gueterwagen, new ArrayList<>(gueterwagen.getContainers()), Config.getFloatProperty("ContainerLaenge"),
                (Config.getFloatProperty("ContainerMaxLadung") + Config.getFloatProperty("ContainerLeerGewicht")));
    }

    /**
     * Gibt zurück wieviele Container auf dem Güterzug platziert werden können.
     *
     * @param gueterzug
     * @return Anzahl möglicher Container.
     */
    private int getAnzahlFreieContainerPlaetze(Gueterzug gueterzug) {
        int plaetze = 0;
        int plaetzeDurchAnhaengen = (getMoeglicheGueterwagenFuerZug(gueterzug) * getMoeglicheContainerFuerGueterwagen(new Gueterwagon()));

        for (Gueterwagon gueterwagon : gueterzug.getGueterwagons()) {
            plaetze += getMoeglicheContainerFuerGueterwagen(gueterwagon);
        }
        return plaetze + plaetzeDurchAnhaengen;
    }

}
