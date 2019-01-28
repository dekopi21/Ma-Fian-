package controllers.elisisplay.attendance.objects;

import controllers.elisisplay.utils.DateUtils;
import play.mvc.Http;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Action effectuée sur une page.
 */
public class ActionOnPage implements Serializable {

    private static final long serialVersionUID = 1000000000000000001L;

    /**
     * Code de la page.
     */
    public String pageCode;

    /**
     * Action on page Code.
     */
    public String code;

    /**
     * Type de contenu demandé.
     */
    public String contentRequested;

    /**
     * Type de méthode Http (POST, GET) utilisée par l'action.
     */
    public String httpMethod;

    /**
     * Chemin allant vers la méthode correspondant à l'action effectué
     * dans l'architecture Java.
     */
    public String methodJavaPath;

    /**
     * Signature complete de la méthode Java appellé par l'action.
     */
    public String methodeSignature;

    /**
     * Classe controlleur devant effectué l'action.
     */
    public String controllerName;

    /**
     * Lorsque l'action est une requête, contient le contenu de cette requête.
     */
    public String query;

    /**
     * Inquique l'adresse Ip de l'utilisateur.
     */
    public String clientRemoteAdress;

    /**
     * Indique si c'est la première action de l'utilisateur sur la plateforme.
     *
     * Si c'est le cas certaines opérations (enregistrer en tant que nouveau
     * visteur...) doivent être effectuées.
     */
    public boolean firstAction;

    /**
     * Indique si l'action est sécurisé (cryptage HTTPS).
     */
    public boolean isSecure;

    /**
     * Type d'action effectuée.
     */
    public TypeActionOnPage typeAction;

    /**
     * Impact de l'action effectué.
     */
    public ActionOnPageImpact actionImpact;

    /**
     * Date à laquelle l'action a été effectué.
     */
    public Calendar performedAt;

    /**
     * Description de l'action.
     */
    public String actionDescription;

    /**
     * Lien utilisé pour effectuer l'action.
     */
    public String relativeUrl;

    /**
     * Page demandée après avoir effectuée l'action.
     */
    public String pageRequested;

    /**
     * Page actuelle sur l'action a été effectuée.
     */
    public String pageActual;

    /**
     * Nom de la méthode Java appellée.
     */
    public String actionMethodName;

    public String reponseStatus;

    public ActionOnPage(Http.Request request, Http.Response response, Page pageActual, Page pageRequested) {
        this.contentRequested = request.contentType;
        this.httpMethod = request.method;
        this.methodJavaPath = request.path;
        this.methodeSignature = request.invokedMethod.toString();
        this.controllerName = request.controller;
        this.query = request.querystring;
        this.clientRemoteAdress = request.remoteAddress;
        this.isSecure = request.secure;
        //Cause NullPointerException
        //this.actionImpact = typeAction.impact();
        this.performedAt = DateUtils.convertToCalendar(request.date);
        if (pageActual != null){
            this.actionDescription = pageActual.description;
            this.relativeUrl = pageActual.url;
            this.pageActual = pageActual.name;
            if (pageRequested == null){
                this.pageRequested = pageActual.code;
            }
        }
        if (pageRequested != null){
            this.pageRequested = pageRequested.code;
        }
        this.reponseStatus = response.status.toString();
        this.actionMethodName = request.action;
    }
}
