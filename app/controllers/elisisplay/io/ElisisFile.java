package controllers.elisisplay.io;

import controllers.elisisplay.lang.StringUtils;
import play.libs.Files;

import java.io.*;

/**
 * Abstraction for a file.
 */
public class ElisisFile implements IElisisFile, Serializable {

    /**
     * Original file complete absolute directory path.
     */
    private String originalAbsoluteDirPath;

    /**
     * Backup field complete absolute directory path.
     */
    private String backupAbsoluteDirPath;

    /**
     * File complete name.
     */
    private String completeName;

    /**
     * File key.
     * <p>
     * This field is used to define the name of the file
     * or his corresponding key in some maps.
     */
    private String key;

    /**
     * Object file denote by this class parameters.
     */
    private File file;

    /**
     *
     * @param originalAbsoluteFilePath Original file complete absolute directory path.
     * @throws FileNotFoundException File Not Found
     */
    public ElisisFile(String originalAbsoluteFilePath) throws FileNotFoundException {
        if (StringUtils.isEmpty(originalAbsoluteFilePath))
            throw new NullPointerException();

        file = new File(originalAbsoluteFilePath);
        if (!file.exists() || !file.isFile())
            throw new FileNotFoundException();

        originalAbsoluteDirPath = file.getParent();
        completeName = file.getName();
        key = file.toString();
        backupAbsoluteDirPath = originalAbsoluteDirPath;
    }

    /**
     *
     * @param originalAbsoluteDirPath Original file complete absolute directory path.
     * @param completeName File complete name.
     * @throws FileNotFoundException File Not Found Exception
     */
    public ElisisFile(String originalAbsoluteDirPath, String completeName) throws FileNotFoundException {
        if (StringUtils.isEmpty(originalAbsoluteDirPath) || StringUtils.isEmpty(completeName))
            throw new NullPointerException();

        if (!IOUtils.fileExists(originalAbsoluteDirPath, completeName))
            throw new FileNotFoundException();

        file = IOUtils.getFile(originalAbsoluteDirPath, completeName);
        this.originalAbsoluteDirPath = originalAbsoluteDirPath;
        this.backupAbsoluteDirPath = originalAbsoluteDirPath;
        this.completeName = completeName;
        key = file.toString();

    }

    /**
     *
     * @param originalAbsoluteDirPath Original file complete absolute directory path.
     * @param completeName File complete name.
     * @param key File key.
     * @throws FileNotFoundException File Not Found Exception
     */
    public ElisisFile(String originalAbsoluteDirPath, String completeName, String key) throws FileNotFoundException {
        if (StringUtils.isEmpty(originalAbsoluteDirPath) || StringUtils.isEmpty(completeName))
            throw new NullPointerException();

        if (!IOUtils.fileExists(originalAbsoluteDirPath, completeName))
            throw new FileNotFoundException();

        file = IOUtils.getFile(originalAbsoluteDirPath, completeName);
        this.originalAbsoluteDirPath = originalAbsoluteDirPath;
        this.backupAbsoluteDirPath = originalAbsoluteDirPath;
        this.completeName = completeName;
        this.key = key;
    }

    /**
     *
     * @param originalAbsoluteDirPath Original file complete absolute directory path.
     * @param completeName File complete name.
     * @param key File key.
     * @param backupAbsoluteDirPath Backup field complete absolute directory path.
     * @throws FileNotFoundException IO Exception
     */
    public ElisisFile(String originalAbsoluteDirPath, String completeName, String key, String backupAbsoluteDirPath) throws FileNotFoundException {
        if (StringUtils.isEmpty(originalAbsoluteDirPath) || StringUtils.isEmpty(completeName))
            throw new NullPointerException();

        if (!IOUtils.fileExists(originalAbsoluteDirPath, completeName))
            throw new FileNotFoundException();

        file = IOUtils.getFile(originalAbsoluteDirPath, completeName);
        this.originalAbsoluteDirPath = originalAbsoluteDirPath;
        this.backupAbsoluteDirPath = originalAbsoluteDirPath;
        this.completeName = completeName;
        this.key = key;

        File backupFile = new File(backupAbsoluteDirPath, completeName + ".tmp");
        backupFile.deleteOnExit();
        Files.copy(file, backupFile);
    }


    /**
     * @return Original file complete absolute directory path.
     */
    @Override
    public String originalAbsolutePath() {
        return IOUtils.getFileAbsolutePath(originalAbsoluteDirPath, completeName);
    }

    /**
     * @return Backup field complete absolute directory path.
     */
    @Override
    public String originalAbsoluteDirPath() {
        return originalAbsoluteDirPath;
    }

    /**
     * @return File complete name.
     */
    @Override
    public String completeName() {
        return completeName;
    }

    /**
     * This field is used to define the name of the file
     * or his corresponding key in some maps.
     *
     * @return File key.
     */
    @Override
    public String key() {
        return key;
    }

    /**
     * @return The file denote by the paremeters of this class.
     */
    @Override
    public File getFile() {
        return file;
    }

    /**
     * Create the file denote by the class parameters.
     *
     * @return {@code true} if file don't exists and have been created.
     */
    @Override
    public boolean create() {
        if (!IOUtils.fileExists(originalAbsoluteDirPath, completeName))
            return false;

        return IOUtils.initFile(originalAbsoluteDirPath, completeName);
    }

    /**
     * Create the file denote by the class parameters.
     *
     * @return {@code true} if file exists after this operation.
     */
    @Override
    public boolean createSafe() {
        IOUtils.initFile(originalAbsoluteDirPath, completeName);

        return IOUtils.fileExists(originalAbsoluteDirPath, completeName);
    }

    /**
     * Denote if the file already exists or not.
     *
     * @return {@code true} if this file already exists.
     */
    @Override
    public boolean exists() {
        return IOUtils.fileExists(originalAbsoluteDirPath, completeName);
    }

    /**
     * Denote if the dir of this file already exists or not.
     *
     * @return {@code true} if the dir of this file already exists.
     */
    @Override
    public boolean dirExists() {
        return IOUtils.dirExists(originalAbsoluteDirPath);
    }

    @Override
    public String toString() {
        return (file == null) ? IOUtils.getFileAbsolutePath(originalAbsoluteDirPath, completeName) : file.getAbsolutePath();
    }
}
