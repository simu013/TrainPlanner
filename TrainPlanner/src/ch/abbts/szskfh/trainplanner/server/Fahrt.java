/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;

/**
 * Stellt eine Zugfahrt zur Verfügung.
 *
 * @author Florian Haeusermann
 */
public class Fahrt {

    private Gueterzug gueterzug;
    private LocalTime startZeit;
    private LocalTime endZeit;
    private StatusEnum status;
    private ZugtypEnum zugTyp;
    private int zugNr;

    /**
     * Stellt eine Zugfahrt zurVerfügung. Wird zur Zeit für Personenzüge
     * verwendet.
     *
     * @param zugTyp Zugtyp gemäss Typenvorgabe
     * @param startZeit Abfahrtszeit
     * @param endZeit Ankunftszeit
     * @param zugNr Zug Nummer
     */
    public Fahrt(ZugtypEnum zugTyp, LocalTime startZeit, LocalTime endZeit, int zugNr) {
        this.zugTyp = zugTyp;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        this.zugNr = zugNr;
    }

    /**
     * Stellt eine Fahrt zur Verfügung. Wird zur Zeit für Güterzüge verwendet.
     *
     * @param gueterzug Güterzug Objekt
     * @param zugTyp Zugtyp gemäss Typenvorgabe
     * @param startZeit Abfahrtszeit
     * @param endZeit Ankunftszeit
     * @param zugNr Zug Nummer
     */
    public Fahrt(Gueterzug gueterzug, ZugtypEnum zugTyp, LocalTime startZeit, LocalTime endZeit, int zugNr) {
        this.zugTyp = zugTyp;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        this.zugNr = zugNr;
        this.gueterzug = gueterzug;
    }

    /**
     * Gibt den aktuellen Zugstatus zurück.
     *
     * @return StatusEnum Zugstatus
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * Setzt den Zugstatus.
     *
     * @param status StatusEnum Zugstatus
     */
    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    /**
     * Gibt das Güterzug Objekt der Fahrt zurück.
     *
     * @return Güterzug Objekt. Gibt null zurück wenn Fahrt keine Güterzugfahrt
     * ist.
     */
    public Gueterzug getGueterzug() {
        return gueterzug;
    }

    /**
     * Gibt die Abfahrtszeit der Fahrt zurück.
     *
     * @return LocalTime Abfahrtszeit im Format HH:mm
     */
    public LocalTime getStartZeit() {
        return startZeit;
    }

    /**
     * Gibt die Ankunftszeit der Fahrt zurück.
     *
     * @return LocalTime Ankunftszeit im Format HH:mm
     */
    public LocalTime getEndZeit() {
        return endZeit;
    }

    /**
     * Gibt den ZugtypEnum der Fahrt zurück.
     *
     * @return Enum ZugtypEnum (PERSONENZUG, GUETERZUG)
     */
    public ZugtypEnum getZugtyp() {
        return zugTyp;
    }

    /**
     * Gibt die Zug Nummer zurück
     *
     * @return int Zug Nummer.
     */
    public int getZugNr() {
        return zugNr;
    }

}
