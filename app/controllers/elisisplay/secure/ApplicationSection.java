package controllers.elisisplay.secure;

/**
 * Application various sections.
 *
 * Must be updated by the need.
 */
public enum ApplicationSection {
    /**
     * Admin section of Application.
     */
    ADMIN,

    /**
     * Public section of the Application.
     */
    PUBLIC,

    /**
     * Connexion and authentication pages section.
     */
    AUTHENTIFICATION,

    /**
     * Application Editor section (is often the product manager section).
     */
    EDITOR,

    /**
     * Section use only for debug the application.
     */
    DEBUG,

    /**
     * Section used in development of the application.
     */
    DEV
}
