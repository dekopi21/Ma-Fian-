package controllers.elisisplay.serial;

import com.sun.istack.internal.Nullable;
import controllers.elisisplay.io.IOUtils;
import controllers.elisisplay.io.Serialization;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;

import java.io.File;

/**
 * Shortcuts of Play serializer methods.
 */
public final class PlaySerializer {

    /**
     * Private controller.
     */
    private PlaySerializer() {
    }

    /**
     * Get object serialized in a serial file.
     *
     * @param file Object file that represents the serial file.
     * @return Object serialized or {@code null} if there is any exception.
     */
    @Nullable
    public static Object getSerial(File file) {
        return Serialization.deserialise(file);
    }

    /**
     * Get object serialized in a serial file.
     *
     * @param completeAbsoluteFilePath Complete absolute serial file path.
     * @return Object serialized or {@code null} if there is any exception.
     */
    @Nullable
    public static Object getSerial(String completeAbsoluteFilePath) {
        return Serialization.deserialise(new File(completeAbsoluteFilePath));
    }

    /**
     * Get object serialized in a serial file.
     *
     * @param completeAbsoluteDirPath Absolute serial file directory path.
     * @param fileCompleteName        Complete name of the serial file.
     * @return Object serialized or {@code null} if there is any exception.
     */
    @Nullable
    public static Object getSerial(String completeAbsoluteDirPath, String fileCompleteName) {
        return Serialization.deserialise(IOUtils.getFile(completeAbsoluteDirPath, fileCompleteName));
    }

    /**
     * Get object serialized in a serial file in play directory.
     *
     * @param completeRelativeFilePath Relative serial file path from Play root.
     * @return Object serialized or {@code null} if there is any exception.
     */
    @Nullable
    public static Object getPlaySerial(String completeRelativeFilePath) {
        return Serialization.deserialise(IOUtils.getPlayFile(completeRelativeFilePath));
    }

    /**
     * Get object serialized in a serial file in play directory.
     *
     * @param completeRelativeDirPath Relative serial file directory path from Play root.
     * @param completeFileName        Serial file complete name.
     * @return Object serialized or {@code null} if there is any exception.
     */
    @Nullable
    public static Object getPlaySerial(String completeRelativeDirPath, String completeFileName) {
        return Serialization.deserialise(IOUtils.getPlayFile(completeRelativeDirPath, completeFileName));
    }

    /**
     * Serialize an object to a file (serial file).
     *
     * @param serialFile     The object file which represents the file to save the object.
     * @param objectToSerial Object to serialize.
     * @return {@code true} if there is any exception in the process (that indicates the object has been serialized).
     */
    public static boolean serialize(File serialFile, Object objectToSerial) {
        return Serialization.serialiseSafe(serialFile, objectToSerial);
    }

    /**
     * Serialize an object to a file (serial file).
     *
     * @param completeAbsoluteFilePath Complete absolute serial file path.
     * @param objectToSerial           Object to serialize.
     * @return {@code true} if there is any exception in the process (that indicates the object has been serialized).
     */
    public static boolean serialize(String completeAbsoluteFilePath, Object objectToSerial) {
        try {
            return Serialization.serialiseSafe(new File(completeAbsoluteFilePath), objectToSerial);
        }
        catch (RuntimeException e)
        {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowable(e));
            return false;
        }
    }

    /**
     * Serialize an object to a file (serial file).
     *
     * @param completeRelativeFilePath Complete relative path to the serial file from Play root.
     * @param objectToSerial           Object to serialize.
     * @return {@code true} if there is any exception in the process (that indicates the object has been serialized).
     */
    public static boolean playSerialize(String completeRelativeFilePath, Object objectToSerial) {
        try {
            return Serialization.serialiseSafe(IOUtils.getPlayFile(completeRelativeFilePath), objectToSerial);
        }
        catch (RuntimeException e)
        {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowable(e));
            return false;
        }
    }

    /**
     * Serialize an object to a file (serial file).
     * @param completeRelativeDirPath  Complete relative path to the serial file directory from Play root.
     * @param fileCompleteName Compete file name.
     * @param objectToSerial Object to serialize.
     * @return {@code true} if there is any exception in the process (that indicates the object has been serialized).
     */
    public static boolean playSerialize(String completeRelativeDirPath, String fileCompleteName, Object objectToSerial) {
        try {
            return Serialization.serialiseSafe(IOUtils.getPlayFile(completeRelativeDirPath, fileCompleteName), objectToSerial);
        }
        catch (RuntimeException e)
        {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowable(e));
            return false;
        }
    }

    /**
     * Serialize an object to a file (serial file).
     * @param completeAbsoluteDirPath Complete absolute path of the serial file directory.
     * @param fileCompleteName Compete file name.
     * @param objectToSerial Object to serialize.
     * @return {@code true} if there is any exception in the process (that indicates the object has been serialized).
     */
    public static boolean serialize(String completeAbsoluteDirPath, String fileCompleteName, Object objectToSerial) {
        try {
            return Serialization.serialiseSafe(new File(completeAbsoluteDirPath, fileCompleteName), objectToSerial);
        }
        catch (RuntimeException e)
        {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowable(e));
            return false;
        }
    }


}
