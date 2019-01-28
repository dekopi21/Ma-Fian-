package controllers.elisisplay.cachecookie.cache;

import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.reflect.Match;
import controllers.elisisplay.serial.IElisisSerial;
import controllers.elisisplay.time.TimeDuration;
import play.cache.Cache;
import play.mvc.Controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Use this instead of play.Cache to gain some consistency
 * and clarity.
 *
 * Methods with name like e.g. "method" are methods who works with the three
 * caches implementation (Play Cache,  local one and Serial File Cache).
 *
 * Methods with name like e.g. "___method" are methods who works only
 * with the Serial File Cache implementation.
 *
 * Methods with name like e.g. "__method" are methods who works only
 * with the Play Cache implementation.
 *
 * Methods with name like e.g. "_method" are methods who works only
 * with the local Cache implementation.
 *
 */
public class ElisisCache extends Controller {

    public static IElisisSerial<Match> getSerialsLocalCache() {
        return serialsLocalCache;
    }

    /**
     * Local Cache Serial.
     */
    private static IElisisSerial<Match> serialsLocalCache = new CacheSerial(false);

    /**
     * Values in this map are frequently Cached.
     */
    private static Map<String, Object> alwaysCached = new HashMap<>();

    /**
     * Default duration used when the duration passed to each methods is not a valid one.
     */
    public static final String DEFAULT_DURATION_TIME = "1h";

    /**
     * Add an element only if it doesn't exist.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return {@code true} if the value has been really added.
     */
    public static boolean add(String key, Object value, String expiration) {
        if (StringUtils.isEmpty(expiration))
            Cache.add(key, value, DEFAULT_DURATION_TIME);
        else
            Cache.add(key, value, expiration);

        _add(key, value);
        return containsKeyAndValue(key, value);
    }

    /**
     * Check If the Cache contains a key, and the value associate to this key
     * key equal the parameter {@code value}.
     *
     * @param key   key
     * @param value value
     * @return {@code true} If the Cache contains a key, and the value associate to this key
     * key equal the parameter {@code value}.
     */
    public static boolean containsKeyAndValue(String key, Object value) {
        return __containsKeyAndValue(key, value) ||
                _containsKeyAndValue(key, value);
    }

    /**
     * Check If the Play default Cache implementation contains a key, and the value associate to this key
     * key equal the parameter {@code value}.
     *
     * @param key   key
     * @param value value
     * @return {@code true} If the Play default Cache implementation contains a key, and the value associate to this key
     * key equal the parameter {@code value}.
     */
    public static boolean __containsKeyAndValue(String key, Object value) {
        return (__contains(key)) && Objects.equals(__get(key), value);
    }

    /**
     * Add an element only if it doesn't exist.
     *
     * @param key          Element key
     * @param value        Element value
     * @param expiration   Ex: 10, 3, 8
     * @param durationType Duration Type (Hour, Min, Sec...)
     * @return {@code true} if the value has been really added.
     */
    public static boolean add(String key, Object value, int expiration, DurationType durationType) {
        add(key, value, TimeDuration.duration(expiration, durationType));

        return containsKeyAndValue(key, value);
    }

    /**
     * Add an element only if it doesn't exist, and return only when
     * the element is effectively cached.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeAdd(String key, Object value, String expiration) {
        if (StringUtils.isEmpty(expiration))
            Cache.safeAdd(key, value, DEFAULT_DURATION_TIME);
        else
            Cache.safeAdd(key, value, expiration);

        _add(key, value);
        return containsKeyAndValue(key, value);
    }

    /**
     * Add an element only if it doesn't exist, and return only when
     * the element is effectively cached.
     *
     * @param key          Element key
     * @param value        Element value
     * @param expiration   Ex: 10, 3, 8
     * @param durationType Duration Type(Hour, Day...)
     * @return If the element an eventually been cached
     */
    public static boolean safeAdd(String key, Object value, int expiration, DurationType durationType) {
        return safeAdd(key, value, TimeDuration.duration(expiration, durationType));
    }

    /**
     * Add an element only if it doesn't exist (store it indefinitely), and return only when
     * the element is effectively cached.
     *
     * @param key   Element key
     * @param value Element value
     * @return If the element an eventually been cached
     */
    public static boolean safeAdd(String key, Object value) {
        Cache.safeAdd(key, value, null);
        _add(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Add an element only if it doesn't exist and store it indefinitely.
     *
     * @param key   Element key
     * @param value Element value
     */
    public static boolean add(String key, Object value) {
        Cache.add(key, value);
        _add(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Set an element.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static boolean set(String key, Object value, String expiration) {
        Cache.set(key, value, expiration);
        _set(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Set an element.
     *
     * @param key          Element key
     * @param value        Element value
     * @param expiration   Ex: 10, 3, 8
     * @param durationType Element durationType
     */
    public static boolean set(String key, Object value, int expiration, DurationType durationType) {
        set(key, value, TimeDuration.duration(expiration, durationType));

        return containsKeyAndValue(key, value);
    }

    /**
     * Set an element and return only when the element is effectively cached.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeSet(String key, Object value, String expiration) {
        Cache.safeSet(key, value, expiration);
        _set(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Set an element and return only when the element is effectively cached.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeSet(String key, Object value, int expiration, DurationType durationType) {
        return safeSet(key, value, TimeDuration.duration(expiration, durationType));
    }

    /**
     * Set an element and store it indefinitely.
     *
     * @param key   Element key
     * @param value Element value
     */
    public static boolean set(String key, Object value) {
        Cache.set(key, value);
        _set(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Replace an element only if it already exists.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static boolean replace(String key, Object value, String expiration) {
        Cache.replace(key, value, expiration);
        _replace(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Replace an element only if it already exists.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void replace(String key, Object value, int expiration, DurationType durationType) {
        replace(key, value, TimeDuration.duration(expiration, durationType));
    }

    /**
     * Replace an element only if it already exists and return only when the
     * element is effectively cached.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeReplace(String key, Object value, String expiration) {
        Cache.safeReplace(key, value, expiration);
        _replace(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Replace an element only if it already exists and return only when the
     * element is effectively cached.
     *
     * @param key        Element key
     * @param value      Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeReplace(String key, Object value, int expiration, DurationType durationType) {
        safeReplace(key, value, TimeDuration.duration(expiration, durationType));

        return containsKeyAndValue(key, value);
    }

    /**
     * Replace an element only if it already exists, store it indefinitely
     * and return only when the element is effectively cached.
     *
     * @param key   Element key
     * @param value Element value
     */
    public static boolean safeReplace(String key, Object value) {
        Cache.safeReplace(key, value, null);
        _replace(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Replace an element only if it already exists and store it indefinitely.
     *
     * @param key   Element key
     * @param value Element value
     */
    public static boolean replace(String key, Object value) {
        Cache.replace(key, value);
        _replace(key, value);

        return containsKeyAndValue(key, value);
    }

    /**
     * Return a Cache value identified by her key.
     * <p>
     * If the default Play Cache Map does not contains the key,
     * he is searched in the local Cache map.
     *
     * @param key key
     * @return The key value or {@code null} if all caches don't contains this hey.
     */
    public static Object get(String key) {
        return (__contains(key)) ? __get(key) : _get(key);
    }

    /**
     * Convenient clazz to get a value a class type;
     *
     * @param <T>   The needed type
     * @param key   The element key
     * @param clazz The type class
     * @return The element value or null
     */
    private static <T> T __get(String key, Class<T> clazz) {
        return Cache.get(key, clazz);
    }

    /**
     * Return a map Cache values identified by their keys.
     *
     * @param keys keys
     * @return A Map of keys values.
     */
    private static Map<String, Object> __get(String... keys) {
        return Cache.get(keys);
    }

    /**
     * Check if all Caches contains a key.
     *
     * @param key key
     * @return {@code true} if all Caches contains the key.
     */
    public static boolean contains(String key) {
        return (__get(key) != null) || (_get(key) != null);
    }

    /**
     * Check if Play default Cache contains a key.
     *
     * @param key key
     * @return {@code true} if Play default Cache contains the key.
     */
    public static boolean __contains(String key) {
        return (__get(key) != null);
    }

    /**
     * Get an object from the Play default cache.
     *
     * @param key key
     * @return The object associate to the key in the Play Cache map.
     */
    private static Object __get(String key) {
        return Cache.get(key);
    }

    /**
     * Check if all Caches contains a set of keys.
     *
     * @param keys Keys
     * @return {@code true} if one of the Cache contains all of this set of keys.
     */
    public static boolean containsAll(String... keys) {
        return __containsAll(keys) || _containsAll(keys);
    }

    /**
     * Check if Play Cache contains a set of keys.
     *
     * @param keys Keys
     * @return {@code false} if Cache does not contains one of this keys.
     */
    private static boolean __containsAll(String... keys) {
        for (String key :
                keys) {
            if (!contains(key))
                return false;
        }
        return true;
    }

    /**
     * Increment the element value (must be a Number) by 1.
     *
     * @param key Element key
     * @return The new value
     */
    public static long __incr(String key) {
        return Cache.incr(key, 1);
    }

    /**
     * Increment the element value (must be a Number).
     *
     * @param key Element key
     * @param by  The incr value
     * @return The new value
     */
    public static long __incr(String key, int by) {
        return Cache.incr(key, by);
    }

    /**
     * Decrement the element value (must be a Number).
     *
     * @param key Element key
     * @param by  The decr value
     * @return The new value
     */
    public static long __decr(String key, int by) {
        return Cache.decr(key, by);
    }

    /**
     * Decrement the element value (must be a Number) by 1.
     *
     * @param key Element key
     * @return The new value
     */
    public static long __decr(String key) {
        return Cache.decr(key, 1);
    }

    /**
     * Clear all data from cache.
     */
    public static void clear() {
        Cache.clear();
        alwaysCached.clear();
    }

    /**
     * Delete an element from the cache.
     *
     * @param key The element key
     */
    public static boolean delete(String key) {
        Cache.delete(key);
        alwaysCached.remove(key);

        return !contains(key);
    }

    /**
     * Delete an element from the cache and return only when the
     * element is effectively removed.
     *
     * @param key The element key
     * @return If the element an eventually been deleted
     */
    public static boolean safeDelete(String key) {
        return Cache.safeDelete(key) && _delete(key);

    }

    /**
     * Stop the cache system.
     */
    public static void stop() {
        Cache.stop();
        ___stop();
    }

    /**
     * Close the local cache system to the serial file.
     */
    public static void ___stop() {
        serialsLocalCache.reset();
        alwaysCached.keySet().forEach(key -> serialsLocalCache.add(new Match(key, alwaysCached.get(key))));
        serialsLocalCache.flush();
    }

    /**
     * Flush Local Cache objects in the serial file
     */
    public static boolean ___flush() {
        alwaysCached.keySet().forEach(key -> serialsLocalCache.add(new Match(key, alwaysCached.get(key))));
        return serialsLocalCache.flush();
    }

    /**
     * Load Local Cache objects from the serial file
     */
    public static boolean ___load() {
        int nb = alwaysCached.size();
        serialsLocalCache.all().forEach(match -> alwaysCached.put(match.getName(), match.getValue()));
        return alwaysCached.size() > nb;
    }

    /**
     * Initialize the cache system.
     */
    public static void init() throws FileNotFoundException {
        Cache.init();
        ___init();
    }

    /**
     * Initialize the local cache system from the serial file.
     */
    public static void ___init() throws FileNotFoundException {
        serialsLocalCache.init();
        alwaysCached = new HashMap<>();
        serialsLocalCache.all().forEach(match -> alwaysCached.put(match.getName(), match.getValue()));
    }

    /**
     * Add an element only if it doesn't exist and store it indefinitely
     * in the local Cache.
     *
     * @param key   Element key
     * @param value Element value
     */
    private static boolean _add(String key, Object value) {
        alwaysCached.put(key, value);

        return _containsKeyAndValue(key, value);
    }

    /**
     * Check if local Cache contains a key.
     *
     * @param key key
     * @return {@code true} if local Cache contains this key.
     */
    private static boolean _contains(String key) {
        return alwaysCached.containsKey(key);
    }

    /**
     * Return a Cache value identified by her key from the local Cache implementation.
     *
     * @param key key
     * @return The key value or {@code null} if local cache don't contains this hey.
     */
    private static Object _get(String key) {
        return alwaysCached.get(key);
    }

    /**
     * Check If the local Cache contains a key, and the value associate to this key
     * key equal the parameter {@code value}.
     *
     * @param key   key
     * @param value value
     * @return {@code true} If the Cache contains a key, and the value associate to this key
     * key equal the parameter {@code value}.
     */
    private static boolean _containsKeyAndValue(String key, Object value) {
        return _contains(key) && (Objects.equals(_get(key), value));
    }

    /**
     * Set new value for a key in the local Cache.
     *
     * @param key   key
     * @param value value
     * @return {@code true} if the value has been replaced.
     */
    private static boolean _set(String key, Object value) {
        if (_contains(key))
            alwaysCached.replace(key, value);

        return _containsKeyAndValue(key, value);
    }

    /**
     * Replace a value from the local cache.
     *
     * @param key   key
     * @param value value
     * @return {@code true} if the value has been replaced.
     */
    private static boolean _replace(String key, Object value) {
        alwaysCached.replace(key, value);

        return _containsKeyAndValue(key, value);
    }

    /**
     * Replace a value from the local cache if this oldValue corresponds to
     * the parameter {@code oldValue}.
     *
     * @param key      key
     * @param newValue Old value
     * @param oldValue New value
     * @return {@code true} if the value has been replaced.
     */
    private static boolean _replace(String key, Object oldValue, Object newValue) {
        if (_containsKeyAndValue(key, oldValue))
            alwaysCached.replace(key, oldValue, newValue);

        return _containsKeyAndValue(key, newValue);
    }

    /**
     * Replace a value from the local cache if this value exists.
     *
     * @param key   key
     * @param value value
     * @return {@code true} if the value has been replaced.
     */
    private static boolean _replaceIfExists(String key, Object value) {
        if (_contains(key))
            alwaysCached.replace(key, value);

        return _containsKeyAndValue(key, value);
    }

    /**
     * Clear the local cache.
     *
     * @return {@code true} if the local Cache has been cleared.
     */
    public static boolean _clear() {
        alwaysCached.clear();
        return alwaysCached.isEmpty();
    }

    /**
     * Remove a key from the local Cache.
     *
     * @param key key
     * @return {@code true} if the key has been removed from the local cache.
     */
    private static boolean _delete(String key) {
        alwaysCached.remove(key);
        return !_contains(key);
    }

    /**
     * Check If the local implementation of Cache contains all those keys.
     *
     * @param keys keys
     * @return {@code true} If the local implementation of Cache contains all those keys.
     */
    private static boolean _containsAll(String... keys) {
        for (String key :
                keys) {
            if (!_contains(key))
                return false;
        }
        return true;
    }

}
