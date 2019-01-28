package controllers.elisisplay.serial;

import controllers.elisisplay.io.ElisisFile;
import controllers.elisisplay.io.IElisisFile;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;

/**
 * This class must be used to organize Serials files or
 * a ElisisSerialDir class.
 */
public class ElisisSerialDirOrganizer implements Serializable {

    public static final ElisisDirDistributeMethod DEFAULTDIRDISTRIBUTEDMETHOD = ElisisDirDistributeMethod.SERNUMBER;

    public static final String SERFILEPREFIXE = "organizer";

    /**
     * Method used to distribute serials on several objects.
     */
    private ElisisDirDistributeMethod distributeMethod = DEFAULTDIRDISTRIBUTEDMETHOD;

    /**
     * Serial file which contains this
     */
    private transient IElisisFile thisFile;

    /**
     * Last file in use.
     */
    private IElisisFile lastFile;

    private long lastFileSize;

    private DateTime lastFileDateCreated;

    private long lastFileSerNumber;

    private IElisisSerial<IElisisFile> serials;

    /**
     * Init a new ElisisDirOrganizer object that is not init (file don't exists).
     * @param organizerFile File object.
     */
    public ElisisSerialDirOrganizer(ElisisFile organizerFile) throws FileAlreadyExistsException {
        if (organizerFile.exists())
            throw new FileAlreadyExistsException(organizerFile.toString());
        thisFile = organizerFile;
    }

    /**
     * Init a new ElisisDirOrganizer object that is not init (file don't exists).
     * @param organizerFile File object.
     * @param newDistributeMethod Define Method used to distribute serials on several objects.
     */
    public ElisisSerialDirOrganizer(ElisisDirDistributeMethod newDistributeMethod, ElisisFile organizerFile) throws FileAlreadyExistsException {
        if (organizerFile.exists())
            throw new FileAlreadyExistsException(organizerFile.toString());
        thisFile = organizerFile;
        distributeMethod = newDistributeMethod;
    }
}
