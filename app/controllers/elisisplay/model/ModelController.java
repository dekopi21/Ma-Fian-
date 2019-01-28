package controllers.elisisplay.model;

import controllers.elisisplay.utils.Utils;

import java.util.List;
import java.util.Map;

import static controllers.elisisplay.templating.generateHTML.Table.tableHTML;

/**
 * Abstract class for Model Controller.
 * <p>
 * This can be use instead of ElisisModelController class
 * if you want models methods to be accessible out of the ModelController
 * class.
 */
@SuppressWarnings("AbstractClassNeverImplemented")
public abstract class ModelController implements IModelController {

    private String lastCode;

    /**
     * @return Le code du dernier enregistrement de ce modèle.
     */
    public String getLastCode() {
        return lastCode;
    }

    /**
     * Lire la dernière entité créée de ce modèle.
     *
     * @param <T> Classe modèle de l'entité.
     * @return La dernière entité modèle la classe modèle {@code T}.
     */
    public  <T extends ElisisModel> T readLast() {

        try {
            return (T) read(lastCode);
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    public  <T extends ElisisModel> boolean createAndSave(T entity, Object... fieldsAndValues) {
        if (entity == null)
            return false;

        entity.code = codeGenerated(IElisisModel.DEFAULT_CODE_LENGTH);
        return createEntityFields(entity, fieldsAndValues) && entity.create();
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
            return classModel().generateCode();
        } catch (Exception ignored) {
            return codeGenerated(model().abbrev(), count());
        }
    }

    public String table(String tableClass, String tableId,
                        boolean onlyPublic, Map<String, String> tablesNaming, String... unless) {
        return tableHTML(all(), tableClass, tableId, onlyPublic, tablesNaming, unless);
    }

    public String table(String tableClass, String tableId,
                        boolean onlyPublic, String... unless) {
        return tableHTML(all(), tableClass, tableId, onlyPublic, unless);
    }
}
