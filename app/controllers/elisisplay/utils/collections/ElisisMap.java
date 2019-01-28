package controllers.elisisplay.utils.collections;

import controllers.elisisplay.crypto.Crypto;
import play.mvc.After;

import java.util.*;

/**
 * Created by Bienvenu on 19/11/2018 in elisisplay.
 */
public class ElisisMap<K, V> extends HashMap<K, V> implements IElisisMap<K, V>{

    private static final long serialVersionUID = 4463793298743363963L;

    private Calendar lastTimeAccess = new GregorianCalendar();

    private String mapKey;

    private boolean updated;

    public ElisisMap(String mapKey){
        this.mapKey = mapKey;
    }

    public ElisisMap(){
        this.mapKey = Crypto.UUID();
    }


    @Override
    public String getKey() {
        return mapKey;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public boolean _put(K key, V value) {
        put(key, value);

        return Objects.equals(get(key), value);
    }

    @Override
    public boolean _replace(K key, V newValue) {
        replace(key, newValue);

        return Objects.equals(get(key), newValue);
    }

    @Override
    public boolean _replaceOnlyIfExists(K key, V newValue) {
        if (contains(key))
            replace(key, newValue);

        return Objects.equals(get(key), newValue);
    }

    public boolean _remove(K key) {
        remove(key);

        return !contains(key);
    }

    /**
     * Set the lastTimeAccess value.
     */
    @After
    private void lastTimeAccessChanged() {
        lastTimeAccess = new GregorianCalendar();
    }

    /**
     * Override of toString.
     * @return mapKey.
     */
    @Override
    public String toString() {
        return mapKey;
    }
}
