package controllers.elisisplay.serial;

import controllers.elisisplay.io.IOUtils;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;

import java.io.File;
import java.io.Serializable;

/**
 * Abstract class of one Serial Object.
 */
public abstract class OneSerialAbstractObject<O extends Serializable> implements IOneObjectSerial {

    private static final long serialVersionUID = -592075367253521392L;

    /**
     * If the file must be erased on init or others operations;
     */
    protected boolean erase;

    protected OneSerialAbstractObject(boolean erase) {
        this.erase = erase;
    }

    /**
     * @return The directory where this object will or has been serialized path.
     */
    public abstract String dirPlayPath();

    /**
     * @return The complete filename of the serialization file of this serial object.
     */
    public abstract String filename();

    /**
     * Serial object.
     */
    private O serial = null;

    /**
     * Init the serial file.
     * <p>
     * This try to load data from the serial file if it
     * already exists.
     */
    @Override
    public void init() {
        //Try to get the Complete File Path. If the file doesn't exists but can be created,
        //he is.
        String fileCompleteAbsolutePath = IOUtils.getPlayFileAbsolutePathSafe(dirPlayPath(), filename());

        if (StringUtils.isEmpty(fileCompleteAbsolutePath)) {
            Logger.log(TypeLogMessageError.CANTCREATEFILE.setMessage("\"This file is not found, and can't be created." +
                    "Directory : " + dirPlayPath() +
                    " File : " + filename()));
            return;
        }

        if (erase)
            flush();
        else if (IOUtils.fileExists(fileCompleteAbsolutePath))
            load();
    }

    /**
     * Save the state of serial object to the serial file.
     */
    @Override
    public void save() {
        if (serial != null)
            PlaySerializer.serialize(getFile(), serial);
    }

    /**
     * Load ob serial object, data contained in the serial file.
     *
     * @return {@code true} if the serial data have been load in object file.
     */
    @Override
    public boolean load() {
        O serLoad = (O) PlaySerializer.getSerial(getFile());
        if (serLoad != null) {
            serial = serLoad;
            return true;
        }
        return false;
    }

    /**
     * Flush objects to the serial file.
     *
     * @return <tt>true</tt> If the objects have been flushed.
     */
    @Override
    public boolean flush() {
        if (serial != null) {
            return PlaySerializer.serialize(getFile(), serial);
        }
        return false;
    }

    /**
     * Get File Object from Serial file parameters (Path, FileName).
     *
     * @return File Object from Serial file parameters.
     */
    @Override
    public File getFile() {
        return IOUtils.getPlayFile(dirPlayPath(), filename());
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

    @Override
    public String toString() {
        return getFile().getAbsolutePath();
    }

    /**
     * @return the serial object.
     */
    public O getSerial() {
        return serial;
    }
}
