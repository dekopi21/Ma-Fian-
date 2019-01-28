package controllers.elisisplay.attendance.objects;

import controllers.elisisplay.crypto.Crypto;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.secure.ApplicationSection;
import controllers.elisisplay.secure.UserType;
import play.mvc.Http;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Une page de l'application.
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1000000000000000002L;

    /**
     * The method signature Md5 hash.
     */
    public String hash;

    /**
     * Méthode permettant d'afficher cette page.
     */
    public transient Method method;

    /**
     * Because it appears that an seialization error is occured by
     * the class <code>Methos</code>.
     */
    public String methodToString;

    /**
     * Libellé de la page.
     */
    public String name;

    /**
     * Code de la page, utilisé pour l'identifier.
     */
    public String code;

    /**
     * Lien de la page.
     */
    public String url;

    /**
     * Description succinte de la page.
     */
    public String description;

    /**
     * Section d'Application de la page.
     */
    public ApplicationSection pageSection;

    /**
     * Utilisateur le plus bas hiérarchiquement qui peut accéder
     * à cette page.
     */
    public UserType minimumTypeRequirement;

    /**
     * Impact maximal des actions avec impact pouvant être effectuées
     * sur la page.
     */
    public ActionOnPageImpact higherActionImpact;

    /**
     * Classe controlleur de cette page.
     */
    public String controllerName;

    /**
     * Nom d'identification complète de la classe du controlleur.
     */
    public String controllerClass;

    public Page(Http.Request request, ApplicationSection section, String code, String description) {
        if (StringUtils.isNotEmpty(code))
            this.code = code;
        this.hash = Crypto.encrypt(request.invokedMethod.toString());
        this.name = request.actionMethod;
        this.url = request.url;
        if (StringUtils.isNotEmpty(description))
            this.description = description;
        this.pageSection = section;
        this.controllerName = request.controller;
        this.method = request.invokedMethod;
        this.methodToString = method.toString();
        this.controllerClass = request.controllerClass.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Page && this.method.toString().equals(obj.toString());
    }
}
