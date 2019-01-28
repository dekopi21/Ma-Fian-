package controllers.elisisplay.views;

import play.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Utilitaires de méthodes sur les vues.
 */
public class Utils extends Controller{

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * Tous les attributs sont concernés.
     *
     * @param objects Objets à mettre sous forme de tableaux.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * @throws IllegalAccessException Se produit quand on ne peut pas accéder à la valeur d'un attribut
     * sur un objet.
     */
    public static HashMap<String, Object> dataTable(ArrayList<Object> objects) throws IllegalAccessException {
        return dataTable(objects, true);
    }

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * @param objects Objets à mettre sous forme de tableaux.
     * @param onlyPublic Indique si tous les attributs ({@code false})
     *                   ou seulement les attributs publics ({@code false})
     *                   des objets sont concernés.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * @throws IllegalAccessException Se produit quand on ne peut pas accéder à la valeur d'un attribut
     * sur un objet.
     */
    public static HashMap<String, Object> dataTable(ArrayList<Object> objects, boolean onlyPublic) throws IllegalAccessException {
        return dataTable(objects, onlyPublic, (String[]) null);
    }

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * @param objects Objets à mettre sous forme de tableaux.
     * @param onlyPublic Indique si tous les attributs ({@code false})
     *                   ou seulement les attributs publics ({@code false})
     *                   des objets sont concernés.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * @throws IllegalAccessException Se produit quand on ne peut pas accéder à la valeur d'un attribut
     * sur un objet.
     */
    public static HashMap<String, Object> dataTable(ArrayList<Object> objects, boolean onlyPublic, String... unless) throws IllegalAccessException {
        if (objects.size() == 0)
            //TODO : Revue - Return null ?
            return new HashMap<>();

        ArrayList<Field> fields = (onlyPublic) ? new ArrayList<>(Arrays.asList(objects.get(0).getClass().getDeclaredFields())) :
                new ArrayList<>(Arrays.asList(objects.get(0).getClass().getFields()));

        if (unless != null &&unless.length > 0)
        {
            ArrayList<String> unlesss = new ArrayList<>(Arrays.asList(unless));
            ArrayList<Field> toShowFields = new ArrayList<>();
            for (Field field:
                    fields) {
                if (!unlesss.contains(field.getName()))
                    toShowFields.add(field);

            }
            fields = toShowFields;
        }


        ArrayList<String> attributs = new ArrayList<>();
        ArrayList<ArrayList<Object>> values = new ArrayList<>();

        for (Field field:
                fields) {
            attributs.add(field.getName());
        }

        for (Object object:
                objects) {
            ArrayList<Object> valuesField = new ArrayList<>();
            for (Field field:
                    fields) {
                valuesField.add(field.get(object));
            }
            values.add(valuesField);
        }

        HashMap<String, Object> datas = new HashMap<>();
        datas.put("attributs", attributs);
        datas.put("values", values);
        return datas;
    }
}
