/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;

/**
 * Errechnet mögliche Startzeit für Güterzüge. Dient dem Disponent als Helper
 * zur Einplanung von Güterzügen.
 *
 * @author Simon
 */
public class FahrtenHelper {

    /**
     * Plant eine Fahrt für einen Güterzug.
     *
     * @param startZeit Abfahrtszeit des Güterzuges
     * @param fahrplan Fahrplan mit bereits erfassten Fahrten
     */
    public LocalTime planeFahrt(LocalTime startZeit, Fahrplan fahrplan) {
        LocalTime verfuegbareStartZeit;
        do {
            verfuegbareStartZeit = findeStartZeit(startZeit, fahrplan);
            if(verfuegbareStartZeit == null){
                return null;
            }
            if (verfuegbareStartZeit.isBefore(startZeit)) {
                startZeit = startZeit.plusMinutes(1);
            }
        } while (verfuegbareStartZeit.isBefore(startZeit));
        return verfuegbareStartZeit;
    }

    /**
     * Sucht eine mögliche Startzeit
     *
     * @param startZeit Abfahrtszeit des Güterzuges.
     * @param fahrplan Fahrplan mit allen bereits erfassten Fahrten.
     * @return
     */
    private LocalTime findeStartZeit(LocalTime startZeit, Fahrplan fahrplan) {
        LocalTime verfuegbareStartZeit = startZeit;
        while (true) {
            Fahrt vorherigeFahrt = getVorherigeFahrt(fahrplan, verfuegbareStartZeit);
            Fahrt folgendeFahrt = getFolgendeFahrt(fahrplan, verfuegbareStartZeit);
            if (vorherigeFahrt != null && folgendeFahrt != null) {
                if (lueckeVorhanden(vorherigeFahrt, folgendeFahrt) && istMoeglicheAnkunftszeit(startZeit, folgendeFahrt)) {
                    LocalTime tempstartzeit = getBesteAbfahrtszeit(folgendeFahrt.getEndZeit(), verfuegbareStartZeit);
                    if(istMoeglicheAbfahrtszeit(tempstartzeit, vorherigeFahrt)){
                        return tempstartzeit;
                    }else{
                        return tempstartzeit.plusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"));
                    }
                } else {
                    verfuegbareStartZeit = folgendeFahrt.getStartZeit().plusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"));
                }
            }
            if (vorherigeFahrt == null && folgendeFahrt == null) {
                return getBesteAbfahrtszeit(LocalTime.of(23, 59), verfuegbareStartZeit);
            }
            if (vorherigeFahrt == null && folgendeFahrt != null) {
                return getBesteAbfahrtszeit(folgendeFahrt.getEndZeit(), findeStartZeitOhneVorherigeFahrt(folgendeFahrt, verfuegbareStartZeit));
            }
            if (vorherigeFahrt != null && folgendeFahrt == null) {
                return getBesteAbfahrtszeit(vorherigeFahrt.getStartZeit(), findeStartZeitOhneFolgendeFahrt(vorherigeFahrt, verfuegbareStartZeit));
            }
        }
    }

    /**
     * Errechnet eine mögliche Startzeit für Güterzüge denen keine unmittelbare
     * Fahrt vorausgeht.
     *
     * @param folgendeFahrt Nächste nachfolgende, bereits eingeplante Fahrt
     * @param verfuegbareStartZeit Nächstgelegene mögliche Abfahrtszeit
     * @return Gibt die nahegelegenste Fahrt zurück.
     */
    private LocalTime findeStartZeitOhneVorherigeFahrt(Fahrt folgendeFahrt, LocalTime verfuegbareStartZeit) {
        if (!verfuegbareStartZeit.isAfter(folgendeFahrt.getEndZeit().minusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit")).minusMinutes(Einstellungen.getIntProperty("GueterzugDauer")))) {
            return verfuegbareStartZeit;
        }
        return folgendeFahrt.getEndZeit().minusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit")).minusMinutes(Einstellungen.getIntProperty("GueterzugDauer"));
    }

    /**
     * Errechnet eine mögliche Startzeit für Güterzüge denen keine unmittelbare
     * Fahrt folgt.
     *
     * @param vorherigeFahrt
     * @param verfuegbareStartZeit
     * @return
     */
    private LocalTime findeStartZeitOhneFolgendeFahrt(Fahrt vorherigeFahrt, LocalTime verfuegbareStartZeit) {
        if (verfuegbareStartZeit.isAfter(vorherigeFahrt.getStartZeit().plusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit")))) {
            return verfuegbareStartZeit;
        }
        return vorherigeFahrt.getStartZeit().plusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"));
    }

    /**
     * Ermittelt ob eine Lücke zwischen zwei Fahrten / Zügen vorhanden ist.
     *
     * @param vorherigeFahrt Nächste vorausgehende, bereits eingeplante Fahrt
     * @param folgendeFahrt Nächste nachfolgende, bereits eingeplante Fahrt
     * @return Gibt die nächste vorangehende Fahrt zurück.
     */
    private boolean lueckeVorhanden(Fahrt vorherigeFahrt, Fahrt folgendeFahrt) {
        LocalTime fruehesteAnkunft = vorherigeFahrt.getStartZeit().plusMinutes((Einstellungen.getIntProperty("SicherheitsabstandsZeit") * 2))
                .plusMinutes(Einstellungen.getIntProperty("GueterzugDauer"));
        return !fruehesteAnkunft.isAfter(folgendeFahrt.getEndZeit());
    }

    /**
     *
     * @param fahrplan
     * @param startZeit
     * @return
     */
    private Fahrt getVorherigeFahrt(Fahrplan fahrplan, LocalTime startZeit) {
        Fahrt fahrt = null;

        for (Fahrt f : fahrplan.getFahrtenByZugTyp(Zugtyp.PERSONENZUG)) {
            if (!startZeit.isBefore(f.getStartZeit())) {
                if (((fahrt == null) || f.getStartZeit().isAfter(fahrt.getStartZeit()))) {
                    fahrt = f;
                }
            }
        }
        return fahrt;
    }

    private Fahrt getFolgendeFahrt(Fahrplan fahrplan, LocalTime startZeit) {
        Fahrt fahrt = null;
        for (Fahrt f : fahrplan.getFahrtenByZugTyp(Zugtyp.PERSONENZUG)) {
            if (startZeit.isBefore(f.getStartZeit())) {
                if (((fahrt == null) || f.getStartZeit().isBefore(fahrt.getStartZeit()))) {
                    fahrt = f;
                }
            }
        }
        return fahrt;
    }

    private boolean istMoeglicheAnkunftszeit(LocalTime startZeit, Fahrt folgendeFahrt) {
        return (startZeit.plusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"))
                .plusMinutes(Einstellungen.getIntProperty("GueterzugDauer")).compareTo(folgendeFahrt.getEndZeit()) <= 0);
    }

    private boolean istMoeglicheAbfahrtszeit(LocalTime startZeit, Fahrt vorherigeFahrt) {
        return (startZeit.minusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"))
                .compareTo(vorherigeFahrt.getStartZeit()) >= 0);
    }
    
    private LocalTime getBesteAbfahrtszeit(LocalTime referenzZeit, LocalTime startZeit) {
        LocalTime besteZeit = referenzZeit;
        if (referenzZeit.isBefore(startZeit)) {
            while (besteZeit.isBefore(startZeit)) {
                if(spaetereFahrtNichtMoeglich(besteZeit)){
                    return null;
                }
                besteZeit = besteZeit.plusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"));
            }
        } else {
            LocalTime temp = referenzZeit.minusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"))
                    .minusMinutes(Einstellungen.getIntProperty("GueterzugDauer"));
            do {
                besteZeit = temp;
                if(fruehereFahrtNichtMoeglich(besteZeit)){
                    return besteZeit;
                }
                temp = temp.minusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit"));
            } while (!startZeit.isAfter(temp));
        }
        return besteZeit;
    }
    
    private boolean spaetereFahrtNichtMoeglich(LocalTime fahrzeit){
        return !fahrzeit.isBefore(LocalTime.MIDNIGHT.minusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit")));
    }
    
    private boolean fruehereFahrtNichtMoeglich(LocalTime fahrtzeit){
        return fahrtzeit.isBefore(LocalTime.MIDNIGHT.plusMinutes(Einstellungen.getIntProperty("SicherheitsabstandsZeit")));
    }
}
