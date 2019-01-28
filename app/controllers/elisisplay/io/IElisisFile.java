package controllers.elisisplay.io;

import java.io.File;
import java.io.Serializable;

/**
 * Interface for ElisisFile classes.
 */
public interface IElisisFile extends Serializable {

    /**
     * @return Original file complete absolute directory path.
     */
    String originalAbsolutePath();

    /**
     * @return Backup field complete absolute directory path.
     */
    String originalAbsoluteDirPath();

    /**
     * @return File complete name.
     */
    String completeName();

    /**
     * This field is used to define the name of the file
     * or his corresponding key in some maps.
     *
     * @return File key.
     */
    String key();

    /**
     * @return The file denote by the paremeters of this class.
     */
    File getFile();

    /**
     * Create the file denote by the class parameters.
     * @return {@code true} if file don't exists and have been created.
     */
    boolean create();

    /**
     * Create the file denote by the class parameters.
     * @return {@code true} if file exists after this operation.
     */
    boolean createSafe();

    /**
     * Denote if the file already exists or not.
     * @return {@code true} if this file already exists.
     */
    boolean exists();

    /**
     * Denote if the dir of this file already exists or not.
     *
     * @return {@code true} if the dir of this file already exists.
     */
    boolean dirExists();
}
