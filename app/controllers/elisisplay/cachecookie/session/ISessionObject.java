package controllers.elisisplay.cachecookie.session;

import java.io.Serializable;

/**
 * Interface for objects who must contains Session data.
 */
public interface ISessionObject extends Serializable {

    /**
     * This may be used for some key identification purpose.
     *
     * @return the key of this session object.
     */
    String toString();

    /**
     * Remove a key in the map.
     *
     * @param key   key
     * @param value value
     * @return {@code true} if the key has been removed.
     */
    boolean remove(String key, Object value);

    /**
     * Remove a key in the map.
     *
     * @param key key
     * @return {@code true} if the key has been removed.
     */
    boolean remove(String key);

    /**
     * Replace a value. Only replace it the key already exist
     * @param key key
     * @param newValue newValue
     * @return {@code true}  if the value has been replaced.
     */
    boolean replaceOnlyIfExists(String key, Object newValue);

    /**
     * Replace a key and his value.
     * If the key doesn't exists the value is added.
     * @param key key
     * @param newValue newValue
     * @return {@code true}  if the value has been replaced or added.
     */
    boolean replace(String key, Object newValue);

    /**
     * Replace a key and his value, only if his old value correspond to parameter {@code oldValue}.
     * If the key doesn't exists the value is not added.
     * @param key key
     * @param newValue newValue
     * @param oldValue oldvalue
     * @return {@code true}  if the value has been replaced.
     */
    boolean replace(String key, Object oldValue, Object newValue);

    /**
     * Put a value with this key in the map.
     * @param key key
     * @param value value
     * @return {@code true} if the map contains this value and key after the operation.
     */
    boolean put(String key, Object value);

    /**
     * Check if the map contains one value.
     * Use this when you don't know the key of this value in the map.
     * @param value value
     * @return {@code true} if the map contains this value.
     */
    boolean containsValue(Object value);

    /**
     * If the map contains a key.
     * @param key key
     * @return {@code true} if the map contains a value for this key.
     */
    boolean contains(String key);

    /**
     * Get a value associate to this key.
     * @param key key
     * @return value associate to this key.
     */
    Object get(String key);

    /**
     * Check If the map contains a key and the value associate to this one is
     * equal to the {@code value} parameter.
     * @param key key
     * @param value value
     * @return {@code true} If the map contains a key and the value associate to this one is
     * equal to the {@code value} parameter.
     */
    boolean containsKeyAndValue(String key, Object value);
}
