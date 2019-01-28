package controllers.elisisplay.internationalisation;

import controllers.elisisplay.Elisis;

/**
 * Définit les messages affichés suivant le language actuel de l'application.
 *
 * Pilier de l'internalisation.
 */

public final class ApplicationMessages {

    private ApplicationMessages() {
    }

    /**
     * @return Le message de violation de l'accès à une section.
     */
    public static String sectionAccessViolation()
    {
        switch (Elisis.ACTUAL_LANGUAGE)
        {
            case FRANCAIS:
                return StaticMessages.ACCOUNT_VIOLATION_FR;

            case ANGLAIS:
                return StaticMessages.ACCOUNT_VIOLATION_FR;

            default:
                return StaticMessages.ACCOUNT_VIOLATION_FR;
        }
    }

    /**
     * @return Le message indiquant qu'un utilisateur est déjà connecté.
     */
    public static String oftenUserConnected()
    {
        switch (Elisis.ACTUAL_LANGUAGE)
        {
            case FRANCAIS:
                return StaticMessages.OFTEN_USER_CONNECTED_FR;

            case ANGLAIS:
                return StaticMessages.OFTEN_USER_CONNECTED_FR;

            default:
                return StaticMessages.OFTEN_USER_CONNECTED_FR;
        }
    }

    /**
     * @return Le message indiquant qu'un utilisateur est déjà connecté.
     */
    public static String badUserInfos()
    {
        switch (Elisis.ACTUAL_LANGUAGE)
        {
            case FRANCAIS:
                return StaticMessages.USER_BAD_INFOS_FR;

            case ANGLAIS:
                return StaticMessages.USER_BAD_INFOS_FR;

            default:
                return StaticMessages.USER_BAD_INFOS_FR;
        }
    }
}
