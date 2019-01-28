package controllers.elisisplay.serial;

import java.io.Serializable;

/**
 * Class of one Serial Object.
 */
public class OneSerialObject<O extends Serializable> extends OneSerialAbstractObject<O> {

    private static final long serialVersionUID = 8404951641501654493L;

    /**
     * The directory where this object will or has been serialized path.
     */
    private String dirPlayPath;

    /**
     * The complete filename of the serialization file of this serial object.
     */
    private String fileCompleteName;

    public OneSerialObject(String dirPlayPath, String fileCompleteName) {
        super(false);
        this.dirPlayPath = dirPlayPath;
        this.fileCompleteName = fileCompleteName;
    }

    public OneSerialObject(String dirPlayPath, String fileCompleteName, boolean erase) {
        super(erase);
        this.dirPlayPath = dirPlayPath;
        this.fileCompleteName = fileCompleteName;
    }

    /**
     * @return The directory where this object will or has been serialized path.
     */
    @Override
    public String dirPlayPath() {
        return dirPlayPath;
    }

    /**
     * @return The complete filename of the serialization file of this serial object.
     */
    @Override
    public String filename() {
        return fileCompleteName;
    }
}
