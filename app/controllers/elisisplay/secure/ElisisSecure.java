package controllers.elisisplay.secure;

import controllers.elisisplay.cachecookie.PlayCacheCookie;
import controllers.elisisplay.cachecookie.session.UserLogState;
import controllers.elisisplay.controller.ElisisController;
import controllers.elisisplay.controller.StaticUrls;
import controllers.elisisplay.internationalisation.ApplicationMessages;
import controllers.elisisplay.lang.StringUtils;
import models.utilisateurs.CompteUtilisateur;
import org.apache.commons.lang.NotImplementedException;
import play.mvc.Scope.Session;

/**
 * This class play ☺ the same role as the Play Secure Module, but
 * attune with the general idea of Elisis module.
 */
@SuppressWarnings({"MethodReturnOfConcreteClass"})
public final class ElisisSecure extends ElisisController {

    /**
     * Value equal {@code true} if user want to be connected even when he disconnect from
     * his account.
     */
    public static final String COOKIE_STAY_CONNECTED = "stayConnected";

    /**
     * It is the cookie used to store in the sesion his UUID value.
     * <p>
     * This value is used to store session data in the CacheCookie.
     */
    public static final String COOKIE_CACHE_CODE_KEY = "cacheKey";

    /**
     * Elisis application name key.
     */
    public static final String ELISIS_INIT_COOKIE = "elisis";

    /**
     * Visitor UberCookie key.
     */
    public static final String VISITOR_UBER_COOKIE = "elisisUberCookieHash";

    /**
     * Visitor fingerprint cookie.
     */
    public static final String VISITOR_FINGER_PRINT_COOKIE = "elisisFingerPrintHash";

    /**
     * If user is known.
     */
    public static final String VISITOR_IS_IDENTIFIED_COOKIE = "elisisIsUserKnown";

    /**
     * Gallery Pics key.
     */
    public static final String GALLERY_LIB_COOKIE = "galleryLib";

    /**
     * Loading template key.
     */
    public static final String LOADING_TEMPLATE_COOKIE = "templateLib";

    /**
     * Admin template key.
     */
    public static final String ADMIN_TEMPLATE_COOKIE = "adminTemplate";

    /**
     * Visitor template key.
     */
    public static final String VISITOR_TEMPLATE_COOKIE = "visitorTemplate";

    /**
     * Username user session key.
     */
    public static final String COOKIE_USERNAME = "username";

    /**
     * Type user session key.
     */
    public static final String COOKIE_TYPE_USER = "typeUser";

    /**
     * Connexion state key store in the user session.
     */
    public static final String COOKIE_SESSION_STATE = "sessionState";

    /**
     * Default user type give to new user or visitor.
     */
    public static final UserType DEFAULT_USER_TYPE = UserType.VISITOR;

    /**
     * Default user session state.
     */
    public static final UserLogState DEFAULT_SESSION_STATE = UserLogState.UNKNOWN;

    /**
     * Private constructor.
     * <p>
     * Avoid construct objects from this class.
     */
    private ElisisSecure() {
    }

    public static boolean contains(String key) {
        return PlayCacheCookie.contains(key);
    }

    public static boolean stayConnected() {
        return PlayCacheCookie.contains(COOKIE_STAY_CONNECTED) && (boolean) PlayCacheCookie.getValue(COOKIE_STAY_CONNECTED);
    }

    public static boolean stayConnected(boolean stayConnected) {
        return PlayCacheCookie.putValue(COOKIE_STAY_CONNECTED, stayConnected, true);
    }

    public static UserLogState userLogState() {
        if (PlayCacheCookie.contains(COOKIE_SESSION_STATE))
            return (UserLogState) PlayCacheCookie.getValue(COOKIE_SESSION_STATE);

        return UserLogState.UNKNOWN;
    }

    /**
     * Obtains the actual user type.
     *
     * @return Actual account type.
     */
    public static UserType actualUserType() {
        return userType(connectedName());
    }

    /**
     * The user type of a user.
     *
     * @param username user name
     * @return The user type of a user.
     */
    private static UserType userType(String username) {
        throw new NotImplementedException("USers class not yet defined.");
    }

    private static UserType userType() {
        return (UserType) PlayCacheCookie.getValue(COOKIE_TYPE_USER);
    }

    /**
     * Init of the session.
     */
    public static boolean initSession() {
        if (session == null) {
            session = new Session();
        } else
            session.clear();

        return true;
    }

    /**
     * Safe init of the user session CacheCookie.
     * @return {@true} if the session CacheCookie have been init.
     */
    public static boolean safeInit() {
        return PlayCacheCookie.safeInit();
    }

    /**
     * Test if this user is connected in the actual session.
     *
     * @param username username or email of user.
     * @return {@code true} if the session username equals the parameter {@code name},
     */
    public static boolean isConnected(String username) {
        return PlayCacheCookie.containsKeyAndValue(COOKIE_USERNAME, username);
    }

    /**
     * Test if this username is connected in one session.
     *
     * @param username username or email of user.
     * @return {@code true} if the session username equals the parameter {@code name},
     */
    public static boolean isConnectedAtAll(String username) {
        return PlayCacheCookie.containsKeyAndValueAtAll(COOKIE_USERNAME, username);
    }

    /**
     * The actual session username.
     *
     * @return The actual session username.
     */
    public static String connectedName() {
        if (connected())
            return (String) PlayCacheCookie.getValue(COOKIE_USERNAME);

        return null;
    }

    /**
     * The user connected code.
     *
     * @return the user connected code.
     */
    public static String connectedCode() {
        return getCode(connectedName());
    }

    /**
     * A user connected code.
     *
     * @param username username
     * @return A user connected code.
     */
    private static String getCode(String username) {
        throw new NotImplementedException();
    }

    /**
     * Test if the session is init.
     */
    public static boolean sessionIsInit() {
        return (session != null) && PlayCacheCookie.safeInit();
    }

    /**
     * Test if an user is connected in the actual session.
     *
     * @return {@code true} if an user is connected in the actual session.
     */
    public static boolean connected() {
        return typeUserKnown() && (PlayCacheCookie.contains(COOKIE_USERNAME));
    }

    /**
     * @return Retourne l'état de la session actuelle.
     */
    public static UserLogState sessionState() {
        return PlayCacheCookie.contains(COOKIE_SESSION_STATE) ?
                (UserLogState) PlayCacheCookie.getValue(COOKIE_SESSION_STATE) : DEFAULT_SESSION_STATE;
    }

    /**
     * Test if an user is connected in the actual session and if his username is correct.
     *
     * @return {@code true} if an user is connected in the actual session and if his username is correct.
     * <p>
     * //TODO A Surchager.
     */
    public static boolean reallyConnected() {
        return (connected() && exists(connectedName()));
    }

    /**
     * If a username exists.
     *
     * @param username username
     * @return If this username exists.
     */
    private static boolean exists(String username) {
        throw new NotImplementedException();
    }

    /**
     * Test if the actual user type is known.
     *
     * @return Type of actual user.
     */
    public static boolean typeUserKnown() {
        return sessionIsInit() && (PlayCacheCookie.contains(COOKIE_TYPE_USER));
    }

    /**
     * Get the user class of the user connected in this session.
     *
     * @return the user class of the user connected in this session.
     */
    public static CompteUtilisateur user() {
        return getUser(connectedName());
    }

    /**
     * Get the user class of a user who exists.
     *
     * @param username username
     * @return the user class of a user who exists.
     */
    private static CompteUtilisateur getUser(String username) {
        throw new NotImplementedException();
    }

    /**
     * Get the user class of the user connected in this session.
     * If there is no user in this session, the visitor is invited to
     * connect to his account.
     *
     * @return the user class of the user connected in this session.
     */
    public static CompteUtilisateur userSafe() {
        //Disconnect if user is unknown.
        if (!reallyConnected())
            onDisconnected();

        return getUser(connectedName());
    }


    /**
     * Get the actual user in the session type.
     *
     * @return the actual user in the session type.
     */
    public static UserType getActualTypeUser() {
        return typeUserKnown() ?
                (UserType) PlayCacheCookie.getValue(COOKIE_TYPE_USER) : DEFAULT_USER_TYPE;
    }

    /**
     * Call when the user want to connect to his account.
     */
    public static void onLoggedIn(String username, String password) {
        onLoggedIn(username, password, "");
    }

    /**
     * Call when the user want to connect to his account.
     */
    public static void onLoggedIn(String username, String password, String url) {
        //Already connected.
        if (reallyConnected()) {
            if (userLogState() == UserLogState.CONNECTED) {
                flash.error(ApplicationMessages.oftenUserConnected());
                Profiles.goToDefaultUrl();
                return;
            } else if (((userLogState() == UserLogState.DISCONNECTED) || (userLogState() == UserLogState.LOCKED)) && exists(username, password)) {
                //If user want to be stay connected then the values of his account information are filled automatically.
                    PlayCacheCookie.putValue(COOKIE_SESSION_STATE, UserLogState.CONNECTED, true);
                Profiles.goToDefaultUrl();
            }

        }

        //The username is not correct.
        if (sessionIsInit() && connected() && !reallyConnected())
            session = new Session();

        //Init the session.
        if (sessionIsInit() && !connected())
            session = new Session();

        //The account exists.
        if (exists(username, password)) {
            if (PlayCacheCookie.containsKeyAndValueAtAll(COOKIE_USERNAME, username)) {
                //There is already a user connected with this account.
                Profiles.userAlreadyConnected();
                return;
            }

            setUserConnected(username);

            if (initSession()) {
                PlayCacheCookie.putValue(COOKIE_USERNAME, username, true);
                PlayCacheCookie.putValue(COOKIE_TYPE_USER, typeUser(username));
                PlayCacheCookie.putValue(COOKIE_SESSION_STATE, UserLogState.CONNECTED);
                PlayCacheCookie.putValue(VISITOR_IS_IDENTIFIED_COOKIE, Boolean.TRUE);
            }

            if (StringUtils.isEmpty(url)) {
                redirect(Profiles.getDefaultUrl(getActualTypeUser()));
            } else {
                redirect(url);
            }

        } else {
            flash.error(ApplicationMessages.badUserInfos());
            signin();
        }
    }

    /**
     * Set to the database entities (objects) that this user is connected at this
     * moment, or just set his last time connection date value.
     *
     * @param username username
     */
    private static void setUserConnected(String username) {
        throw new NotImplementedException();
    }

    /**
     * Default sign in action.
     */
    public static void signin() {
        Profiles.signin();
    }

    /**
     * Find the user type of a user.
     *
     * @param username username or login.
     * @return The user type.
     */
    private static UserType typeUser(String username) {
        return userType(username);
    }

    /**
     * Test if a user exists.
     *
     * @param username username or email
     * @param password password
     * @return {@code true} if the account exists.
     * @throws NotImplementedException Exception
     */
    private static boolean exists(String username, String password) {
        throw new NotImplementedException();
    }

    /**
     * Disconnect and clear the session.
     */
    public static void onDisconnected() {
        onDisconnected(UserLogState.UNKNOWN);
    }

    /**
     * Disconnect and clear the session.
     */
    public static void onDisconnected(UserLogState userLogState) {

        //Correct and active session.
        if (reallyConnected()) {
            PlayCacheCookie.replace(COOKIE_SESSION_STATE, userLogState);

            if (userLogState == UserLogState.UNKNOWN) {
                PlayCacheCookie.removeValue(COOKIE_USERNAME);
                PlayCacheCookie.removeValue(COOKIE_TYPE_USER);
                PlayCacheCookie.removeValue(COOKIE_SESSION_STATE);
            }

            Profiles.signin();
        }

        //Session data are not correct.
        if (sessionIsInit() && connected()) {
            session.remove(COOKIE_USERNAME);
            session.remove(COOKIE_TYPE_USER);
            session.remove(COOKIE_SESSION_STATE);

            Profiles.signin();
        }

        //Session is init.
        if (sessionIsInit() && !connected()) {
            session.remove(COOKIE_USERNAME);
            session.remove(COOKIE_TYPE_USER);
            session.remove(COOKIE_SESSION_STATE);

            Profiles.signin();
        }

        //Session is not init.
        if (!sessionIsInit()) {
            session = new Session();
            PlayCacheCookie.safeInit();

            redirect(StaticUrls.INDEX);
        }
    }

    public static void signup() {
        Profiles.signup();
    }

    public static boolean initVisitor(String uberCookie, String fingerPrint) {
        return PlayCacheCookie.initVisitor(uberCookie, fingerPrint);
    }
}
