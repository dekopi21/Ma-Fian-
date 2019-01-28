package controllers.elisisplay.serial;

import controllers.elisisplay.io.Serialization;
import play.Play;

import java.io.File;

/**
 * Bin file serializer.
 *
 * Static methods used on serialization.
 */
public final class Serializer {

    private Serializer() {
    }

    /**
     * Initialiser un fichier de sérialisation.
     *
     * @param dirPlayPath      Dossier du fichier de sérialisation.
     * @param fileCompleteName Nom complet du fichier de sérialisation.
     * @return {@code null}, si le dossier n'existe pas et n'a pas pu
     * être initialisé, ou si le dossier est initialisé mais soit l'objet
     * à sérialiser vaut <em>null</em> ou une erreur s'est produit lors de la désérialisation.
     * <p>
     * L'objet contenu après l'initialisation dans le fichier, dans les autres cas.
     */
    public static Object init(String dirPlayPath, String fileCompleteName) {
        return init(dirPlayPath, fileCompleteName, null);
    }

    /**
     * Initialiser un fichier de sérialisation.
     *
     * @param dirPlayPath      Dossier du fichier de sérialisation.
     * @param fileCompleteName Nom complet du fichier de sérialisation.
     * @param obj              L'objet à sérialiser si le dossier est initialisé.
     *                         Lorsque le dossier ne peut être initialisé,
     *                         la valeur du paramêtre n'est pas utilisée.
     *                         <p>
     *                         Dans le cas où seul le dossier doit être initialisé,
     *                         sans écrire le fichier sa valeur doit être {@code null}.
     * @return {@code null}, si le dossier n'existe pas et n'a pas pu
     * être initialisé, ou si le dossier est initialisé mais soit l'objet
     * à sérialiser vaut <em>null</em> ou une erreur s'est produit lors de la désérialisation.
     * <p>
     * L'objet contenu après l'initialisation dans le fichier, dans les autres cas.
     */
    public static Object init(String dirPlayPath, String fileCompleteName, Object obj) {
        File dir = new File(Play.applicationPath.getAbsolutePath(), dirPlayPath);

        if (dir.exists() && dir.isDirectory()) {
            File ser = new File(dir, fileCompleteName);
            if (ser.exists())
                return Serialization.deserialise(ser);
            else if (obj != null) {
                Serialization.serialise(ser, obj);
                return obj;
            }
        } else {
            if (dir.mkdirs())
                if (obj != null) {
                    File ser = new File(dir, fileCompleteName);
                    Serialization.serialise(ser, obj);
                }
            return obj;
        }
        return null;
    }

    /**
     * Charger un objet dans un fichier sérialisé.
     * @param relativeFilePlayPath Chemin relatif à partir de la racine de
     *                             Play, allant vers le fichier de sérialisation.
     * @return L'objet contenu dans le fichier serialisé.
     *         {@code null} dans le cas où une erreur s'est produite lors de
     *         la désérialisation.
     */
    public static Object load(String relativeFilePlayPath) {
        return Serialization.playdeserialise(relativeFilePlayPath);
    }

    /**
     * Sérialiser l'objet dans un fichier de sérialisation.
     * @param serial L'objet à sérialiser.
     * @param relativeFilePlayPath Chemin relatif à partir de la racine de
     *                             Play, allant vers le fichier de sérialisation.
     */
    public static void save(Object serial, String relativeFilePlayPath) {
        Serialization.playserialise(relativeFilePlayPath, serial);
    }

    /**
     * Sérialiser l'objet dans un fichier de sérialisation.
     * @param serial L'objet à sérialiser.
     * @param relativeFIlePlayPath Chemin relatif à partir de la racine de
     *                             Play, allant vers le fichier de sérialisation.
     */
    public static boolean flush(Object serial, String relativeFIlePlayPath) {
       return Serialization.playserialiseSafe(relativeFIlePlayPath, serial);
    }
}
