package controllers.elisisplay.io;

import com.sun.istack.internal.Nullable;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;
import org.apache.commons.io.FileUtils;
import play.Play;
import play.libs.Codec;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Some IO actions.
 */
@SuppressWarnings("Duplicates")
public final class IOUtils {

    private IOUtils() {
    }

    /**
     * Get the absolute Path of the file in the play directory denote by this parameters.
     * <p>
     * This try to init the file before getting his path.
     *
     * @param dirPlayPath      Relative file directory path from the Play directory root.
     * @param fileCompleteName File complete name.
     * @return The absolute Path of the file in the play directory denote by this parameters
     * or {@code null} if the file does not exists.
     */
    @Nullable
    public static String getPlayFileAbsolutePathSafe(String dirPlayPath, String fileCompleteName) {
        initPlayFile(dirPlayPath, fileCompleteName);
        return getPlayFileAbsolutePath(dirPlayPath, fileCompleteName);
    }

    /**
     * Get the absolute Path of the file in the play directory denote by this parameters.
     * *
     *
     * @param dirPlayPath      Relative file directory path from the Play directory root.
     * @param fileCompleteName File complete name.
     * @return The absolute Path of the file in the play directory denote by this parameters
     * or {@code null} if the file does not exists.
     */
    @Nullable
    public static String getPlayFileAbsolutePath(String dirPlayPath, String fileCompleteName) {
        //noinspection ConstantConditions : Already checked that this file exists.
        return (filePlayExists(dirPlayPath, fileCompleteName)) ? getPlayFile(dirPlayPath, fileCompleteName).getAbsolutePath() : null;
    }

    @Nullable
    public static String  getFileAbsolutePath(String absoluteFileDirPath, String fileCompleteName) {
        if (!fileExists(absoluteFileDirPath, fileCompleteName))
            return null;

        return new File(absoluteFileDirPath, fileCompleteName).getAbsolutePath();
    }

    public static boolean initPlayFile(String fileCompleteAbsolutePath) {
        try {
            File file = new File(fileCompleteAbsolutePath);
            return (file.exists() && file.isFile()) || file.createNewFile();

        } catch (IOException e) {
            Logger.log(TypeLogMessageError.CANTCREATEFILE.setThrowable(e));
            return false;
        }
    }

    /**
     * Get the File object denote by those parameters, in the Play directory.
     *
     * @param dirPlayPath      Play relative file directory path.
     * @param fileCompleteName File complete name.
     * @return the File object denote by those parameters, in the Play directory.
     */
    @Nullable
    public static File getPlayFile(String dirPlayPath, String fileCompleteName) {
        if (!filePlayExists(dirPlayPath, fileCompleteName))
            return null;

        File file = new File(Play.applicationPath.getAbsolutePath(), dirPlayPath);
        return new File(file, fileCompleteName);
    }

    /**
     * Get the File object denote by those parameters, in the Play directory.
     *
     * @param completeAbsoluteDirPath      File directory absolute path.
     * @param fileCompleteName File complete name.
     * @return the File object denote by those parameters.
     */
    @Nullable
    public static File getFile(String completeAbsoluteDirPath, String fileCompleteName) {
        try {
            return new File(completeAbsoluteDirPath, fileCompleteName);
        } catch (RuntimeException re) {
            Logger.log(TypeLogMessageError.FILENOTFOUNDERROR.setThrowable(re));
            return null;
        }
    }

    /**
     * Get the File object denote by those parameters, in the Play directory.
     *
     * @param completeRelativePlayFilePath File complete name.
     * @return the File object denote by those parameters.
     */
    @Nullable
    public static File getPlayFile(String completeRelativePlayFilePath) {
        if (!filePlayExists(completeRelativePlayFilePath))
            return null;

        return new File(Play.applicationPath.getAbsolutePath(), completeRelativePlayFilePath);
    }

    /**
     * Get the Directory object denote by those parameters, in the Play app directory.
     *
     * @param completeRelativePlayFilePath File complete name.
     * @return the Directory object denote by those parameters.
     */
    @Nullable
    public static File getPlayDirFile(String completeRelativePlayFilePath) {
        if (!dirPlayExists(completeRelativePlayFilePath))
            return null;

        return new File(Play.applicationPath.getAbsolutePath(), completeRelativePlayFilePath);
    }

    /**
     * Get the Directory object denote by those parameters.
     *
     * @param completeAbsoluteFilePath File complete name.
     * @return the Directory object denote by those parameters.
     */
    public static File getDirFile(String completeAbsoluteFilePath) {
        if (!dirExists(completeAbsoluteFilePath))
            return null;

        return new File(Play.applicationPath.getAbsolutePath(), completeAbsoluteFilePath);
    }

    /**
     * Check if a directory exists.
     * @param completeAbsoluteDirPath Complete directory path.
     * @return {@code true} if a directory with this name exists.
     */
    public static boolean dirExists(String completeAbsoluteDirPath) {
        File file = new File(completeAbsoluteDirPath);

        return file.exists() && file.isDirectory();
    }

    /**
     * Check if a file exists in Play directory.
     *
     * @param fileCompleteName File name.
     * @param dirPlayPath      Relative path in Play app directory from the root.
     * @return {@code true} if the file denote by those parameters exists.
     */
    public static boolean filePlayExists(String dirPlayPath, String fileCompleteName) {
        File file = new File(Play.applicationPath.getAbsolutePath(), dirPlayPath);
        if (file.exists() && file.isDirectory()) {
            file = new File(file, fileCompleteName);
            return file.exists() && file.isFile();
        }
        return false;
    }

    public static boolean fileExists(String fileDirAbsolutePath, String fileCompleteName) {
        File file = new File(fileDirAbsolutePath, fileCompleteName);
        return file.isFile() && file.exists();
    }

    public static boolean fileExists(String fileCompleteAbsolutePAth) {
        File file = new File(fileCompleteAbsolutePAth);
        return file.isFile() && file.exists();
    }

    /**
     * Check if a file exists in Play directory.
     *
     * @param completeRelativePlayFilePath Relative path of file in Play app directory from the root.
     * @return {@code true} if the file denote by his relative path exists.
     */
    public static boolean filePlayExists(String completeRelativePlayFilePath) {
        File file = new File(Play.applicationPath.getAbsolutePath(), completeRelativePlayFilePath);
        return file.exists() && file.isFile();
    }

    /**
     * Check if a directory exists in Play directory.
     *
     * @param completeRelativePlayDirPath Relative path of directory in Play app directory from the root.
     * @return {@code true} if the directory denote by his relative path exists.
     */
    public static boolean dirPlayExists(String completeRelativePlayDirPath) {
        File file = new File(Play.applicationPath.getAbsolutePath(), completeRelativePlayDirPath);
        return file.exists() && file.isDirectory();
    }

    private static boolean initDir(String originalAbsoluteDirPath) {
        File file = new File(originalAbsoluteDirPath);
        return (file.exists() && file.isDirectory()) || file.mkdirs();
    }

    /**
     * Create directory if it don't exists.
     *
     * @param completeRelativePlayDirPath Relative Path in Play app directory.
     * @return {@code true} if dir has been created or already exists.
     */
    private static boolean initPlayDir(String completeRelativePlayDirPath) {
        File file = new File(Play.applicationPath.getAbsolutePath(), completeRelativePlayDirPath);
        return file.exists() || file.mkdirs();
    }

    /**
     * Create file if it don't exists.à
     *
     * @param completeRelativePlayDirPath Relative Path in Play app directory.
     * @param fileCompleteName            File complete name (with his extension).
     * @return {@code true} if file has been created or already exists.
     */
    public static boolean initPlayFile(String completeRelativePlayDirPath, String fileCompleteName) {
        if (initPlayDir(completeRelativePlayDirPath)) {
            //Directory exists.
            File file = new File(Play.applicationPath.getAbsolutePath(), completeRelativePlayDirPath);
            file = new File(file, fileCompleteName);
            //FIle already exists.
            if (file.exists())
                return true;

            //File don't exists.
            try {
                return file.createNewFile();
            } catch (IOException e) {
                Logger.log(TypeLogMessageError.CANTCREATEFILE.setThrowable(e));
            }
        }
        return false;
    }

    public static boolean initFile(String originalAbsoluteDirPath, String completeName) {
        if (initDir(originalAbsoluteDirPath)) {
            //Directory exists.
            File file = new File(originalAbsoluteDirPath, completeName);
            //FIle already exists.
            if (file.exists())
                return true;

            //File don't exists.
            try {
                return file.createNewFile();
            } catch (IOException e) {
                Logger.log(TypeLogMessageError.CANTCREATEFILE.setThrowable(e));
            }
        }
        return false;
    }


    private static final String CANT_MK_DIR = "Can't create the directory.";
    private static final String DEFAULT_PICTURE_NAME = "PICTURE";

    /**
     * Save a picture.
     *
     * @param image      The Picture File to save.
     * @param pathToSave Relative Play directory app path where the picture will be saved.
     * @return "" When all has been done fine.
     * "1" : For IOException
     * "-1" : For other exception.
     * "2" : Can't access or create the directory path.
     */
    public static String savePic(File image, String pathToSave) {
        return savePicture(image, pathToSave, DEFAULT_PICTURE_NAME);
    }

    /**
     * Save a picture.
     *
     * @param picture    The Picture File to save.
     * @param pathToSave Relative Play directory app path where the picture will be saved.
     * @param picName    Name of the Pic file.
     * @return "" When all has been done fine.
     * "1" : For IOException
     * "-1" : For other exception.
     * "2" : Can't access or create the directory path.
     */
    private static String savePicture(File picture, String pathToSave, String picName) {

        File dirAbsolutePath = new File(Play.applicationPath.getAbsolutePath(), pathToSave);

        //Create directory if it don't exists.
        if (!dirAbsolutePath.exists() && !dirAbsolutePath.mkdirs()) {
            return CANT_MK_DIR;
        }

        //The directory exists.
        try {
            File absolutePath;
            do {
                //Get the file name.
                absolutePath = new File(dirAbsolutePath, getFileNameComplete(picture, picName));
            } while (absolutePath.exists());
            //A valid name has been found.
            FileUtils.moveFile(picture, absolutePath);
            return pathToSave + '/' + absolutePath.getName();
        } catch (IOException ioe) {
            Logger.log(TypeLogMessageError.CANTSAVEIMAGEFILE.setThrowable(ioe));
            return "1";
        } catch (Exception e) {
            Logger.log(TypeLogMessageError.CANTSAVEIMAGEFILE.setThrowable(e));
            return "-1";
        }
    }

    /**
     * Save a picture.
     * <p>
     * This can be used instead of {@code savePicture} to debug or test code.
     *
     * @param picture    The Picture File to save.
     * @param pathToSave Relative Play directory app path where the picture will be saved.
     * @param picName    Name of the Pic file.
     * @return "" When all has been done fine.
     * "1" : For IOException
     * "-1" : For other exception.
     * "2" : Can't access or create the directory path.
     * @throws IOException When the file can't be saved.
     */
    public static String savePictureSafe(File picture, String pathToSave, String picName) throws IOException {
        File dirAbsolutePath = new File(Play.applicationPath.getAbsolutePath(), pathToSave);

        //Create directory if it don't exists.
        if (!dirAbsolutePath.exists() && !dirAbsolutePath.mkdirs()) {
            return CANT_MK_DIR;
        }

        //The directory exists.
        File absolutePath;
        do {
            //Get the file name.
            absolutePath = new File(dirAbsolutePath, getFileNameComplete(picture, picName));
        } while (absolutePath.exists());
        //A valid name has been found.
        FileUtils.moveFile(picture, absolutePath);
        return pathToSave + '/' + absolutePath.getName();
    }

    /**
     * Get File complete name.
     *
     * @param file    File
     * @param picName File saved name.
     * @return File complete name
     */
    private static String getFileNameComplete(File file, String picName) {
        return codeFileName(picName) + '.' + getFileExtension(file);
    }

    /**
     * Get File extension.
     *
     * @param file File
     * @return File extension.
     */
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        return ((fileName.lastIndexOf('.') != -1) && (fileName.lastIndexOf('.') != 0)) ? fileName.substring(fileName.lastIndexOf('.') + 1) : "";
    }

    /**
     * Generate unique file name.
     *
     * @param picName Pic name.
     * @return Unique file name.
     */
    private static String codeFileName(String picName) {
        return picName + new SimpleDateFormat("YddMM_hhmmss").format(new Date()) +
                '_' + Codec.UUID().substring(0, 5);
    }
}
