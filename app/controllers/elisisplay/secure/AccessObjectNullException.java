package controllers.elisisplay.secure;

/**
 * Exception occured when a controller section in unknown.
 */
public class AccessObjectNullException extends NullPointerException {

    private static final long serialVersionUID = 5995409805456410871L;

    /**
     * Default constructor.
     */
    public AccessObjectNullException() {
    }

    /**
     *Default constructor who specify a message.
     *
     * @param   s   Le message de l'exception.
     */
    public AccessObjectNullException(String s) {
        super(s);
    }
}
