package controllers.elisisplay.cachecookie.cookie;

import controllers.elisisplay.crypto.Crypto;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.secure.ElisisSecure;
import play.mvc.Controller;

/**
 * This class allow you to do action with the session
 * without manipulate it directly.
 */
public final class ElisisCookie extends Controller {

    /**
     * Avoid to construct an object from this.
     */
    private ElisisCookie() {
    }

    /**
     * Put a value in the session.
     *
     * @param key   key
     * @param value value
     * @return {@code true} if after operation session really contains key.
     */
    public static boolean put(String key, String value) {
        session.put(key, value);

        return contains(key);
    }

    /**
     * Put a key in a session.
     *
     * @param key   key
     * @param value value
     * @return {@code true} if after operation session really contains key.
     */
    public static boolean put(String key, Object value) {
        return put(key, value.toString());
    }

    /**
     * Update a value in session.
     *
     * @param key   key
     * @param value New value
     */
    public static void safeUpdate(String key, Object value) {
        safeUpdate(key, value.toString());
    }

    /**
     * Update a value in session.
     *
     * @param key   key
     * @param value New value
     */
    public static void update(String key, Object value) {
        update(key, value.toString());
    }

    /**
     * Update a value in session.
     *
     * @param key   key
     * @param value New value
     */
    public static void safeUpdate(String key, String value) {
        String oldValue = session.get(key);
        if (!StringUtils.isEmpty(oldValue))
            session.put(key, value);
    }

    /**
     * Update a value in session.
     *
     * @param key   key
     * @param value New value
     */
    public static void update(String key, String value) {
        if (contains(key))
            remove(key);

        put(key, value);
    }

    /**
     * Get a value in session.
     *
     * @param key key
     */
    public static String get(String key) {
        return session.get(key);
    }

    /**
     * Remove a value in session.
     *
     * @param key key
     * @return The value removed
     */
    public static String remove(String key) {
        String oldValue = session.get(key);
        session.remove(key);
        return oldValue;
    }

    /**
     * Remove values in session.
     *
     * @param keys keys
     */
    public static void remove(String... keys) {
        session.remove(keys);
    }

    /**
     * Check if session contains a key value.
     *
     * @param key key
     * @return {@code true} if session contains a key vakue.
     */
    public static boolean contains(String key) {
        return session.contains(key);
    }

    /**
     * Init a session.
     */
    private static boolean init() {
        return put(ElisisSecure.COOKIE_CACHE_CODE_KEY, Crypto.UUIDHash());
    }

    /**
     * Check if session is init.
     *
     * @return {@code true} if session already contains the key init search.
     */
    public static boolean isInit() {
        return contains(ElisisSecure.COOKIE_CACHE_CODE_KEY);
    }

    public static String cacheCookieUUIDValue() {
        return get(ElisisSecure.COOKIE_CACHE_CODE_KEY);
    }

    private static void safeInit() {
        if (isInit())
            safeInit(Crypto.UUID());
    }

    public static boolean safeInit(String uuid) {
        if (!isInit())
            put(ElisisSecure.COOKIE_CACHE_CODE_KEY, uuid);

        return isInit();
    }
}
