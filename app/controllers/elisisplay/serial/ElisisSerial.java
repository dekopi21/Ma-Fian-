package controllers.elisisplay.serial;

import controllers.elisisplay.io.IOUtils;
import controllers.elisisplay.io.Serialization;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;
import controllers.elisisplay.utils.Utils;
import org.joda.time.DateTime;
import play.Play;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serial object.
 * <p>
 * Serials class must extend this or use Serial.
 *
 * @see Serial
 */
public abstract class ElisisSerial<S extends Serializable> implements IElisisSerial<S> {

    /**
     * Value used as relative directory when the file parameters are not correct.
     */
    private static final String DEFAULT_SERIALS_DIR = "serials";

    /**
     * Log message used when Serial file init failed.
     */
    private static final String MSG_INIT_FAIL = "\"Initialisation failed.\"";

    private static final long serialVersionUID = 1000000000000000000L;

    /**
     * Log message used when Serial file loading failed.
     */
    private static final String LOAD_ERROR_MSG = "Load Serial data failed.";

    /**
     * Contains last objects load from the serial file.
     */
    private ArrayList<S> objectsLoad;

    /**
     * Contains news objects to add to the load one.
     */
    private ArrayList<S> newObjectsToFlush = new ArrayList<>();

    /**
     * Get File Object from Serial file parameters (Path, FileName).
     *
     * @return File Object from Serial file parameters.
     */
    @Override
    public File getFile() {
        return IOUtils.getFile(dirPlayPath(), filename());
    }

    /**
     * Check if the serial file exists.
     *
     * @return {@code true} if the serial file exists.
     */
    @Override
    public boolean exists() {
        return IOUtils.filePlayExists(dirPlayPath(), filename());
    }

    /**
     * Check if the serial directory exists.
     *
     * @return {@code true} if the serial directory exists.
     */
    @Override
    public boolean dirExists() {
        return IOUtils.dirPlayExists(dirPlayPath());
    }

    /**
     * @return The complete filename of the serialization file of this serial object.
     */
    public abstract String filename();

    /**
     * @return The directory where this object will or has been serialized path.
     */
    public abstract String dirPlayPath();

    /**
     * @return Complete file path used to serialize the object.
     */
    public String fileCompletePath() {
        File file = IOUtils.getFile(dirPlayPath(), filename());
        return (file != null) ? file.getAbsolutePath()
                : safeFileCompletePath();
    }

    /**
     * Get a absolute Play file path who is valid.
     * <p>
     * This is use when the file path is not right.
     *
     * @return A valid absolute Play file path
     */
    public String safeFileCompletePath() {
        return new File(Play.applicationPath.getAbsolutePath(),
                DEFAULT_SERIALS_DIR + File.separatorChar + filename()).getAbsolutePath();
    }

    /**
     * Default Constructor
     *
     * @param initSerial Denote if the serial file must be init or not.
     */
    protected ElisisSerial(boolean initSerial) {
        if (initSerial)
            init();
    }

    /**
     * Constructor who must be used by Serial class.
     *
     * @param filename    Serial file complete name.
     * @param dirPlayPath Relative Play directory path or the file.
     * @param initSerial  Denote if the serial file must be init or not.
     * @see Serial
     */
    protected ElisisSerial(String filename, String dirPlayPath, boolean initSerial) {
        if (initSerial)
            init(filename, dirPlayPath);
    }

    /**
     * Init the serial file.
     * <p>
     * This try to load data from the serial file if it
     * already exists.
     */
    public final void init() {
        //Try to get the Complete File Path. If the file doesn't exists but can be created,
        //he is.
        String fileCompleteAbsolutePath = IOUtils.getPlayFileAbsolutePathSafe(dirPlayPath(), filename());

        if (StringUtils.isEmpty(fileCompleteAbsolutePath)) {
            Logger.log(TypeLogMessageError.CANTCREATEFILE.setMessage("\"This file is not found, and can't be created.\" +" +
                    "                        \"Directory : \" + dirPlayPath() " +
                    "                        \" File : \" + filename()"));
            return;
        }

        //There are no news objects to flush in the serial file.
        if (newObjectsToFlush.isEmpty()) {
            //If objectsLoad equal null, that denote that this
            //field have been never updated yet. So we will try to load data
            //from the serial file.
            if ((objectsLoad == null))
                forceLoad(MSG_INIT_FAIL);

                //There is no news objects to flush.
                // This update the serial file..
            else
                flush();

        }

        //There is news objects to flush.
        else {
            //But it seem like serial file has never been loaded.
            //So the serial file is load, the news objects are added, and we flush all objects.
            if (objectsLoad == null) {
                forceLoad(MSG_INIT_FAIL);
                flush();
            }

            //Serial file has already been loaded.
            //So we flush all objects.
            else
                flush();
        }
    }

    /**
     * Init file.
     * <p>
     * This is specially used for Serial Constructor initialisation.
     *
     * @param filename    File name
     * @param dirPlayPath Relative directory.
     */
    private void init(String filename, String dirPlayPath) {

        //Try to get the Complete File Path. If the file doesn't exists but can be created,
        //he is.
        String fileCompleteAbsolutePath = IOUtils.getPlayFileAbsolutePathSafe(dirPlayPath, filename);

        if (StringUtils.isEmpty(fileCompleteAbsolutePath)) {
            Logger.log(TypeLogMessageError.CANTCREATEFILE.setMessage("\"This file is not found, and can't be created.\" +" +
                    "                        \"Directory : \" + dirPlayPath() " +
                    "                        \" File : \" + filename()"));
            return;
        }


        //There are no news objects to flush in the serial file.
        if (newObjectsToFlush.isEmpty()) {
            //If objectsLoad equal null, that denote that this
            //field have been never updated yet. So we will try to load data
            //from the serial file.
            if ((objectsLoad == null))
                forceLoad(MSG_INIT_FAIL, fileCompleteAbsolutePath);

                //There is no news objects to flush.
                // This update the serial file.
            else
                flush(fileCompleteAbsolutePath);

        }

        //There is news objects to flush.
        else {
            //But it seem like serial file has never been loaded.
            //So the serial file is load, the news objects are added, and we flush all objects.
            if (objectsLoad == null) {
                forceLoad(MSG_INIT_FAIL, fileCompleteAbsolutePath);
                flush(fileCompleteAbsolutePath);
            }

            //Serial file has already been loaded.
            //So we flush all objects.
            else
                flush(fileCompleteAbsolutePath);
        }
    }

    /**
     * Try to init the serial file.
     */
    public final void safeInit() {
        init();
    }

    /**
     * Force Load of the objects.
     *
     * @param logMsgError Msg to Log when an error occurred.
     */
    private void forceLoad(String logMsgError) {
        try {
            //Value class must be same as who on which the file content is being cast.
            //noinspection unchecked : Can't check the content of the file.
            objectsLoad = (ArrayList<S>) Serialization.deserialise(fileCompletePath());
        } catch (RuntimeException e) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setThrowableAndParticularMessage(e, logMsgError));
        }
    }

    /**
     * Force Load of the objects.
     *
     * @param logMsgError Msg to Log when an error occurred.
     * @return {@code true} If the serial data has been load.
     */
    private boolean _forceLoad(String logMsgError) {
        try {
            //Value class must be same as who on which the file content is being cast.
            //noinspection unchecked : Can't check the content of the file.
            List<S> data = (List<S>) Serialization.deserialise(fileCompletePath());
            if (data == null)
                return false;
            objectsLoad = (ArrayList<S>) data;
        } catch (RuntimeException e) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setThrowableAndParticularMessage(e, logMsgError));
            return false;
        }
        return true;
    }

    /**
     * Force Load of the objects.
     *
     * @param logMsgError              Msg to Log when an error occurred.
     * @param fileCompleteAbsolutePath File complete absolute Path.
     */
    private void forceLoad(String logMsgError, String fileCompleteAbsolutePath) {
        try {
            //Value class must be same as who on which the file content is being cast.
            //noinspection unchecked : Can't check the content of the file.
            objectsLoad = (ArrayList<S>) Serialization.deserialise(fileCompleteAbsolutePath);
        } catch (RuntimeException e) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setThrowableAndParticularMessage(e, logMsgError));
        }
    }

    /**
     * Load the objects ont the serial file.
     *
     * @param logMsgError Message to log when an error occured.
     */
    public final void load(String logMsgError) {
        //The serial file have never been load.
        if (objectsLoad == null) {
            forceLoad(logMsgError);
        }
    }

    /**
     * Load the objects ont the serial file.
     *
     * @param logMsgError Message to log when an error occured.
     * @return {@code true} If the serial data has been load.
     */
    public final boolean _load(String logMsgError) {
        //The serial file have never been load.
        return (objectsLoad == null) && _forceLoad(logMsgError);
    }

    /**
     * Load data in serial file into Object field corresponding.
     * <p>
     * After this, the field {@code objectsLoad} must be equals to data in serial file.
     *
     * @return <em>true</em>, If data has been load (so the value
     * of this object has been modified),
     */
    public final boolean load() {
        return _load(LOAD_ERROR_MSG);
    }

    /**
     * Flush this object in the serial file.
     */
    public final boolean flush() {
        return flush(all());
    }

    /**
     * Number of objects in the serial file (or actual object).
     *
     * @return Number of objects in the serial file (or actual object)
     */
    @Override
    public int size() {
        return all().size();
    }

    /**
     * Flush a list of objects in the serial file.
     *
     * @param toFlush Objects which must be flush in the serial file.
     * @return {@code true} if the serial file has been really changed.
     */
    private boolean flush(ArrayList<S> toFlush) {
        load();
        if (!newObjectsToFlush.isEmpty()) {
            if (Serialization.serialiseSafe(getFile(), toFlush)) {
                objectsLoad = toFlush;
                newObjectsToFlush.clear();
                return true;
            }
        }
        return false;
    }

    /**
     * Load objects to a specific Path denote by {@code fileAbsolutePath}.
     * <p>
     * Used by the constructor of class Serial.
     *
     * @param fileAbsolutePath File Absolute Path.
     */
    private void flush(String fileAbsolutePath) {
        if (IOUtils.initPlayFile(fileAbsolutePath)) {
            load();
            if (!newObjectsToFlush.isEmpty()) {
                if (Serialization.serialiseSafe(new File(fileAbsolutePath), all())) {
                    objectsLoad = all();
                    newObjectsToFlush.clear();
                }
            }
        }
    }

    /**
     * Flush a list of objects in the serial file.
     *
     * @param toFlush Objects to flush.
     * @return The objects list has been flushed.
     */
    private boolean forceFlush(ArrayList<S> toFlush) {
        if (Serialization.serialiseSafe(getFile(), toFlush)) {
            objectsLoad = toFlush;
            newObjectsToFlush.clear();
            return true;
        }
        return false;
    }

    /**
     * Flush this objects list in the serial file.
     *
     * @return The objects list has been flushed.
     */
    private boolean forceFlush() {
        return forceFlush(all());
    }

    /**
     * Reset all objects in Object and serial file.
     *
     * @return If the serial file has been reset.
     */
    public final boolean reset() {
        if (objectsLoad != null) {
            objectsLoad.clear();
        }


        newObjectsToFlush.clear();
        return forceFlush();
    }

    /**
     * Add object to the exist one.
     *
     * @param objectToAdd Object to Add.
     */
    @Override
    public final void add(S objectToAdd) {
        newObjectsToFlush.add(objectToAdd);
    }

    /**
     * Add objects to the exist one.
     *
     * @param objectsToAdd Objects to Add.
     */
    @Override
    public final void add(List<S> objectsToAdd) {
        newObjectsToFlush.addAll(objectsToAdd);
    }

    /**
     * Remove object in the exist one.
     *
     * @param objectToRemove Object to remove.
     */
    @Override
    public final void remove(S objectToRemove) {
        objectsLoad.stream().filter(load -> load.equals(objectToRemove)).findFirst().ifPresent(load -> objectsLoad.remove(load));
        objectsLoad.stream().filter(newO -> newO.equals(objectToRemove)).findFirst().ifPresent(load -> newObjectsToFlush.remove(load));
    }

    /**
     * Remove all objects that are equals in the context to the parameter.
     *
     * @param objectToRemove Object to compare all objects.
     */
    @Override
    public final void removeAll(S objectToRemove) {
        objectsLoad.stream().filter(load -> load.equals(objectToRemove)).forEachOrdered(load -> objectsLoad.remove(load));
        objectsLoad.stream().filter(newO -> newO.equals(objectToRemove)).forEachOrdered(load -> newObjectsToFlush.remove(load));
    }

    @Override
    public final void remove(List<S> objectsToRemove) {
    }

    /**
     * Last object saved.
     *
     * @return The last object saved on the serial object.
     */
    @Override
    public final S last() {
        return null;
    }

    /**
     * Last objects saved.
     *
     * @param interval Number of last objects to return.
     * @return The last {@code interval} objects saved.
     */
    @Override
    public final ArrayList<S> last(int interval) {
        return null;
    }

    /**
     * First object saved.
     *
     * @return The first object saved on the serial object.
     */
    @Override
    public final ArrayList<S> first() {
        return null;
    }

    /**
     * First objects saved.
     *
     * @param interval Number of first objects to return.
     * @return The first {@code interval} objects saved.
     */
    @Override
    public final ArrayList<S> first(int interval) {
        return null;
    }

    /**
     * Search objects which equals to the parameter passed.
     *
     * @param search The object with who compare the one saved on the serial.
     * @return Objects equals with the object passed.
     */
    @Override
    public final ArrayList<S> search(S search) {
        return null;
    }

    /**
     * Retrieve objects saved at a date.
     *
     * @param calendar The date objects were saved.
     * @return The objects saved at the date passed in parameter.
     */
    @Override
    public final ArrayList<S> search(DateTime calendar) {
        return null;
    }

    /**
     * Save the state of serial object to the serial file.
     */
    @Deprecated
    public final void save() {
        Serializer.save(all(), fileCompletePath());
    }

    /**
     * @return all objects in serial file.
     */
    public final ArrayList<S> all() {
        return Utils.addList(objectsLoad, newObjectsToFlush);
    }

}
