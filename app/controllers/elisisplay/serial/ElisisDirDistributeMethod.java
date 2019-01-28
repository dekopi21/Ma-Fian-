package controllers.elisisplay.serial;

/**
 * Method used to distribute serial objects in several objects.
 */
public enum ElisisDirDistributeMethod {

    /**
     * Wait a date time event to write serial objects in other file.
     */
    DATE,

    /**
     * When a defined number of serial objects, are written in the file,
     * he is changed.
     */
    SERNUMBER,

    /**
     * Written on a file is stopped when the file gave certain size.
     */
    FIlESIZE,

    /**
     * Serials distribution on files is not managed by the ElisisSerialDir.
     */
    MANUAL,
    ;

}
