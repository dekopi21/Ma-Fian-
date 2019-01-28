package controllers.elisisplay.attendance.observator;

/**
 * Interface of Observators Classes.
 */
public interface IObserver {
    /**
     * Serial actual objects to the serial file.
     * @return {@code true} if objects are really serialized,
     * {@code false} either.
     */
    boolean flush();

    /**
     * Load the objects in the file to te load object.
     * @return {@code true} if objects are really load,
     * {@code false} either.
     */
    boolean load();

    /**
     * Init the serialisation the path, the file, the objects.
     */
    void init();

    /**
     * Init the serialisation the path, the file, the objects.
     *
     * The function returns only if the file is init (even if it is
     * possible).
     */
    void safeInit();
}
