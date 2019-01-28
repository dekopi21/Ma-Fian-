package controllers.elisisplay.secure;

/**
 * Représente les différents types d'utilisateurs.
 *
 * Doit être modifiée ou réimplémenter pour l'adapter à votre application.
 */
public enum UserType {

    /**
     * Développeur de l'application.
     *
     * Profil utilisé pendant le développment ou la maintenance de l'application.
     */
    DEV,

    /**
     * Premier Administrateur de la plateforme.
     */
    SUPADMIN,

    /**
     * Administrateur de l'application.
     */
    ADMIN,

    /**
     * Représente le compte utilisateur par défaut, avec aucun droit.
     */
    VISITOR,

    /**
     * Editeur du site Web.
     */
    EDITOR,

    /**
     * Mainteneur du site Web, lorsque il est en production.
     *
     * N'est pas un développeur du site.
     */
    MAINTAINER,
}
