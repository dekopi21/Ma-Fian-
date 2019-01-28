package controllers.elisisplay.controller;

import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.model.ElisisModel;
import controllers.elisisplay.model.IElisisModel;

import java.util.ArrayList;

/**
 * Interface for default ModelController classes.
 */
public interface IElisisModelController {

    /**
     * Abbrev used in default when the model entity abbrev is unknown.
     * @return Abbrev used in default.
     */
    default String abbrev() {
        return "EM";
    }

    /**
     * Model Entity class.
     * @return Model Entity class.
     */
    Class<? extends ElisisModel> getClazz();

    /**
     * Delete the entity whose code equal parameter {@code code}.
     *
     * @param code Entity's code.
     * @return {@code true} if the entity have been dropped.
     */
    boolean _delete(String code);

    /**
     * Delete the entity whose code equal parameter {@code code}.
     *
     * @param code Entity's code.
     * @return The entity dropped.
     */
    <T extends ElisisModel> T __delete(String code);

    /**
     * Generate code for the next model entity.
     *
     * @param prefix Prefix
     * @param nbMax  Maximal length of code.
     * @return code for the next model entity.
     */
    String codeGenerated(String prefix, int nbMax);

    /**
     * Generate code for the next model entity.
     *
     * @param prefix Prefix
     * @param suffix Suffix
     * @param nbMax  Maximal length of code.
     * @return code for the next model entity.
     */
    String codeGenerated(String prefix, long suffix, int nbMax);

    /**
     * Generate code for the next model entity.
     *
     * @param prefix Prefix
     * @param suffix Suffix
     * @return code for the next model entity.
     */
    String codeGenerated(String prefix, long suffix);

    /**
     * Generate code for the next model entity.
     *
     * @param nbMax Maximal length of code.
     * @return code for the next model entity.
     */
    String codeGenerated(int nbMax);

    /**
     * Read the entity which field value {@code code} is passed in parameter.
     *
     * @param code Entity's code.
     * @return the entity which field value {@code code} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    ElisisModel read(String code);

    /**
     * Read the entity which field value {@code attribute} is passed in parameter.
     *
     * @param attribute attribute
     * @param value     Entity's value for this attribute.
     * @return the entity which field value {@code attribute} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    ElisisModel readUnique(String attribute, String value);

    /**
     * Read the entity which field value {@code attribute} is passed in parameter.
     *
     * @param attribute attribute
     * @param value     Entity's value for this attribute.
     * @return the entity which field value {@code attribute} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    ElisisModel readUnique(String attribute, Object value);

    /**
     * Get all entities for which this attribute field value correspond
     * to the value passed.
     *
     * @param attribute Entities's value for this attribute.
     * @param value     Value
     * @return Entities which values correspond,
     * or null if there is any.
     */
    <T extends ElisisModel> ArrayList<T> readAll(String attribute, String value);

    /**
     * Get all entities for which this attribute field value correspond
     * to the value passed.
     *
     * @param attribute Entities's value for this attribute.
     * @param value     Value
     * @param nbMax     nbMax of entities to return.
     * @return Entities which values correspond,
     * or null if there is any.
     */
    <T extends ElisisModel> ArrayList<T> readAll(String attribute, String value, int nbMax);

    /**
     * Get all entities for which this attribute field value correspond
     * to the value passed.
     *
     * @param attribute Entities's value for this attribute.
     * @param value     Value
     * @return Entities which values correspond,
     * or null if there is any.
     */
    <T extends ElisisModel> ArrayList<T> readAll(String attribute, Object value);

    /**
     * Get all entities for which this attribute field value correspond
     * to the value passed.
     *
     * @param attribute Entities's value for this attribute.
     * @param value     Value
     * @param nbMax     nbMax of entities to return.
     * @return Entities which values correspond,
     * or null if there is any.
     */
    <T extends ElisisModel> ArrayList<T> readAll(String attribute, Object value, int nbMax);

    <T extends ElisisModel> boolean updateEntityFields(T entity, Object... fieldsAndValues);

    <T extends ElisisModel> boolean createEntityFields(T entity, Object... fieldsAndValues);

    <T extends ElisisModel> boolean updateEntityField(T entity, String field, Object value);

    <T extends ElisisModel> boolean edit(String entityCode, String field, Object value);

    <T extends ElisisModel> boolean edit(T entity, String field, Object value);

    <T extends ElisisModel> boolean edit(String entityCode, Object... fieldsAndValues);

    <T extends ElisisModel> boolean edit(T entity, Object... fieldsAndValues);

    <T extends ElisisModel> boolean editAndSave(String entityCode, Object... fieldsAndValues);

    <T extends ElisisModel> boolean editAndSave(T entity, Object... fieldsAndValues);

    <T extends ElisisModel> boolean editAndSave(T entity, String field, Object value);

    <T extends ElisisModel> boolean editAndSave(String entityCode, String field, Object value);

    <T extends ElisisModel> boolean createAndSave(T entity, Object... fieldsAndValues);

    static <T extends ElisisModel> boolean createOrSave(T entity) {
        if (StringUtils.isEmpty(entity.code)) {
            entity.code = new ElisisModelController(entity.getClass()).codeGenerated(IElisisModel.DEFAULT_CODE_LENGTH);
            return entity.validateAndCreate();
        } else {
            return entity.validateAndSave();
        }
    }

    /**
     * Obtenir le nombre d'entités enregistrés dans la base de données pour
     * cette classe modèle.
     *
     * @return Le nombre d'enregistrements de ce modèle dans la base de
     * données.
     */
    long count();

    /**
     * Obtenir le nombre d'entités enregistrés dans la base de données pour
     * cette classe modèle.
     *
     * @return Le nombre d'enregistrements de ce modèle dans la base de
     * données.
     */
    <T extends ElisisModel> ArrayList<T> all();

    /**
     * Obtenir le nombre d'entités enregistrés dans la base de données pour
     * cette classe modèle.
     *
     * @return Le nombre d'enregistrements de ce modèle dans la base de
     * données.
     */
    <T extends ElisisModel> ArrayList<T> all(int nbMax);

    /**
     * Obtenir le nombre d'entités enregistrés dans la base de données pour
     * cette classe modèle.
     *
     * @return Le nombre d'enregistrements de ce modèle dans la base de
     * données.
     */
    <T extends ElisisModel> ArrayList<T> all(int page, int length);

    /**
     * Appeler une méthode de la classe modèle qui ne prend aucun paramêtre.
     *
     * @param methodName Nom de la méthode.
     * @return L'objet retourné par la méthode.
     */
    Object invokeNoParametersMethod(String methodName);

    /**
     * Appeler une méthode de la classe modèle qui ne prend aucun paramêtre.
     *
     * @param methodName Nom de la méthode.
     * @param object     Objet sur lequel cette méthode sera appelé.
     * @return L'objet retourné par la méthode.
     */
    <T extends ElisisModel> Object invokeNoParametersMethod(String methodName, T object);

    /**
     * Lire l'entité dont le code hashé en Md5 est passé en paramêtre.
     * <p>
     * Est utile dans le navigateur pour ne pas afficher des données trop suggestives.
     *
     * @param <T> Classe modèle de l'entité.
     * @return L'entité dont le code hashé en Md5 est égale au paramêtre
     * {@code code}.
     */
    <T extends ElisisModel> T readByHashMd5(String hashCode);

    /**
     * Lire la dernière entité créée de ce modèle.
     *
     * @param <T> Classe modèle de l'entité.
     * @return La dernière entité modèle la classe modèle {@code T}.
     */
    <T extends ElisisModel> T readLast();

    /**
     * Permet de tester l'existence d'un code sans retourner (lire)
     * la valeur de l'entité à laquelle il correspond.
     *
     * @param code Valeur du code dont il faut tester l'existence.
     * @param <T>  Classe modèle de l'entité
     * @return {@code true} si l'entité existe,
     * {@code false} dans le cas contraire.
     */
    <T extends ElisisModel> boolean exists(String code);

    /**
     * Permet de tester l'existence d'un code sans retourner (lire)
     * la valeur de l'entité à laquelle il correspond.
     *
     * @param field Attribut du modèle dont la valeur est passé en paramêtre.
     * @param value Valeur de l'attribut pour l'entité à lire.
     * @param <T>   Classe modèle de l'entité
     * @return {@code true} si l'entité existe,
     * {@code false} dans le cas contraire.
     */
    <T extends ElisisModel> boolean exists(String field, String value);

    /**
     * Permet de tester l'existence d'un code sans retourner (lire)
     * la valeur de l'entité à laquelle il correspond.
     *
     * @param field Attribut du modèle dont la valeur est passé en paramêtre.
     * @param value Valeur de l'attribut pour l'entité à lire.
     * @param <T>   Classe modèle de l'entité
     * @return {@code true} si l'entité existe,
     * {@code false} dans le cas contraire.
     */
    <T extends ElisisModel> boolean exists(String field, Object value);


    /**
     * @return Le code du dernier enregistrement de ce modèle.
     */
    String getLastCode();

    @Override
    String toString();
}
