package controllers.elisisplay.reflect;

import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Utilisé au lieu de {@code HashMap} pour afficher les données d'un objet sous forme de tableau.
 * @see java.util.HashMap
 */
public class Match implements Serializable {

    /**
     * Nom de cet objet.
     *
     * Equivaut à la valeur toString de cet objet.
     */
    private String name;

    /**
     * Valeur de cet objet.
     */
    private Object value;

    public Match(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public Match(Field field, Object object){
        this.name = field.getName();
        try {
            this.value = field.get(object);
        } catch (IllegalAccessException iae){
            Logger.log(TypeLogMessageError.REFLECTIONERROR.setThrowableAndParticularMessage(iae,
                    "Impossible d'accéder au champ " + field + " de l'Objet " + object +
                            '.'));
            value = "";
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


}
