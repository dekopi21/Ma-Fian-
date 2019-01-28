package controllers.elisisplay.model;

import controllers.elisisplay.controller.ElisisModelController;
import controllers.elisisplay.controller.IElisisModelController;
import controllers.elisisplay.crypto.Crypto;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;
import controllers.elisisplay.reflect.Utils;
import play.db.jpa.GenericModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for Model Controller.
 * <p>
 * This can be use instead of ElisisModelController class
 * if you want models methods to be accessible out of the ModelController
 * class.
 */

//This class will be used by models class in concrete project.
@SuppressWarnings({"InterfaceMayBeAnnotatedFunctional", "InterfaceNeverImplemented", "Duplicates"})
public interface IModelController {



    /**
     * ModelController used by this class.
     *
     * @return ModelController used by this class.
     */
    IElisisModelController model();

    /**
     * A class model object from the model entity.
     *
     * This is to create shortcut for some methods.
     * @return A class model object from the model entity.
     */
    ElisisModel classModel();

    /**
     * Model Entity class.
     *
     * @return Model Entity class.
     */
    default IElisisModelController getClazz() {
        return model();
    }

    /**
     * Delete the entity whose code equal parameter {@code code}.
     *
     * @param code Entity's code.
     * @return {@code true} if the entity have been dropped.
     */
    default boolean _delete(String code) {
        try {
            return read(code).delete() != null;
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    /**
     * Delete the entity whose code equal parameter {@code code}.
     *
     * @param code Entity's code.
     * @return The entity dropped.
     */
    default <T extends ElisisModel> T __delete(String code) {
        return (read(code)).delete();
    }

    /**
     * Read the entity which field value {@code code} is passed in parameter.
     *
     * @param code Entity's code.
     * @return the entity which field value {@code code} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    default ElisisModel read(String code) {
        return readUnique("code", code);
    }

    /**
     * Read the entity which field value {@code attribute} is passed in parameter.
     *
     * @param attribute attribute
     * @param value     Entity's value for this attribute.
     * @return the entity which field value {@code attribute} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    default ElisisModel readUnique(String attribute, String value) {
        return readUnique(attribute, (Object) value);
    }

    /**
     * Read the entity which field value {@code attribute} is passed in parameter.
     *
     * @param attribute attribute
     * @param value     Entity's value for this attribute.
     * @return the entity which field value {@code attribute} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    default ElisisModel readUnique(String attribute, Object value) {
        if (StringUtils.isEmpty(attribute) || (value == null))
            return null;

        try {
            Method method = model().getClazz().getDeclaredMethod("find", String.class, Object[].class);

            //Objects send to method find.
            Object[] objects = new Object[1];
            objects[0] = value;

            GenericModel.JPAQuery result = (GenericModel.JPAQuery) method.invoke(null, attribute, objects);
            if (result != null)
                return result.first();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Logger.log(TypeLogMessageError.ENTITYFINDINGERROR.setThrowable(e));
        }
        return null;
    }

    /**
     * Get all entities for which this attribute field value correspond
     * to the value passed.
     *
     * @param attribute Entities's value for this attribute.
     * @param value     Value
     * @return Entities which values correspond,
     * or null if there is any.
     */
    default <T extends ElisisModel> ArrayList<T> readAll(String attribute, String value) {
        return readAll(attribute, (Object) value);
    }

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
    default <T extends ElisisModel> ArrayList<T> readAll(String attribute, String value, int nbMax) {
        return readAll(attribute, (Object) value, nbMax);
    }

    /**
     * Get all entities for which this attribute field value correspond
     * to the value passed.
     *
     * @param attribute Entities's value for this attribute.
     * @param value     Value
     * @return Entities which values correspond,
     * or null if there is any.
     */
    default <T extends ElisisModel> ArrayList<T> readAll(String attribute, Object value) {
        try {
            Method method = model().getClazz().getDeclaredMethod("find", String.class, Object[].class);

            //Objects send to the find method.
            Object[] objects = new Object[1];
            objects[0] = value;

            GenericModel.JPAQuery result = (GenericModel.JPAQuery) method.invoke(null, attribute, objects);
            if (result != null)
                return new ArrayList<>(result.fetch());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

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
    default <T extends ElisisModel> ArrayList<T> readAll(String attribute, Object value, int nbMax) {
        try {
            Method method = model().getClazz().getDeclaredMethod("find", String.class, Object[].class);

            //Objects send to method find.
            Object[] objects = new Object[1];
            objects[0] = value;

            GenericModel.JPAQuery result = (GenericModel.JPAQuery) method.invoke(null, attribute, objects);
            if (result != null)
                return new ArrayList<>(result.fetch(nbMax));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    default <T extends ElisisModel> boolean updateEntityFields(T entity, Object... fieldsAndValues) {
        return (entity != null) && (fieldsAndValues != null) && Utils.updateFields(model(), entity, fieldsAndValues);
    }

    default <T extends ElisisModel> boolean createEntityFields(T entity, Object... fieldsAndValues) {
        return (entity != null) && (fieldsAndValues != null) && Utils.updateFields(model(), entity, fieldsAndValues);
    }

    default <T extends ElisisModel> boolean updateEntityField(T entity, String field, Object value) {
        if (entity != null) if (!StringUtils.isEmpty(field))
            if (value != null) return Utils.updateField(classModel().getClass(), entity, field, value);
        return false;
    }

    default <T extends ElisisModel> boolean edit(String entityCode, String field, Object value) {
        T entity = (T) read(entityCode);
        return edit(entity, field, value);
    }

    default <T extends ElisisModel> boolean edit(T entity, String field, Object value) {
        return (entity != null) && updateEntityField(entity, field, value);
    }

    default <T extends ElisisModel> boolean edit(String entityCode, Object... fieldsAndValues) {
        T entity = (T) read(entityCode);
        return edit(entity, fieldsAndValues);
    }

    default <T extends ElisisModel> boolean edit(T entity, Object... fieldsAndValues) {
        return (entity != null) && updateEntityFields(entity, fieldsAndValues);
    }

    default <T extends ElisisModel> boolean editAndSave(String entityCode, Object... fieldsAndValues) {
        T entity = (T) read(entityCode);
        return editAndSave(entity, fieldsAndValues) && entity.validateAndSave();
    }

    default <T extends ElisisModel> boolean editAndSave(T entity, Object... fieldsAndValues) {
        return (entity != null) && updateEntityFields(entity, fieldsAndValues) && entity.validateAndSave();
    }

    default <T extends ElisisModel> boolean editAndSave(T entity, String field, Object value) {
        return ((entity != null) && edit(entity, field, value) && entity.validateAndSave());
    }

    default <T extends ElisisModel> boolean editAndSave(String entityCode, String field, Object value) {
        T entity = (T) read(entityCode);
        return editAndSave(entity, field, value);
    }

    default <T extends ElisisModel> boolean createOrSave(T entity) {
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
    default long count() {
        try {
            return (long) invokeNoParametersMethod("count");
        } catch (RuntimeException ignored) {
            return -1;
        }
    }

    /**
     * Obtenir le nombre d'entités enregistrés dans la base de données pour
     * cette classe modèle.
     *
     * @return Le nombre d'enregistrements de ce modèle dans la base de
     * données.
     */
    default <T extends ElisisModel> ArrayList<T> all() {
        try {
            GenericModel.JPAQuery result = (GenericModel.JPAQuery) invokeNoParametersMethod("all");
            return new ArrayList<T>(result.fetch());
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    /**
     * Obtenir le nombre d'entités enregistrés dans la base de données pour
     * cette classe modèle.
     *
     * @return Le nombre d'enregistrements de ce modèle dans la base de
     * données.
     */
    default <T extends ElisisModel> ArrayList<T> all(int nbMax) {
        try {
            GenericModel.JPAQuery result = (GenericModel.JPAQuery) invokeNoParametersMethod("all");
            return new ArrayList<T>(result.fetch(nbMax));
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    /**
     * Obtenir le nombre d'entités enregistrés dans la base de données pour
     * cette classe modèle.
     *
     * @return Le nombre d'enregistrements de ce modèle dans la base de
     * données.
     */
    default <T extends ElisisModel> ArrayList<T> all(int page, int length) {
        try {
            GenericModel.JPAQuery result = (GenericModel.JPAQuery) invokeNoParametersMethod("all");
            return new ArrayList<T>(result.fetch(page, length));
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    /**
     * Appeler une méthode de la classe modèle qui ne prend aucun paramêtre.
     *
     * @param methodName Nom de la méthode.
     * @return L'objet retourné par la méthode.
     */
    default Object invokeNoParametersMethod(String methodName) {
        try {
            Method method = model().getClazz().getDeclaredMethod(methodName, (Class<?>[]) null);
            if (method != null)
                return method.invoke(null, (Object[]) null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    /**
     * Appeler une méthode de la classe modèle qui ne prend aucun paramêtre.
     *
     * @param methodName Nom de la méthode.
     * @param object     Objet sur lequel cette méthode sera appelé.
     * @return L'objet retourné par la méthode.
     */
    default <T extends ElisisModel> Object invokeNoParametersMethod(String methodName, T object) {
        try {
            Method method = model().getClazz().getDeclaredMethod(methodName, (Class<?>[]) null);
            if (method != null)
                return method.invoke(object, (Object[]) null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    /**
     * Lire l'entité dont le code hashé en Md5 est passé en paramêtre.
     * <p>
     * Est utile dans le navigateur pour ne pas afficher des données trop suggestives.
     *
     * @param <T> Classe modèle de l'entité.
     * @return L'entité dont le code hashé en Md5 est égale au paramêtre
     * {@code code}.
     */
    default <T extends ElisisModel> T readByHashMd5(String hashCode) {
        try {
            List<T> all = (List<T>) invokeNoParametersMethod("findAll");

            Iterable<ElisisModel> models = new ArrayList<ElisisModel>(all);
            for (ElisisModel model :
                    models) {
                if (Crypto.isHash(model.code, hashCode))
                    return (T) model;
            }
            return null;
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    /**
     * Permet de tester l'existence d'un code sans retourner (lire)
     * la valeur de l'entité à laquelle il correspond.
     *
     * @param code Valeur du code dont il faut tester l'existence.
     * @param <T>  Classe modèle de l'entité
     * @return {@code true} si l'entité existe,
     * {@code false} dans le cas contraire.
     */
    default <T extends ElisisModel> boolean exists(String code) {
        return read(code) != null;
    }

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
    default <T extends ElisisModel> boolean exists(String field, String value) {
        return readUnique(field, value) != null;
    }

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
    default <T extends ElisisModel> boolean exists(String field, Object value) {
        return readUnique(field, value) != null;
    }
}
