package controllers.elisisplay.io;

import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;
import play.Play;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 * Created by Bienvenu on 27/08/2018 in controllers.elisisplay.
 */
public final class Serialization {

    private Serialization() {
    }

    /**
     * Désérialiser un objet d'un fichier.
     *
     * @param serFile Le fichier contenant l'objet sérialisé.
     * @return L'objet désérialisé.
     */
    public static Object deserialise(File serFile) {
        if (serFile == null) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("L'objet fichier paramètre est nul."));
            return null;
        }

        if (!serFile.exists()) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le fichier indiqué n'existe pas ("
                    + serFile.getAbsolutePath() + ") ."));
            return null;
        }

        if (serFile.isDirectory()) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le chemin indiqué est un " +
                    "dossier ("
                    + serFile.getAbsolutePath() + ") ."));
            return null;
        }

        try {
            ObjectInput deserialiser = new ObjectInputStream(new FileInputStream(serFile));

            //Fermer le flux avant de renvoyer l'objet déserialisé.
            Object _return = deserialiser.readObject();
            deserialiser.close();
            return _return;
        } catch (Exception e) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowable(e));
            return null;
        }
    }

    /**
     * Désérialiser un objet d'un fichier.
     *
     * @param absoluteFilePath Le chemin absolu vers le fichier dans lequel l'objet a été sérialisé.
     * @return L'objet désérialisé.
     */
    public static Object deserialise(String absoluteFilePath) {
        if (absoluteFilePath == null) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("Le chemin est nul."));
            return null;
        }

        File serFile = new File(absoluteFilePath);

        return deserialise(serFile);
    }

    /**
     * Désérialiser un objet d'un fichier à partir d'une application Play.
     *
     * @param relativeFIlePlayPath Le chemin relatif (partant de la racine de l'application Play)
     *                             vers le fichier dans lequel l'objet a été sérialisé.
     * @return L'objet désérialisé.
     */
    public static Object playdeserialise(String relativeFIlePlayPath) {
        if (relativeFIlePlayPath == null) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("Le chemin est nul."));
            return null;
        }

        File serFile = new File(Play.applicationPath.getAbsolutePath(), relativeFIlePlayPath);

        return deserialise(serFile);
    }

    /**
     * Sérialiser un fichier.
     *
     * @param serFile Le fichier dans lequel sérialiser l'objet.
     * @param serial  L'objet à sérialiser.
     */
    public static void serialise(File serFile, Object serial) {
        if ((serFile == null) || (serial == null)) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("Le chemin ou l'objet à sérialiser est nul."));
            return;
        }

        if (serFile.isDirectory()) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le chemin indiqué est un " +
                    "dossier (" + serFile.getAbsolutePath() + ") ."));
            return;
        }

        if (!serFile.exists()) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le chemin indiqué n'existe pas . Création du fichier...(" +
                    serFile.getAbsolutePath() + ") ."));
            try {
                if (!serFile.createNewFile())
                    return;
            } catch (IOException ioe) {
                Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Impossible de créer le fichier (" +
                        serFile.getAbsolutePath() + ") ."));
            }

            return;
        }

        try {
            ObjectOutputStream serialiser = new ObjectOutputStream(new FileOutputStream(serFile));
            serialiser.writeObject(serial);
            serialiser.close();
        } catch (FileNotFoundException fnfe) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le chemin indiqué n'existe pas . Création du fichier...(" +
                    serFile.getAbsolutePath() + ") ."));
            try {
                if (serFile.createNewFile())
                    serialise(serFile, serial);
            } catch (IOException ignored) {
                Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowableAndParticularMessage(ignored,
                        "Impossible de créer le fichier (" +
                                serFile.getAbsolutePath() + ") ."));
            }
        } catch (FileAlreadyExistsException ignored) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le fichier existe déjà. Suppression du fichier...(" +
                    serFile.getAbsolutePath() + ") ."));
            if (serFile.delete()) //Deleting the file.
                serialise(serFile, serial);
        } catch (Exception ignored) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowable(ignored));
        }
    }

    /**
     * Sérialiser un fichier.
     *
     * @param serFile Le fichier dans lequel sérialiser l'objet.
     * @param serial  L'objet à sérialiser.
     */
    public static boolean serialiseSafe(File serFile, Object serial) {
        //Avoid NullPointerException
        if ((serFile == null) || (serial == null)) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("Le chemin ou l'objet à sérialiser est nul."));
            return false;
        }

        //Check if the file object references a file or directory.
        //You can't serialise object in directory.
        if (serFile.exists() && serFile.isDirectory()) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le chemin indiqué est un " +
                    "dossier (" + serFile.getAbsolutePath() + ") ."));
            return false;
        }

        //File don't exists.
        if (!serFile.exists()) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le chemin indiqué n'existe pas . Création du fichier...(" +
                    serFile.getAbsolutePath() + ") ."));
            try {
                //Try to create file.
                if (!serFile.createNewFile())
                    return false;
            } catch (IOException ioe) {
                Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setThrowableAndParticularMessage(ioe,
                        "Impossible de créer le fichier (" + serFile.getAbsolutePath() + ") ."));
                return false;
            }
        }

        try {
            ObjectOutputStream serialiser = new ObjectOutputStream(new FileOutputStream(serFile));
            serialiser.writeObject(serial);
            serialiser.close();
        } catch (FileNotFoundException fnfe) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setThrowableAndParticularMessage(fnfe,
                    "Impossible de créer le fichier (" + serFile.getAbsolutePath() + ") ."));
            return false;

        } catch (FileAlreadyExistsException ignored) {
            Logger.log(TypeLogMessageError.CANTDESERIALIZEFILE.setMessage("Le fichier existe déjà. Suppression du fichier...(" +
                    serFile.getAbsolutePath() + ") ."));
            if (serFile.delete()) //Deleting the file.
                serialise(serFile, serial);
        } catch (Exception ignored) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setThrowable(ignored));
            return false;
        }
        return true;
    }

    /**
     * Sérialiser un fichier.
     *
     * @param serFile Le fichier dans lequel sérialiser l'objet.
     * @param serial  L'objet à sérialiser.
     */
    @Deprecated
    public static boolean _serialise(File serFile, Object serial) {
        long length = serFile.lastModified();
        serialise(serFile, serial);
        return new File(serFile.getAbsolutePath()).lastModified() != length;
    }

    /**
     * Sérialiser un fichier dans une application Play.
     *
     * @param relativeFilePlayPath Le chemin relatif (partant de la racine de l'application Play)
     *                             vers le fichier dans lequel l'objet a été sérialisé.
     * @param serial               L'objet à sérialiser.
     */
    public static void playserialise(String relativeFilePlayPath, Object serial) {
        if ((relativeFilePlayPath == null) || (serial == null)) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("Le chemin est incorrect ou l'objet à sérialiser est vide."));
            return;
        }

        File serFile = new File(Play.applicationPath.getAbsolutePath(), relativeFilePlayPath);

        serialise(serFile, serial);
    }

    //TODO Rename this to : playSerialiseSafe...

    /**
     * Sérialiser un fichier dans une application Play.
     *
     * @param relativeFilePlayPath Le chemin relatif (partant de la racine de l'application Play)
     *                             vers le fichier dans lequel l'objet a été sérialisé.
     * @param serial               L'objet à sérialiser.
     */
    public static boolean playserialiseSafe(String relativeFilePlayPath, Object serial) {
        if ((relativeFilePlayPath == null) || (serial == null)) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("Le chemin est incorrect ou l'objet à sérialiser est vide."));
            return false;
        }

        File serFile = new File(Play.applicationPath.getAbsolutePath(), relativeFilePlayPath);
        long lastModified = serFile.lastModified();

        serialise(serFile, serial);

        return serFile.lastModified() != lastModified;
    }

    /**
     * Use it instead of the top one, because of error on method naming.
     */
    public static boolean playSerialiseSafe(String relativeFilePlayPath, Object serial) {
        return playserialiseSafe(relativeFilePlayPath, serial);
    }

    /**
     * Sérialiser un fichier dans une application Play.
     *
     * @param relativeDirPlayPath Le chemin relatif (partant de la racine de l'application Play)
     *                            vers le fichier dans lequel l'objet a été sérialisé.
     * @param serial              L'objet à sérialiser.
     */
    public static void playserialise(String relativeDirPlayPath, String fileCompleteName, Object serial) {
        if ((relativeDirPlayPath == null) || (serial == null) || (fileCompleteName == null)) {
            Logger.log(TypeLogMessageError.CANTSERIALIZEFILE.setMessage("Le chemin est incorrect ou l'objet à sérialiser est vide."));
            return;
        }

        File serDir = new File(Play.applicationPath.getAbsolutePath(), relativeDirPlayPath);
        //Créer le dossier s'il n'existe pas.
        if (!serDir.exists() && !serDir.mkdirs()) {
            return;
        }

        if (!serDir.isDirectory())
            return;

        File serFile = new File(serDir, fileCompleteName);

        serialise(serFile, serial);
    }


}
