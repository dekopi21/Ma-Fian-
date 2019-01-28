package controllers.elisisplay.utils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Permet d'implémenter les boutons Suivant, Précédent avec plus de facilités.
 */
public class NavigateLink {

    public int actualPosition = 0;

    public boolean isPreviousActive = false;

    public boolean isNextActive = false;

    public LinkedList<HashMap<String, String>> urls = new LinkedList<>();
}
