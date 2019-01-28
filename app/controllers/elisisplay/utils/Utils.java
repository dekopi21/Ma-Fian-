package controllers.elisisplay.utils;

import java.util.ArrayList;

/**
 * General Utils.
 */
public class Utils {

    private static final String defaultValueToAdd = "0";

    public static String hashMD5(String toHash) {
        return play.libs.Codec.hexMD5(toHash);
    }

    public static ArrayList<Object> inverse(ArrayList<Object> toinverse) {
        ArrayList<Object> inverse = new ArrayList<>();
        for (int i = toinverse.size() - 1; i >= 0; i--) {
            inverse.add(toinverse.get(i));
        }
        return inverse;
    }

    public static String booleanStr(boolean value) {
        return (value) ? "Oui" : "Non";
    }

    static String generateCode(String prefixe, int suffixe, int nbMax) {
        StringBuilder value = new StringBuilder(String.valueOf(suffixe));
        while (value.length() + prefixe.length() < nbMax)
            value.insert(0, "0");
        return prefixe + value;
    }

    public static String generateCode(String prefixe, long suffixe, int nbMax) {
        StringBuilder value = new StringBuilder(String.valueOf(suffixe));
        while (value.length() + prefixe.length() < nbMax)
            value.insert(0, "0");
        return prefixe + value;
    }

    /**
     * Générer un code.
     * @param prefixe Préfixe
     * @param suffixe Suffixe.
     * @param nbMax La taille maximale du code.
     * @return Le code généré.
     */
    public static String generateCode(String prefixe, String suffixe, int nbMax) {
        return generateCode(prefixe, suffixe, nbMax, defaultValueToAdd);
    }

    private static String generateCode(String prefixe, String suffixe, int nbMax, String valueToAdd) {
        StringBuilder value = new StringBuilder(suffixe);
        while (value.length() + prefixe.length() < nbMax)
            value.insert(0, valueToAdd);
        return prefixe + value;
    }

    /**
     * Ajouter deux listes d'objets.
     * @param list La première liste d'objets.
     * @param secondList La seconde liste d'objets.
     * @param <O> La classe des objets.
     * @return La liste contenant les objets contenus dans les deux listes.
     */
    public static <O> ArrayList<O> addList(ArrayList<O> list, ArrayList<O> secondList) {
        if (isEmpty(list))
            return secondList;
        if (isEmpty(secondList))
            return list;
        list.addAll(secondList);
        return list;
    }

    /**
     * Indique si une liste est vide ou <code>null</code>.
     * @param list La liste à tester.
     * @return <code>true</code> si la liste est vide ou vaut <code>null</code>.
     */
    public static boolean isEmpty(ArrayList<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Recupérer dans une liste les objets semblables dans le contexte, à un objet.
     *
     * @param list      Liste initiale des objets.
     * @param toCompare Objet à laquelle on compare les objets de la liste.
     * @param <O>       Classe d'objet.
     * @return Une liste des objets semblables à l'objet <code>toCompare</code>
     * dans la liste.
     */
    public static <O> ArrayList<O> retrieveFromList(ArrayList<O> list, O toCompare) {
        ArrayList<O> retrieves = new ArrayList<>();
        for (O o : list) {
            if (o.equals(toCompare))
                retrieves.add(o);
        }
        return retrieves;
    }

    public static <O> O retrieveObjectFromList(ArrayList<O> list, O toCompare) {
        for (O o : list) {
            if (o.equals(toCompare))
                return o;
        }
        return null;
    }

    public static Object retrieveObjectFromList(ArrayList<?> list, String field, Object value) {
        for (Object o : list) {
            if (value.equals(controllers.elisisplay.reflect.Utils.getObjectField(o, field)))
                return o;
        }
        return null;
    }
}
