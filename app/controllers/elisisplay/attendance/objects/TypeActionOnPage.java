package controllers.elisisplay.attendance.objects;

/**
 *
 */
public enum TypeActionOnPage {
    CREATE,
    READ,
    UPDATE,
    DELETE,
    VISITONLY,

    /**
     * L'action qu'on attend de l'utilisateur.
     */
    USERDEFAULTPAGEACTION,
    DEBUG,
    ;

    ActionOnPageImpact impact(){
        switch (this)
        {
            case CREATE:
                return ActionOnPageImpact.COMMON;
            case READ:
                return ActionOnPageImpact.INSIGNIFICANT;
            case UPDATE:
                return ActionOnPageImpact.COMMON;
            case DELETE:
                return ActionOnPageImpact.IMPORTANT;
            case VISITONLY:
                return ActionOnPageImpact.COMMON;
            case USERDEFAULTPAGEACTION:
                return ActionOnPageImpact.MAJOR;
            case DEBUG:
                return ActionOnPageImpact.IMPORTANT;
            default:
                return ActionOnPageImpact.UNKNOWN;
        }
    }
}
