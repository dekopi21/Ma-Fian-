package controllers.elisisplay.reflect;

import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import static controllers.elisisplay.logging.TypeLogMessageError.*;

/**
 * Utilitaires pour l'introspection et la gestion dynamique des objets.
 */
@SuppressWarnings("Duplicates")
public class Utils {

    public static String callerMethodSignature(Throwable throwable) {
        return throwable.getStackTrace()[1].getClassName() + "." +
                throwable.getStackTrace()[1].getMethodName();
    }

    public static String calleeMethodSignature(Throwable throwable) {
        return throwable.getStackTrace()[0].getClassName() + "." +
                throwable.getStackTrace()[0].getMethodName();
    }

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * Tous les attributs sont concernés.
     *
     * @param object Objet à mettre sous forme de tableaux.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * sur un objet.
     */
    public static ArrayList<Match> data(Class clazz, Object object) {
        return data(clazz, object, true);
    }

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * Tous les attributs sont concernés.
     *
     * @param object Objet à mettre sous forme de tableaux.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * sur un objet.
     */
    public static HashMap<String, Object> _data(Object object) {
        return _data(object, true);
    }

    public static HashMap<String,Object> _data(Object object, boolean onlyPublic, String... unless) {
        if (object == null)
            return new HashMap<>();
        ArrayList<Field> fields = new ArrayList<>();

        try {
            fields = getObjectFields(object.getClass(), onlyPublic, unless);
        }
        catch (Exception e) {
            Logger.log(REFLECTIONERROR.setThrowable(e));
        }

        HashMap<String, Object> fieldsValues = new HashMap<>();

        for (Field field:
                fields) {
            try {
                fieldsValues.put(field.getName(), field.get(object));
            }
            catch (IllegalAccessException iae){
                Logger.log(REFLECTIONERROR.setThrowableAndParticularMessage(iae,
                        "Impossible d'accéder au champ " + field.getName() + " de l'Objet " + object.toString() +
                                "."));
            }
        }
        return fieldsValues;
    }

    public static ArrayList<Match> data(Class clazz, Object object, boolean onlyPublic, String... unless) {
        if (object == null)
            return new ArrayList<>();

        ArrayList<Field> fields = getObjectFields(clazz, onlyPublic, unless);

        ArrayList<Match> matches = new ArrayList<>();

        for (Field field:
                fields) {
            matches.add(new Match(field, object));
        }
        return matches;
    }

    private static ArrayList<Field> removeFields(ArrayList<Field> fields, String[] unless) {
        if (unless != null && unless.length > 0)
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
        return fields;
    }

    private static ArrayList<Field> getObjectFields(Class clazz, boolean onlyPublic, String... unless){
        ArrayList<Field> fields = (onlyPublic) ? new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())) :
                new ArrayList<>(Arrays.asList(clazz.getFields()));

        //Enlever les champs dont le nom est passé en paramêtre.
        return removeFields(fields, unless);
    }

    private static ArrayList<String> getObjectFieldsNames(Class clazz, boolean onlyPublic, String... unless){
        ArrayList<Field> fields = (onlyPublic) ? new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())) :
                new ArrayList<>(Arrays.asList(clazz.getFields()));

        //Enlever les champs dont le nom est passé en paramêtre.
        fields = removeFields(fields, unless);
        ArrayList<String> fieldsNames = new ArrayList<>();
        for (Field field:
             fields) {
            fieldsNames.add(field.getName());
        }
        return fieldsNames;
    }

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * Tous les attributs sont concernés.
     *
     * @param object Objet à mettre sous forme de tableaux.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * sur un objet.
     */
    public static HashMap<String, Object> _data(Object object, String... unless) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(object);
        return _data(objects, true, unless);
    }

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * Tous les attributs sont concernés.
     *
     * @param object Objet à mettre sous forme de tableaux.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * sur un objet.
     */
    public static ArrayList<Match> data(Class clazz, Object object, String... unless) {
        return data(clazz, object, true, unless);
    }

    /**
     * Permet de créer une HashMap, facilitant la création de tableaux dynamiques de valeurs.
     *
     * Tous les attributs sont concernés.
     *
     * @param objects Objets à mettre sous forme de tableaux.
     * @return Une HashMap composé des attributs et des valeurs qui y sont associées.
     * sur un objet.
     */
    public static HashMap<String, Object> dataTable(ArrayList<Object> objects) {
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
     * sur un objet.
     */
    public static HashMap<String, Object> dataTable(ArrayList<Object> objects, boolean onlyPublic) {
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
     * sur un objet.
     */
    public static HashMap<String, Object> dataTable(ArrayList<Object> objects, boolean onlyPublic, String... unless) {
        if (objects.size() == 0)
            return new HashMap<>();

        ArrayList<Field> fields = null;

        try {
            fields = getObjectFields(objects.get(0).getClass(), onlyPublic, unless);
        }
        catch (Exception e) {
            Logger.log(REFLECTIONERROR.setThrowable(e));
        }

        ArrayList<String> attributs;
        ArrayList<ArrayList<Object>> values = new ArrayList<>();

        if (fields == null)
            return new HashMap<>();

        attributs = fields.stream().map(field -> field.getName().toUpperCase()).collect(Collectors.toCollection(ArrayList::new));

        for (Object object:
                objects) {
            ArrayList<Object> valuesField = new ArrayList<>();
            for (Field field:
                    fields) {
                try {
                    valuesField.add(field.get(object));
                }
                catch (IllegalAccessException iae){
                    Logger.log(REFLECTIONERROR.setThrowableAndParticularMessage(iae,
                            "Impossible d'accéder au champ " + field.getName() + " de l'Objet " + object.toString() +
                                    "."));
                }
            }
            values.add(valuesField);
        }

        HashMap<String, Object> datas = new HashMap<>();
        datas.put("attributs", attributs);
        datas.put("values", values);
        return datas;
    }

    /**
     * Mettre à jour les champs d'un objet.
     * @param objectToUpdate Objet à mettre à jour.
     * @param fieldsAndValues Succession de champ et de leur valeurs.
     *                        Exemple : "age", 21...
     * @return {@code true} si la modification n'a retournée aucune erreur,
     * {@code false} dans le cas contraire.
     */
    public static boolean updateFields(Class clazz, Object objectToUpdate, Object... fieldsAndValues) {

        if (objectToUpdate == null || fieldsAndValues == null || fieldsAndValues.length < 2)
            return false;

        HashMap<String, Object> fieldAndValue = new HashMap<>();
        ArrayList<String> fieldsStr = new ArrayList<>();

        for (int i = 0; i < fieldsAndValues.length; i += 2) {
            fieldsStr.add(fieldsAndValues[i].toString());
            try {
                fieldAndValue.put(fieldsAndValues[i].toString(), fieldsAndValues[i + 1]);
            } catch (IndexOutOfBoundsException ioobe) {
                Logger.log(UPDATEENTITYERROR.setThrowableAndParticularMessage(ioobe,
                        "Méthode : controllers.elisisplay.ElisisModelController." +
                                "edit(T model, String field, Object value)." +
                                "\n Les chammps ne sont pas synchonisés avec leur valeur."));
            }
        }

        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));

        for (Field f :
                fields) {
            if (StringUtils._contains(fieldsStr, f.getName())) {
                try {
                    if (fieldAndValue.get(f.getName()) != null)
                        f.set(objectToUpdate, fieldAndValue.get(f.getName()));
                } catch (IllegalAccessException e) {
                    Logger.log(FIELDDONTEXISTS.setThrowableAndParticularMessage(e,
                            "Méthode : controllers.elisisplay.ElisisModelController." +
                                    "edit(T model, String field, Object value)." +
                                    "\n Champ : " + f.getName()));
                    return false;
                } catch (Exception e) {
                    Logger.log(UPDATEENTITYERROR.setThrowableAndParticularMessage(e,
                            "Méthode : controllers.elisisplay.ElisisModelController." +
                                    "edit(T model, String field, Object value)." +
                                    "\nErreur lors de la modification du Champ : " + f.getName()));
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Mettre à jour un champ d'un objet.
     * @param objectToUpdate Objet à mettre à jour.
     * @param field Champ à mettre à jour.
     * @param value Nouvelle valeur du champ.
     * @return {@code true} si la modification n'a retournée aucune erreur,
     * {@code false} dans le cas contraire.
     */
    public static boolean updateField(Class objectToUpdateClass, Object objectToUpdate, String field, Object value) {
        if (StringUtils.isEmpty(field) || value == null || objectToUpdate == null)
            return false;

        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(objectToUpdateClass.getDeclaredFields()));

        for (Field f :
                fields) {
            if (Objects.equals(f.getName().toLowerCase(), field.toLowerCase()))
                try {
                    f.set(objectToUpdate, value);
                    return true;
                } catch (IllegalAccessException e) {
                    Logger.log(FIELDDONTEXISTS.setThrowableAndParticularMessage(e,
                            "Méthode : controllers.elisisplay.ElisisModelController." +
                                    "edit(T model, String field, Object value)." +
                                    "\n Champ : " + f.getName()));
                }
        }
        return false;
    }

    /**
     * Mettre à jour un champ d'un objet.
     * @param objectToUpdate Objet à mettre à jour.
     * @param field Champ à mettre à jour.
     * @param value Nouvelle valeur du champ.
     * @return {@code true} si la modification n'a retournée aucune erreur,
     * {@code false} dans le cas contraire.
     */
    public static boolean updateField(Object objectToUpdate, String field, Object value) {
        return updateField(objectToUpdate.getClass(), objectToUpdate, field, value);
    }

    /**
     * Mettre à jour les champs d'un objet.
     * @param objectToUpdate Objet à mettre à jour.
     * @param fieldsAndValues Succession de champ et de leur valeurs.
     *                        Exemple : "age", 21...
     * @return {@code true} si la modification n'a retournée aucune erreur,
     * {@code false} dans le cas contraire.
     */
    public static boolean updateFields(Object objectToUpdate, Object... fieldsAndValues) {
        return updateFields(objectToUpdate.getClass(), objectToUpdate, fieldsAndValues);
    }

    /**
     * Retourner le champ d'un objet inclu dans un autre objet.
     * @param globalObject Objet englobant l'objet à retourner.
     * @param fieldName Nom du champ de l'objet à retourner.
     * @return Le thamp inclu dans l'objet {@code globalObject} portant le
     * nom passé en paramêtre.
     */
    public static Field getField(Object globalObject, String fieldName){
        if (StringUtils.isEmpty(fieldName) || globalObject == null)
            return null;

        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(globalObject.getClass().getDeclaredFields()));

        for (Field f :
                fields) {
            if (Objects.equals(f.getName().toLowerCase(), fieldName.toLowerCase()))
                return f;
        }
        return null;
    }

    /**
     * Retourner à partir du nom du champ, un objet inclu dans un autre objet.
     * @param globalObject Objet englobant l'objet à retourner.
     * @param fieldName Nom du champ de l'objet à retourner.
     * @return L'objet inclu dans l'objet {@code globalObject} portant le
     * nom passé en paramêtre.
     */
    public static Object getObjectField(Object globalObject, String fieldName){
        Field field = getField(globalObject, fieldName);
        if (globalObject == null || StringUtils.isEmpty(fieldName) || field == null)
            return null;

        try {
            return field.get(globalObject);
        } catch (IllegalAccessException | NullPointerException e) {
            Logger.log(FIELDDONTEXISTS.setThrowableAndParticularMessage(e,
                    "Méthode : controllers.elisisplay.reflect.Utils." +
                            "getObjectField(Object globalObject, String fieldName)." +
                            "\n Champ : " + field.getName()));
        }

        return null;
    }
}
