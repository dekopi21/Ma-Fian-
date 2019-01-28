package controllers.elisisplay.ajax.attendance;

import controllers.CRUD;
import controllers.elisisplay.cachecookie.PlayCacheCookie;
import controllers.elisisplay.secure.ElisisSecure;

/**
 * Ajax actions to track user.
 */
public class Observator extends CRUD {

    /**
     * Init visitor tracking objects.
     * @param uberCookie uberCookie
     * @param fingerPrint fingerPrint
     */
    public static void initVisitor(String uberCookie, String fingerPrint) {
        renderJSON(ElisisSecure.initVisitor(uberCookie, fingerPrint));
    }

    /**
     * Init fingerPrint
     * @param fingerPrint fingerPrint
     */
    public static void initFingerPrint(String fingerPrint) {
        renderJSON(PlayCacheCookie.putValue(ElisisSecure.VISITOR_FINGER_PRINT_COOKIE, fingerPrint, true));
    }

    /**
     * Init uberCookie
     * @param uberCookie uberCookie
     */
    public static void initUberCookie(String uberCookie) {
        renderJSON(PlayCacheCookie.putValue(ElisisSecure.VISITOR_UBER_COOKIE, uberCookie, true));
    }
}
