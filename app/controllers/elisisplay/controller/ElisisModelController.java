package controllers.elisisplay.controller;

import controllers.CRUD;
import controllers.elisisplay.crypto.Crypto;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;
import controllers.elisisplay.model.ElisisModel;
import controllers.elisisplay.model.IElisisModel;
import controllers.elisisplay.utils.Utils;
import play.db.jpa.GenericModel.JPAQuery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements actions make by a controller on his model.
 * <p>
 * By default in Play ModelControllers has the same name as the model with a 's' at the end.
 * <p>
 * This Class is different of ElisisController because all actions in are made on the model (entity).
 */

//TODO : Log errors.
@SuppressWarnings({"MethodReturnOfConcreteClass", "CastToConcreteClass", "ReturnOfNull"})
public class ElisisModelController extends CRUD implements IElisisModelController{

    /**
     * Class model|entity on which actions are done.
     */
    private Class<? extends ElisisModel> clazz;

    /**
     * Last code generate on model creation.
     * <p>
     * Correspond on last entity created code.
     */
    private String lastCode;

    /**
     * Construct ModelController from the entity class.
     *
     * @param clazz Class of entity|model.
     */
    public ElisisModelController(Class<? extends ElisisModel> clazz) {
        this.clazz = clazz;
    }

    /**
     * Model Entity class.
     *
     * @return Model Entity class.
     */
    @Override
    public Class<? extends ElisisModel> getClazz() {
        return clazz;
    }

    /**
     * Delete the entity whose code equal parameter {@code code}.
     *
     * @param code Entity's code.
     * @return {@code true} if the entity have been dropped.
     */
    public boolean _delete(String code) {
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
    public <T extends ElisisModel> T __delete(String code) {
        return (read(code)).delete();
    }

    /**
     * Generate code for the next model entity.
     *
     * @param prefix Prefix
     * @param nbMax  Maximal length of code.
     * @return code for the next model entity.
     */
    public String codeGenerated(String prefix, int nbMax) {
        return codeGenerated(prefix, count(), nbMax);
    }

    /**
     * Generate code for the next model entity.
     *
     * @param prefix Prefix
     * @param suffix Suffix
     * @param nbMax  Maximal length of code.
     * @return code for the next model entity.
     */
    public String codeGenerated(String prefix, long suffix, int nbMax) {
        long count = suffix;
        String code;

        //While any model get this code.
        //This is useful when entities are deleted and the
        //count at the end of the code does not correspond to
        //the number of entities in this table.
        do {
            ++count;
            code = Utils.generateCode(prefix, count, nbMax);
        }
        while (exists(code));

        lastCode = code;
        return code;
    }

    /**
     * Generate code for the next model entity.
     *
     * @param prefix Prefix
     * @param suffix Suffix
     * @return code for the next model entity.
     */
    public String codeGenerated(String prefix, long suffix) {
        return codeGenerated(prefix, suffix, IElisisModel.DEFAULT_CODE_LENGTH);
    }

    /**
     * Generate code for the next model entity.
     *
     * @param nbMax Maximal length of code.
     * @return code for the next model entity.
     */
    public String codeGenerated(int nbMax) {
        try {
            //ISSUE : An error occur when a the abbrev() method is not defined in the subclass.
            return codeGenerated((String) invokeNoParametersMethod("abbvr", clazz.getConstructor().newInstance()), nbMax);
        } catch (Exception ignored) {
            return codeGenerated(abbrev(), count());
        }
    }

    /**
     * Read the entity which field value {@code code} is passed in parameter.
     *
     * @param code Entity's code.
     * @return the entity which field value {@code code} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    public ElisisModel read(String code) {
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
    public ElisisModel readUnique(String attribute, String value) {
        return (ElisisModel) readUnique(attribute, (Object) value);
    }

    /**
     * Read the entity which field value {@code attribute} is passed in parameter.
     *
     * @param attribute attribute
     * @param value     Entity's value for this attribute.
     * @return the entity which field value {@code attribute} is passed in parameter,
     * or {@code null} if such entity does not exists.
     */
    public ElisisModel readUnique(String attribute, Object value) {
        if (StringUtils.isEmpty(attribute) || (value == null))
            return null;

        try {
            Method method = clazz.getDeclaredMethod("find", String.class, Object[].class);

            //Objects send to method find.
            Object[] objects = new Object[1];
            objects[0] = value;

            JPAQuery result = (JPAQuery) method.invoke(null, attribute, objects);
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
    public <T extends ElisisModel> ArrayList<T> readAll(String attribute, String value) {
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
    public <T extends ElisisModel> ArrayList<T> readAll(String attribute, String value, int nbMax) {
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
    public <T extends ElisisModel> ArrayList<T> readAll(String attribute, Object value) {
        try {
            Method method = clazz.getDeclaredMethod("find", String.class, Object[].class);

            //Objects send to the find method.
            Object[] objects = new Object[1];
            objects[0] = value;

            JPAQuery result = (JPAQuery) method.invoke(null, attribute, objects);
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
    public <T extends ElisisModel> ArrayList<T> readAll(String attribute, Object value, int nbMax) {
        try {
            Method method = clazz.getDeclaredMethod("find", String.class, Object[].class);

            //Objects send to method find.
            Object[] objects = new Object[1];
            objects[0] = value;

            JPAQuery result = (JPAQuery) method.invoke(null, attribute, objects);
            if (result != null)
                return new ArrayList<>(result.fetch(nbMax));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    public  <T extends ElisisModel> boolean updateEntityFields(T entity, Object... fieldsAndValues) {
        return (entity != null) && (fieldsAndValues != null) && controllers.elisisplay.reflect.Utils.updateFields(clazz, entity, fieldsAndValues);
    }

    public  <T extends ElisisModel> boolean createEntityFields(T entity, Object... fieldsAndValues) {
        return (entity != null) && (fieldsAndValues != null) && controllers.elisisplay.reflect.Utils.updateFields(clazz, entity, fieldsAndValues);
    }

    public  <T extends ElisisModel> boolean updateEntityField(T entity, String field, Object value) {
        if (entity != null) if (!StringUtils.isEmpty(field))
            if (value != null) if (controllers.elisisplay.reflect.Utils.updateField(clazz, entity, field, value)) return true;
        return false;
    }

    public <T extends ElisisModel> boolean edit(String entityCode, String field, Object value) {
        T entity = (T) read(entityCode);
        return edit(entity, field, value);
    }

    public <T extends ElisisModel> boolean edit(T entity, String field, Object value) {
        return (entity != null) && updateEntityField(entity, field, value);
    }

    public <T extends ElisisModel> boolean edit(String entityCode, Object... fieldsAndValues) {
        T entity = (T) read(entityCode);
        return edit(entity, fieldsAndValues);
    }

    public <T extends ElisisModel> boolean edit(T entity, Object... fieldsAndValues) {
        return (entity != null) && updateEntityFields(entity, fieldsAndValues);
    }

    public <T extends ElisisModel> boolean editAndSave(String entityCode, Object... fieldsAndValues) {
        T entity = (T) read(entityCode);
        return editAndSave(entity, fieldsAndValues) && entity.validateAndSave();
    }

    public <T extends ElisisModel> boolean editAndSave(T entity, Object... fieldsAndValues) {
        return (entity != null) && updateEntityFields(entity, fieldsAndValues) && entity.validateAndSave();
    }

    public <T extends ElisisModel> boolean editAndSave(T entity, String field, Object value) {
        return ((entity != null) && edit(entity, field, value) && entity.validateAndSave());
    }

    public <T extends ElisisModel> boolean editAndSave(String entityCode, String field, Object value) {
        T entity = (T) read(entityCode);
        return editAndSave(entity, field, value);
    }

    public <T extends ElisisModel> boolean createAndSave(T entity, Object... fieldsAndValues) {
        if (entity == null)
            return false;

        entity.code = codeGenerated(IElisisModel.DEFAULT_CODE_LENGTH);
        return createEntityFields(entity, fieldsAndValues) && entity.create();
    }

    public static <T extends ElisisModel> boolean createOrSave(T entity) {
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
    public long count() {
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
    public <T extends ElisisModel> ArrayList<T> all() {
        try {
            JPAQuery result = (JPAQuery) invokeNoParametersMethod("all");
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
    public <T extends ElisisModel> ArrayList<T> all(int nbMax) {
        try {
            JPAQuery result = (JPAQuery) invokeNoParametersMethod("all");
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
    public <T extends ElisisModel> ArrayList<T> all(int page, int length) {
        try {
            JPAQuery result = (JPAQuery) invokeNoParametersMethod("all");
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
    public Object invokeNoParametersMethod(String methodName) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, (Class<?>[]) null);
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
    public <T extends ElisisModel> Object invokeNoParametersMethod(String methodName, T object) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, (Class<?>[]) null);
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
    public <T extends ElisisModel> T readByHashMd5(String hashCode) {
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
     * Lire la dernière entité créée de ce modèle.
     *
     * @param <T> Classe modèle de l'entité.
     * @return La dernière entité modèle la classe modèle {@code T}.
     */
    public <T extends ElisisModel> T readLast() {

        try {
            return (T) read(lastCode);
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
    public <T extends ElisisModel> boolean exists(String code) {
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
    public <T extends ElisisModel> boolean exists(String field, String value) {
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
    public <T extends ElisisModel> boolean exists(String field, Object value) {
        return readUnique(field, value) != null;
    }


    /**
     * @return Le code du dernier enregistrement de ce modèle.
     */
    public String getLastCode() {
        return lastCode;
    }

    @Override
    public String toString() {
        return clazz.toGenericString();
    }
}
