package controllers.elisisplay.cachecookie.session;

/**
 * User session state.
 */
public enum UserLogState {

    /**
     * Non-init session.
     */
    UNKNOWN,

    /**
     * Session connected and available.
     */
    CONNECTED,

    /**
     * Session disconneted but active.
     */
    DISCONNECTED,

    /**
     * Session connected, but locked.
     */
    LOCKED,
    ;
}
