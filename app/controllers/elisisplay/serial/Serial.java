package controllers.elisisplay.serial;

import java.io.Serializable;

/**
 * A class who implement {@code ElisisSerial}.
 *
 * @see ElisisSerial
 * @see IElisisSerial
 */
public final class Serial<S extends Serializable> extends ElisisSerial<S> {

    private static final long serialVersionUID = 5678538329188326251L;

    private String filename;

    private String dirPlayPath;

    private boolean canWrite;

    public Serial(String filename, String dirPlayPath, boolean init) {
        super(filename, dirPlayPath, init);
        this.filename = filename;
        this.dirPlayPath = dirPlayPath;
    }

    @Override
    public String filename() {
        return filename;
    }

    @Override
    public String dirPlayPath() {
        return dirPlayPath;
    }

    public String getFilename() {
        return filename;
    }

    public String getDirPlayPath() {
        return dirPlayPath;
    }

    public boolean canWrite() {
        return getFile().canWrite();
    }

    @Override
    public String toString() {
        return getFile().toString();
    }
}
