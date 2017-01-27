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
                    return getBesteAbfahrtszeit(folgendeFahrt.getEndZeit(), verfuegbareStartZeit);
                } else {
                    verfuegbareStartZeit = folgendeFahrt.getStartZeit().plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"));
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
        if (!verfuegbareStartZeit.isAfter(folgendeFahrt.getEndZeit().minusMinutes(Config.getIntProperty("SicherheitsabstandsZeit")).minusMinutes(Config.getIntProperty("GueterzugDauer")))) {
            return verfuegbareStartZeit;
        }
        return folgendeFahrt.getEndZeit().minusMinutes(Config.getIntProperty("SicherheitsabstandsZeit")).minusMinutes(Config.getIntProperty("GueterzugDauer"));
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
        if (!verfuegbareStartZeit.isAfter(vorherigeFahrt.getStartZeit().plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit")))) {
            return verfuegbareStartZeit;
        }
        return vorherigeFahrt.getStartZeit().plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"));
    }

    /**
     * Ermittelt ob eine Lücke zwischen zwei Fahrten / Zügen vorhanden ist.
     *
     * @param vorherigeFahrt Nächste vorausgehende, bereits eingeplante Fahrt
     * @param folgendeFahrt Nächste nachfolgende, bereits eingeplante Fahrt
     * @return Gibt die nächste vorangehende Fahrt zurück.
     */
    private boolean lueckeVorhanden(Fahrt vorherigeFahrt, Fahrt folgendeFahrt) {
        LocalTime fruehesteAnkunft = vorherigeFahrt.getStartZeit().plusMinutes((Config.getIntProperty("SicherheitsabstandsZeit") * 2))
                .plusMinutes(Config.getIntProperty("GueterzugDauer"));
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
            if (startZeit.isAfter(f.getStartZeit())) {
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
        return (startZeit.plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"))
                .plusMinutes(Config.getIntProperty("GueterzugDauer")).compareTo(folgendeFahrt.getEndZeit()) <= 0);
    }

    private LocalTime getBesteAbfahrtszeit(LocalTime referenzZeit, LocalTime startZeit) {
        LocalTime besteZeit = referenzZeit;
        if (referenzZeit.isBefore(startZeit)) {
            while (besteZeit.isBefore(startZeit)) {
                besteZeit.plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"));
            }
        } else {
            LocalTime temp = referenzZeit.minusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"))
                    .minusMinutes(Config.getIntProperty("GueterzugDauer"));
            do {
                besteZeit = temp;
                temp = temp.minusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"));
            } while (startZeit.isBefore(temp));
        }
        return besteZeit;
    }
}
