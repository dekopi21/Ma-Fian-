package controllers.elisisplay.utils.collections;

import java.util.Map;

/**
 * Created by Bienvenu on 19/11/2018 in elisisplay.
 */
public interface IElisisMap<K, V> extends Map<K, V>{

    String getKey();

    boolean contains(K key);

    boolean _put(K key, V value);

    boolean _replace(K key, V newValue);

    boolean _replaceOnlyIfExists(K key, V newValue);

    boolean _remove(K key);
}
