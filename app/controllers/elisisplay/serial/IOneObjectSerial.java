package controllers.elisisplay.serial;

import java.io.File;
import java.io.Serializable;

/**
 * Interface for one object serial classes.
 */
public interface IOneObjectSerial extends Serializable {

    /**
     * Init the serial file.
     * <p>
     * This try to load data from the serial file if it
     * already exists.
     */
    void init();

    /**
     * Save the state of serial object to the serial file.
     */
    void save();

    /**
     * Load ob serial object, data contained in the serial file.
     *
     * @return {@code true} if the serial data have been load in object file.
     */
    boolean load();

    /**
     * Flush objects to the serial file.
     *
     * @return <tt>true</tt> If the objects have been flushed.
     */
    boolean flush();

    /**
     * Get File Object from Serial file parameters (Path, FileName).
     *
     * @return File Object from Serial file parameters.
     */
    File getFile();

    /**
     * Check if the serial file exists.
     *
     * @return {@code true} if the serial file exists.
     */
    boolean exists();

    /**
     * Check if the serial directory exists.
     *
     * @return {@code true} if the serial directory exists.
     */
    boolean dirExists();
}
