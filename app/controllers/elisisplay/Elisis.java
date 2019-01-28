package controllers.elisisplay;

import controllers.elisisplay.internationalisation.Language;

/**
 * Elisis Module class.
 */
public final class Elisis {

    private Elisis() {}

    /**
     * j : Jour
     * s : S
     * m : Minute
     * h : heure
     * M : Mois
     * a : année
     */
    public static final String DEFAULT_OBJECT_EXPIRE_TIME = "90j";

    /**
     * Nom de l'Application/Organisation).
     */
    public static final String APP_NAME = "";

    /**
     * Mail prinicipal de l'Application/Organisation.
     */
    public static final String APP_EMAIL = "";

    /**
     * Mail prinicipal de l'Application/Organisation.
     */
    public static final String APP_EMAIL_USERNAME = "";

    /**
     * Mail principal de l'Application/Organisation.
     */
    public static final String APP_EMAIL_PASSWORD = "";

    /**
     * Langage actuel du site. Utilisé pour déterminer les messages
     * à afficher.
     */
    public static final Language ACTUAL_LANGUAGE = Language.FRANCAIS;

    /**
     * Définit l'orientation du framework utilisée pour la création
     * du site
     *
     * Est tout simplement aussi la catégorie du site.
     */
    public static final String ELISIS_TYPE_APPLICATION = "schoolar";
}
