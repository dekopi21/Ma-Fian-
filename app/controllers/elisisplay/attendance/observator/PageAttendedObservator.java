package controllers.elisisplay.attendance.observator;

import controllers.elisisplay.attendance.objects.PageAttended;
import controllers.elisisplay.attendance.serials.PagesAttended;
import play.mvc.Http;

/**
 * Created by Bienvenu on 30/10/2018 in schoolar_dev.
 */
public class PageAttendedObservator extends Observator<PageAttended> {

    /**
     * Espacement d'enregistrement des visites sur la plateforme.
     */
    private int recordAttendsInterval;

    /**
     * Indique suivant la valeur de {@code recordAttendsInterval},
     * la nième visite non enregistré pour un utilisateur identifié.
     */
    private int pagesAttendedRecordedNb = 0;

    /**
     * Indique si toutes les visites effectuées sur la plateforme sont enregistrées.
     * <p>
     * Est égale à {@code true} si {@code recordAttendsInterval = 1}
     */
    private boolean permantlyRecordAttend = false;

    public PageAttendedObservator(int recordAttendsInterval) {
        serial = new PagesAttended(false);
        if (recordAttendsInterval >= 0) {
            this.recordAttendsInterval = recordAttendsInterval;
            if (recordAttendsInterval == 1)
                permantlyRecordAttend = true;
        }
    }

    private boolean attendsAreObservable() {
        return permantlyRecordAttend || recordAttendsInterval > 0;
    }

    private void incrIntAttends() {
        ++pagesAttendedRecordedNb;
    }

    private boolean observeThisAttend() {
        return (pagesAttendedRecordedNb % recordAttendsInterval == 0);
    }

    public void observe(Http.Request request) {
        incrIntAttends();
        if (attendsAreObservable() && observeThisAttend())
            record(request);
    }

    public void record(Http.Request request) {
        serial.add(new PageAttended(request));
    }
}
