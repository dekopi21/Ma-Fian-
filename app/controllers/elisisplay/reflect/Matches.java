package controllers.elisisplay.reflect;

import java.util.ArrayList;

/**
 * Utilisé au lieu de {@code HashMap} pour afficher les données d'une liste d'objets sous forme de tableau.
 * @see java.util.HashMap
 */
public class Matches {

    /**
     * Nom du champ cet objet.
     *
     * Equivaut à la valeur toString de cet objet.
     */
    public String name;

    /**
     * Valeur de cet objet.
     */
    public ArrayList<Object> values;
}
