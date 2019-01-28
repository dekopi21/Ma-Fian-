package controllers.elisisplay.lang;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Bienvenu on 03/09/2018 in controllers.elisisplay.
 */
public class StringUtils {

    public static final int NOTFOUND = 1;

    /**
     * Vérifies si une chaine est nulle ou ne contient rien.
     *
     * @param s La chaine à vérifier.
     * @return {@code true} Si la chaine vaut nulle ou est vide,
     * {@code false} dans tous les autres cas.
     */
    public static boolean isEmpty(String s) {
        return s == null || Objects.equals(s, "") || s.length() == 0 || org.apache.commons.lang.StringUtils.isBlank(s);
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }

    public static boolean areAllEmpty(String... strings) {
        for (String s :
                strings) {
            if (!s.isEmpty())
                return false;
        }
        return true;
    }

    public static boolean oneisEmpty(String... strings) {
        for (String s :
                strings) {
            if (s == null || s.isEmpty())
                return true;
        }
        return false;
    }

    public static boolean isOneEmpty(String... strings) {
        return oneisEmpty(strings);
    }

    public static boolean areEmpty(Object object) {
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(object.getClass().getFields()));
        for (Field f :
                fields) {
            if (f.getClass().isInstance(""))
                if (!isEmpty(String.valueOf(f)))
                    return false;
        }
        return true;
    }

    public static boolean isOneEmpty(Object object) {
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(object.getClass().getFields()));
        for (Field f :
                fields) {
            if (Objects.equals(f.getClass().getCanonicalName(), "String"))
                if (isEmpty(String.valueOf(f)))
                    return true;
        }
        return false;
    }

    public static int contains(ArrayList<String> strings, String search) {
        for (String s :
                strings) {
            if (Objects.equals(s, search))
                return strings.indexOf(s);
        }
        return NOTFOUND;
    }

    public static boolean _contains(ArrayList<String> strings, String search) {
       return contains(strings, search) != NOTFOUND;
     }

}
