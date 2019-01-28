package controllers.elisisplay.attendance.observator;

import controllers.elisisplay.attendance.objects.ActionOnPage;
import controllers.elisisplay.attendance.objects.Page;
import controllers.elisisplay.attendance.serials.ActionsOnPages;
import play.mvc.Http;

import java.util.ArrayList;

/**
 * Created by Bienvenu on 30/10/2018 in schoolar_dev.
 */
public class ActionObservator extends Observator<ActionOnPage> {

    /**
     * Espacement d'enregistrement des actions sur la plateforme.
     */
    private int recordActionsInterval;

    /**
     * Indique si toutes les actions effectuées sur la plateforme sont enregistrées.
     * <p>
     * Est égale à {@code true} si {@code recordActionsInterval = 1}
     */
    private boolean permantlyRecordAction = false;

    /**
     * Indique suivant la valeur de {@code recordActionsInterval},
     * la nième action non enregistré pour un utilisateur identifié.
     */
    private int actionRecordedNb = 0;

    public ActionObservator(int recordActionsInterval) {
        serial = new ActionsOnPages(false);
        if (recordActionsInterval >= 0) {
            this.recordActionsInterval = recordActionsInterval;
            if (recordActionsInterval == 1)
                permantlyRecordAction = true;
        }
    }

    public ArrayList<ActionOnPage> all() {
        return serial.all();
    }

    public void record(Http.Request request, Http.Response response, Page actualPage, Page requestPage) {
        serial.add(new ActionOnPage(request, response, actualPage, requestPage));
    }

    public void observe(Http.Request request, Http.Response response, Page actualPage, Page requestPage) {
        incrIntActions();
        if (actionsAreObservable() && observeThisAction())
            record(request, response, actualPage, requestPage);
    }

    /**
     * Indique si l'observator est actif.
     * @return {@code true} si l'observator observe les actions,
     * {@code false} dans le cas contraire.
     */
    private boolean actionsAreObservable() {
        return permantlyRecordAction || recordActionsInterval > 0;
    }

    /**
     * Incrémente la variable recensant les actions enregistrées par l'observator.
     */
    private void incrIntActions() {
        ++actionRecordedNb;
    }

    /**
     * Est appellé lorsqu'une action s'est produite pour savoir si cette action doit être enregistré.
     * //TODO : Doit être unique pour chaque utilisateur.
     * @return True if this action can be observe.
     */
    private boolean observeThisAction() {
        return (actionRecordedNb % recordActionsInterval == 0);
    }


}
