package controllers.elisisplay.cachecookie;

import controllers.elisisplay.ElisisPlay;
import controllers.elisisplay.cachecookie.cache.ElisisCache;
import controllers.elisisplay.cachecookie.cookie.ElisisCookie;
import controllers.elisisplay.cachecookie.session.ISessionObject;
import controllers.elisisplay.crypto.Crypto;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.secure.ElisisSecure;
import controllers.elisisplay.serial.IElisisSerial;
import controllers.elisisplay.serial.Serial;

import java.util.AbstractList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class with static methods for who want to store data, indifferently
 * on Cache or Cookie with Play default Cache implementation (through ElisisCache).
 */
public final class PlayCacheCookie {

    /**
     * Serial File name.
     */
    public static final String FILENAME = "sessions.ser";

    /**
     * Serial File relative directory path.
     */
    public static final String DIR_PATH = ElisisPlay.FILE_SEP + "data" + ElisisPlay.FILE_SEP + "sessions";

    /**
     * This return all the backup map objects.
     *
     * @return Unmodifiable all sessions objects map.
     */
    public static Map<String, ISessionObject> getSessionsMap() {
        return Collections.unmodifiableMap(sessionsMap);
    }

    /**
     * Sessions objects serials, used to serialize session objects.
     *
     * @return All Sessions objects serials.
     */
    public static IElisisSerial<ISessionObject> getSessionsSerial() {
        return sessionsSerial;
    }

    /**
     * Backup map, used when the Cache get a lack.
     */
    private static Map<String, ISessionObject> sessionsMap = new HashMap<>();

    /**
     * Flush sessions in the serial file.
     *
     * @return {@code true} if the sessions objects have been flushed.
     */
    public static boolean flushInSer() {
        sessionsSerial.reset();
        getSessionsMap().values().forEach(so -> sessionsSerial.add(so));
        return sessionsSerial.flush();
    }

    /**
     * Flush sessions in the serial file.
     *
     * @return {@code true} if the sessions objects have been flushed.
     */
    public static boolean loadFromSer() {
        sessionsSerial.init();
        AbstractList<ISessionObject> sessionObjects = sessionsSerial.all();
        for (ISessionObject so : sessionObjects) {
            init(so);
        }
        return true;
    }

    /**
     * Avoid construct objects from this class.
     */
    private PlayCacheCookie() {
    }

    /**
     * Check if this Session Cache contains a session uuid object.
     *
     * @param uuid Session UUID
     * @return {@code true} if this Session Cache contains a session uuid object.
     */
    private static boolean containsSessionUUID(String uuid) {
        return StringUtils.isNotEmpty(uuid) && (ElisisCache.contains(uuid) || (sessionsMap.get(uuid) != null));
    }

    /**
     * Check if this Session Cache contains the actual user session uuid object.
     *
     * @return {@code true} if this Session Cache contains the actual user session uuid object.
     */
    public static boolean containsSessionUUID() {
        return containsSessionUUID(ElisisCookie.cacheCookieUUIDValue());
    }

    /**
     * Init a user Session Cache Object.
     *
     * @return {@code true} if the session has been init.
     */
    public static boolean init() {
        String uuid = Crypto.UUID();
        //TODO : Initialises value of this session object.
        ISessionObject iSessionObject = null;
        sessionsMap.put(uuid, iSessionObject);
        ElisisCache.add(uuid, iSessionObject);
        return containsSessionUUID(uuid) && ElisisCookie.safeInit(uuid);
    }

    private static boolean init(ISessionObject so) {
        String uuid = so.toString();
        sessionsMap.put(uuid, so);
        ElisisCache.add(uuid, so);
        return containsSessionUUID(uuid) && ElisisCookie.safeInit(uuid);
    }

    /**
     * Init a user Session Cache Object.
     *
     * @return {@code true} if the session has been init.
     */
    public static boolean init(String uuid) {
        //TODO : Initialises value of this session object.
        ISessionObject iSessionObject = null;
        sessionsMap.put(uuid, iSessionObject);
        ElisisCache.add(uuid, iSessionObject);
        return containsSessionUUID(uuid) && ElisisCookie.safeInit(uuid);
    }

    /**
     * Init the session only when it is not.
     *
     * @return If the session has been init, or was already init.
     */
    public static boolean safeInit() {
        if (!containsSessionUUID())
            init();

        return containsSessionUUID();
    }

    /**
     * Get the CacheObject of one session.
     *
     * @param uuid Session UUID
     * @return This session CacheObject.
     */
    private static ISessionObject getCachedSession(String uuid) {
        return (ElisisCache.contains(uuid)) ? (ISessionObject) ElisisCache.get(uuid) : sessionsMap.get(uuid);
    }

    /**
     * Object used to serialize those Cache Session objects.
     * <p>
     * TODO : Issue - when {@code init} parameter equal à {@code true} this cause an initialiser error.
     */
    private static IElisisSerial<ISessionObject> sessionsSerial = new Serial<>(FILENAME, DIR_PATH, false);

    /**
     * Get this session value.
     *
     * @param key key
     * @return this session value.
     */
    public static Object getValue(String key) {
        return getValue(uuid(), key);
    }

    /**
     * Get a session value.
     *
     * @param uuid Session UUID
     * @param key  key
     * @return a session value.
     */
    private static Object getValue(String uuid, String key) {
        try {
            return getCachedSession(uuid).get(key);
        } catch (RuntimeException ignored) {
            safeInit();
            return null;
        }
    }

    /**
     * Put a value in the user uuid session with this key.
     *
     * @param uuid  uuid
     * @param key   key
     * @param value value
     * @return Return {@code true} if the value has been effectively add
     * or if the CacheCookie already contains a value for this key.
     */
    private static boolean putValue(String uuid, String key, Object value) {
        if (safeInit()) {
            ISessionObject sessionObject = getCachedSession(uuid);
            if (contains(uuid, key)) {
                sessionObject.replace(key, value);

            } else {
                sessionObject.put(key, value);
            }
            //Replace the object in the maps.
            //TODO : Does getCachedObjet(uuid).put(key, value) work ?
            sessionsMap.replace(uuid, sessionObject);
            ElisisCache.replace(uuid, sessionObject);
        }

        return containsKeyAndValue(uuid, key, value);
    }

    /**
     * Put a value in the user uuid session with this key.
     *
     * @param uuid           Session UUID
     * @param key            key
     * @param value          value
     * @param updateIfExists When this parameter is false, if the value already exists the operation is aborted.
     *                       When he is true, if the key already exists his value is updated.
     * @return {@code true}  if the operation has been done, or if the CacheCookie already contains
     * a value for this key and the param {@code force == false}.
     */
    private static boolean putValue(String uuid, String key, Object value, boolean updateIfExists) {
        if (updateIfExists) {
            return contains(uuid, key) ? replace(uuid, key, value) : putValue(uuid, key, value);
        } else
            return putValue(uuid, key, value);
    }

    /**
     * Put a value in the user uuid session with this key.
     *
     * @param key   key
     * @param value value
     * @return Return {@code true} if the value has been effectively add.
     */
    public static boolean putValue(String key, Object value) {
        return putValue(uuid(), key, value);
    }

    /**
     * Put a value in the user uuid session with this key.
     *
     * @param key            key
     * @param value          value
     * @param updateIfExists When this parameter is false, if the value already exists the operation is aborted.
     *                       When he is true, if the key already exists his value is updated.
     * @return {@code true}  if the operation has been done, or if the CacheCookie already contains
     * a value for this key and the param {@code force == false}.
     */
    public static boolean putValue(String key, Object value, boolean updateIfExists) {
        return putValue(uuid(), key, value, updateIfExists);
    }

    /**
     * Update a value of the actual user session uuid, with his key.
     *
     * @param key   key
     * @param value value
     */
    public static boolean replace(String key, Object value) {
        return replace(uuid(), key, value);
    }

    /**
     * Update a value of the user session uuid, with his key.
     *
     * @param uuid  User session UUID
     * @param key   key
     * @param value value
     */
    private static boolean replace(String uuid, String key, Object value) {
        if (contains(uuid, key)) {
            ISessionObject sessionObject = getCachedSession(uuid);
            sessionObject.replace(uuid, key);
            sessionsMap.replace(uuid, sessionObject);
            ElisisCache.replace(uuid, sessionObject);
        }

        return containsKeyAndValue(uuid, key, value);
    }

    /**
     * Contains a key.
     *
     * @param uuid The CookieSession UUID
     * @param key  key
     * @return {@code true} if the CacheCookie contains the key.
     */
    private static boolean contains(String uuid, String key) {
        return getCachedSession(uuid).contains(key);
    }

    /**
     * Contains a key.
     *
     * @param key key
     * @return {@code true} if the CacheCookie contains the key.
     */
    public static boolean contains(String key) {
        return contains(uuid(), key);
    }

    /**
     * Test If the CacheCookie a key and this value.
     *
     * @param uuid  The CookieSession UUID
     * @param value value
     * @param key   The value key
     * @return {@code true} if the CacheCookie contains the value.
     */
    private static boolean containsKeyAndValue(String uuid, String key, Object value) {
        return contains(uuid, key) && (getValue(uuid, key).equals(value));
    }

    /**
     * Test If the CacheCookie a key and this value.
     *
     * @param value value
     * @param key   The value key
     * @return {@code true} if the CacheCookie contains the value.
     */
    public static boolean containsKeyAndValue(String key, Object value) {
        return containsKeyAndValue(uuid(), key, value);
    }

    /**
     * Contains a value.
     *
     * @param uuid  The CookieSession UUID
     * @param value value
     * @return {@code true} if the CacheCookie contains the value.
     */
    private static boolean containsValue(String uuid, Object value) {
        return getCachedSession(uuid).containsValue(value);
    }

    /**
     * Contains a value.
     *
     * @param value value
     * @return {@code true} if the CacheCookie contains the value.
     */
    public static boolean containsValue(Object value) {
        return containsValue(uuid(), value);
    }

    /**
     * Remove a key & his value from the CacheCookie of one session.
     * <p>
     * If the value give as parameter is the not equals to the
     * key value, the key & his value is not removed.
     *
     * @param uuid  Session UUID
     * @param key   key
     * @param value value
     * @return true if the key was removed.
     */
    private static boolean removeValue(String uuid, String key, Object value) {
        ISessionObject sessionObject = getCachedSession(uuid);
        sessionObject.remove(key, value);
        sessionsMap.replace(uuid, sessionObject);
        ElisisCache.replace(uuid, sessionObject);

        return !contains(uuid, key);
    }

    /**
     * Remove a key & his value from the CacheCookie of this session.
     * <p>
     * If the value give as parameter is the not equals to the
     * key value, the key & his value is not removed.
     *
     * @param key   key
     * @param value value
     * @return true if the key was removed.
     */
    public static boolean removeValue(String key, Object value) {
        return removeValue(uuid(), key, value);
    }

    /**
     * Remove a key & his value from the CacheCookie of one session.
     *
     * @param uuid Session UUID
     * @param key  key
     * @return true if the key was removed.
     */
    private static boolean removeValue(String uuid, String key) {
        ISessionObject sessionObject = getCachedSession(uuid);
        sessionObject.remove(key);
        sessionsMap.replace(uuid, sessionObject);
        ElisisCache.replace(uuid, sessionObject);

        return !contains(uuid, key);
    }

    /**
     * Remove a key & his value from the CacheCookie of this session.
     *
     * @param key key
     * @return true if the key was removed.
     */
    public static boolean removeValue(String key) {
        return removeValue(uuid(), key);
    }

    /**
     * Remove all data of this session in the CacheCookie.
     *
     * @return true if the session data has been removed.
     */
    public static boolean remove() {
        return remove(uuid());
    }

    /**
     * This Session User UUID.
     * @return This Session User UUID.
     */
    private static String uuid() {
        return ElisisCookie.cacheCookieUUIDValue();
    }

    /**
     * Remove all data of a session in the CacheCookie.
     *
     * @param uuid Session UUID
     * @return true if the session data has been removed.
     */
    private static boolean remove(String uuid) {
        sessionsMap.remove(uuid);
        ElisisCache.delete(uuid);

        return !(sessionsMap.containsKey(uuid) || ElisisCache.contains(uuid));
    }

    /**
     * If all Sessions Objects contains a key with this value associate.
     * @param key key
     * @param value key
     * @return {@code true} If a session contains this key with this value associate.
     */
    public static boolean containsKeyAndValueAtAll(String key, Object value) {
        return getSessionsMap().values().stream().anyMatch(so -> so.containsKeyAndValue(key, value));
    }

    /**
     * Init Visitor User tracks datas.
     * @param uberCookie uberCookie
     * @param fingerPrint fingerPrint
     * @return {@code true} if Visitor datas has been init.
     */
    public static boolean initVisitor(String uberCookie, String fingerPrint) {
        return putValue(ElisisSecure.VISITOR_FINGER_PRINT_COOKIE, fingerPrint)
                && putValue(ElisisSecure.VISITOR_UBER_COOKIE, uberCookie);
    }
}
