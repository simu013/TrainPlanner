/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;

/**
 *
 * @author Simon
 */
public class FahrtenHelper {

    public void planeFahrt(Zugtyp zugTyp, LocalTime startZeit, int zugNr, Fahrplan fahrplan) {
        LocalTime verfuegbareStartZeit = findeStartZeit(startZeit, fahrplan);

    }

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

    private LocalTime findeStartZeitOhneVorherigeFahrt(Fahrt folgendeFahrt, LocalTime verfuegbareStartZeit) {
        if (!verfuegbareStartZeit.isAfter(folgendeFahrt.getEndZeit().minusMinutes(Config.getIntProperty("SicherheitsabstandsZeit")).minusMinutes(Config.getIntProperty("GueterzugDauer")))) {
            return verfuegbareStartZeit;
        }
        return folgendeFahrt.getEndZeit().minusMinutes(Config.getIntProperty("SicherheitsabstandsZeit")).minusMinutes(Config.getIntProperty("GueterzugDauer"));
    }

    private LocalTime findeStartZeitOhneFolgendeFahrt(Fahrt vorherigeFahrt, LocalTime verfuegbareStartZeit) {
        if (!verfuegbareStartZeit.isAfter(vorherigeFahrt.getStartZeit().plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit")))) {
            return verfuegbareStartZeit;
        }
        return vorherigeFahrt.getStartZeit().plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"));
    }

    private boolean lueckeVorhanden(Fahrt vorherigeFahrt, Fahrt folgendeFahrt) {
        LocalTime fruehesteAnkunft = vorherigeFahrt.getStartZeit().plusMinutes((Config.getIntProperty("SicherheitsabstandsZeit") * 2))
                .plusMinutes(Config.getIntProperty("GueterzugDauer"));
        return !fruehesteAnkunft.isAfter(folgendeFahrt.getEndZeit());
    }

    private Fahrt getVorherigeFahrt(Fahrplan fahrplan, LocalTime startZeit) {
        Fahrt fahrt = null;
        for (Fahrt f : fahrplan.getFahrtenByZugTyp(Zugtyp.PERSONENZUG)) {
            if (startZeit.isAfter(f.getStartZeit())) {
                if (((fahrt == null) || fahrt.getStartZeit().isAfter(f.getStartZeit()))) {
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
                if (((fahrt == null) || fahrt.getStartZeit().isBefore(f.getStartZeit()))) {
                    fahrt = f;
                }
            }
        }
        return fahrt;
    }

    private boolean istMoeglicheAnkunftszeit(LocalTime endZeit, Fahrt folgendeFahrt) {
        return (endZeit.plusMinutes(Config.getIntProperty("SicherheitsabstandsZeit")).compareTo(folgendeFahrt.getEndZeit()) <= 0);
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
                temp.minusMinutes(Config.getIntProperty("SicherheitsabstandsZeit"));
            } while (temp.isBefore(startZeit));
        }
        return besteZeit;
    }
}
