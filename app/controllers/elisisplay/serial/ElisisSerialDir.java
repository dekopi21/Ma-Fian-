package controllers.elisisplay.serial;

import com.sun.istack.internal.Nullable;
import controllers.elisisplay.io.ElisisFile;
import controllers.elisisplay.io.IElisisFile;
import controllers.elisisplay.io.IOUtils;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;

/**
 * This class is a completion for ElisisSerial[File].
 *
 * This can be use for logging, ie for objects who are serialized
 * regularly and then contains a consequent number of objects.
 *
 * The class Goal is to facilitate reading, searching and organisation
 * of same context serials files and distribute them on small size files
 * with daily save or by others events.
 *
 * @see ElisisSerial
 */
public class ElisisSerialDir<O extends Serializable> {

    /**
     * The serials file size must not greater than this field value.
     */
    private long fileMaxSize = -1;

    /**
     * Max serials objects which a serial file contains.
     */
    private long maxSerialObjects = -1;

    /**
     * Duration after which the serial file must be changed.
     */
    private Duration duration;

    /**
     * List of all dir serial files.
     */
    private List<ElisisFile> files;

    /**
     * File used actually to store serials objects.
     */
    private IElisisFile lastFile;

    /**
     * Directory absolute path.
     */
    private String originalAbsoluteDirPath;

    /**
     * Backup directory absolute path.
     */
    private String backupAbsoluteDirPath;

    /**
     * Serial files key.
     *
     * Used to give name to serial files.
     */
    private String filesKey;

    /**
     * ElisisDir Organizer.
     */
    private ElisisSerialDirOrganizer elisisSerialDirOrganizer;

    public ElisisSerialDir(String originalAbsoluteDirPath, String filesKey, ElisisDirDistributeMethod dirDistributeMethod){

    }
    public ElisisSerialDir(String originalAbsoluteDirPath, String filesKey){}
    public ElisisSerialDir(String originalAbsoluteDirPath, String filesKey, String backupAbsoluteDirPath, ElisisDirDistributeMethod dirDistributeMethod){}
    public ElisisSerialDir(String originalAbsoluteDirPath, String filesKey, String backupAbsoluteDirPath){}
    public ElisisSerialDir(String originalAbsoluteDirPath, ElisisDirDistributeMethod dirDistributeMethod){}

    private boolean loadOrganizer() {
        if (elisisSerialDirOrganizer == null) {
            elisisSerialDirOrganizer = (ElisisSerialDirOrganizer) PlaySerializer.getSerial(getOrganizerFile());
            return elisisSerialDirOrganizer != null;
        }
        return false;
    }

    private boolean saveOrganizer() {
        if (elisisSerialDirOrganizer == null)
            return false;

        return PlaySerializer.serialize(getOrganizerFile(), elisisSerialDirOrganizer);
    }

    public String getOrganizerFile() {
        return IOUtils.getFileAbsolutePath(originalAbsoluteDirPath,  ElisisSerialDirOrganizer.SERFILEPREFIXE + filesKey);
    }

    @Nullable
    public ElisisFile getOrganizerElisisFile() {
        try {
            return new ElisisFile(getOrganizerFile());
        } catch (FileNotFoundException e) {
            Logger.log((TypeLogMessageError.FILENOTFOUNDERROR).setThrowable(e));
            return null;
        }
    }

    @Override
    public String toString() {
        return originalAbsoluteDirPath;
    }
}
