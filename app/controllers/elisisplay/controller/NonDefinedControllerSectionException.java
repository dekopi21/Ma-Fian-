package controllers.elisisplay.controller;

/**
 * Created by Bienvenu on 28/08/2018 in controllers.elisisplay.
 */
public class NonDefinedControllerSectionException extends NullPointerException{

    private static final long serialVersionUID = 4162710183389028792L;

    /**
     * Contruit un {@code NullPointerException} avec aucun message.
     */
    public NonDefinedControllerSectionException() {
        super();
    }

    /**
     * Construit un {@code NonDefinedControllerSectionException} avec le
     * message spécifié.
     *
     * @param   s   Le message de l'exception.
     */
    public NonDefinedControllerSectionException(String s) {
        super(s);
    }
}
