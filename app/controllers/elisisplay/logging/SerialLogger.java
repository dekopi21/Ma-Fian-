package controllers.elisisplay.logging;

import controllers.elisisplay.io.Serialization;
import controllers.elisisplay.lang.StringUtils;
import play.Play;

import java.io.File;

/**
 * Contient les méthodes principales qui permettent de mettre en oeuvre un Logging basic de l'application.
 * <p>
 * La sérialisation dans un fichier de données est la méthode utilisée par défaut.
 */
//public class SerialLogger implements ILogger {
public class SerialLogger {

    /**
     * Objet utilisé pour lire et écrire les Logs.
     */
    private static LoggerObject loggerObject = new LoggerObject();

    /**
     * Ressource de Logging.
     */
    private static final LoggingRessource ressource = LoggingRessource.BINSERIALISATION;

    /**
     * Dossier de Logging.
     */
    private static final String defaultDirPath = "/logs/";

    /**
     * Nom du fichier de Logging.
     */
    private static final String defaultFileName = "logs";

    /**
     * Extension du fichier de Logging.
     */
    private static final String defaultFileExtension = ".ser";

    /**
     * Chemin complet vers le fichier de Logging.
     */
    private static final String defaultRelativeFilePath = defaultDirPath + defaultFileName + defaultFileExtension;

    /**
     * Initialises le Logger.
     * Une parade au NullPointerException lors de l'initialisation.
     */
    private static void initLogger() {
        if (loggerObject == null)
            loggerObject = new LoggerObject();
    }

    /**
     * Logger en utilisant l'objet {@code TypeLogMessageError}.
     * <p>
     * L'objet {@code TypeLogMessageError} a pour but de simplifier le
     * logging.
     * <p>
     * <blockquote> Exemple :
     * <pre>{@code Logger.log(TypeLogMessageError.UNKNOWERROR.setThrowableAndParticularMessage(e, "Exemple").}
     * </pre>
     * </blockquote>
     *
     * @param message Type d'erreur loggé.
     */
    public static void log(TypeLogMessageError message) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(message));
    }

    /**
     * Logger un message avec le Level FINE.
     *
     * @param message Message à Logger
     */
    public static void fine(String message) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.FINE, message));
    }

    /**
     * Logger un message avec le Level FINE.
     *
     * @param message   Message à Logger
     * @param throwable Exception Levée.
     */
    public static void fine(String message, Throwable throwable) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.FINE, message, throwable));
    }

    /**
     * Logger un message avec le Level DEBUG.
     *
     * @param message Message à Logger
     */
    public static void debug(String message) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.DEBUG, message));
    }

    /**
     * Logger un message avec le Level DEBUG.
     *
     * @param message   Message à Logger
     * @param throwable Exception Levée.
     */
    public static void debug(String message, Throwable throwable) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.DEBUG, message, throwable));
    }

    /**
     * Logger un message avec le Level CONFIG.
     *
     * @param message Message à Logger
     */
    public static void config(String message) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.CONFIG, message));
    }

    /**
     * Logger un message avec le Level CONFIG.
     *
     * @param message   Message à Logger
     * @param throwable Exception Levée.
     */
    public static void config(String message, Throwable throwable) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.CONFIG, message, throwable));
    }

    /**
     * Logger un message avec le Level INFO.
     *
     * @param message Message à Logger
     */
    public static void info(String message) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.INFO, message));
    }

    /**
     * Logger un message avec le Level INFO.
     *
     * @param message   Message à Logger
     * @param throwable Exception Levée.
     */
    public static void info(String message, Throwable throwable) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.INFO, message, throwable));
    }


    /**
     * Logger un message avec le Level WARNING.
     *
     * @param message Message à Logger
     */
    public static void warning(String message) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.WARNING, message));
    }

    /**
     * Logger un message avec le Level WARNING.
     *
     * @param message   Message à Logger
     * @param throwable Exception Levée.
     */
    public static void warning(String message, Throwable throwable) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.WARNING, message, throwable));
    }

    /**
     * Logger un message avec le Level SEVERE.
     *
     * @param message Message à Logger
     */
    public static void severe(String message) {
        initLogger();
        loggerObject.addLogMessage(new LogMessage(Level.SEVERE, message));
    }

    /**
     * Logger un message avec le Level SEVERE.
     *
     * @param message   Message à Logger
     * @param throwable Exception Levée.
     */
    public static void severe(String message, Throwable throwable) {
        loggerObject.addLogMessage(new LogMessage(Level.SEVERE, message, throwable));
    }


    /**
     * Logger un message avec le Level FATALE.
     *
     * @param message Message à Logger
     */
    public static void fatal(String message) {
        loggerObject.addLogMessage(new LogMessage(Level.FATAL, message));
    }

    /**
     * Logger un message avec le Level FATALE.
     *
     * @param message   Message à Logger
     * @param throwable Exception Levée.
     */
    public static void fatal(String message, Throwable throwable) {
        loggerObject.addLogMessage(new LogMessage(Level.FATAL, message, throwable));
    }

    /**
     * Indique pour la classe de Logging {@code Logger}, la ressource de Logging
     * et les Logs actuels.
     * <p>
     * Est utile parce que cette classe ne devrait pas être utilisée directement.
     */
    public static void defineLogger() {
        Logger.defineLogger(ressource);
    }

    /**
     * Utile pour enregistrer les Logs ajoutés mais non enregistrés dans
     * la ressource (support d'enregistrement des logs) de logging.
     */
    public static void flush() {
        try {
            Serialization.playserialise(serFilePath, getLoggerObject());
        } catch (Exception ignored) {
            log(TypeLogMessageError.CANTSERLOGSERROR.setThrowable(ignored));
        }
    }

    /**
     * Initialisez le Logging.
     *
     * @param serPath     Chemin absolu du dossier de sérialisation.
     * @param serFileName Nom du fichier de sérialisation.
     */
    public static void init(String serPath, String serFileName) {

        //Utilisation des paramêtres par défaut lorsque les valeurs passées
        //sont nulles.
        if (StringUtils.isEmpty(serPath) && StringUtils.isEmpty(serFileName)){
            init();
            return;
        }

        //Le nom du fichier est connue mais pas celui du dossier de sauvegarde.
        if (StringUtils.isEmpty(serPath))
            serPath = defaultDirPath;

        //Dossier du Log.
        File logsPath = new File(Play.applicationPath.getAbsolutePath(), serPath);

        //Créer le dossier s'il n'existe pas.
        if (!logsPath.exists() && !logsPath.mkdirs()) {
            return;
        }
        try {

            if (StringUtils.isEmpty(serFileName))
                serFileName = defaultFileName + defaultFileExtension;

            //Fichier du Log.
            File serFile = new File(logsPath, serFileName);

            //Chemin relatif du fichier Log.
            String relativePath = serPath + serFileName;

            //Le fichier existe.
            if (serFile.exists())
                load(relativePath);

                //Le fichier n'existait pas et sera créé.
            else if (serFile.createNewFile()) {
                SerialLogger.serFilePath = relativePath;
                defineLogger();
            }
        } catch (Exception ignored) {
        }
    }


    /**
     * Initialiser le Logging.
     */
    public static void init() {
        //Dossier du Log.
        File logsPath = new File(Play.applicationPath.getAbsolutePath(), defaultDirPath);

        //Créer le dossier s'il n'existe pas.
        if (!logsPath.exists() && !logsPath.mkdirs()) {
            return;
        }
        try {
            //Fichier du Log.
            File serFile = new File(logsPath, defaultFileName + defaultFileExtension);

            //Si le fichier existe, les paramêtres par défaut sont utilisés pour le
            //chargement des Logs.
            if (serFile.exists())
                load(defaultRelativeFilePath);

                //Le fichier n'existait pas et sera donc créé.
            else if (serFile.createNewFile()) {
                SerialLogger.serFilePath = defaultRelativeFilePath;
                defineLogger();
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Charger les anciens Logs.
     */
    public static void load() {
        load(defaultRelativeFilePath);
    }

    /**
     * Charger les anciens Logs lors de
     * l'initialisation.
     *
     * @param serFilePath Chemin relatif (partant de la racine de l'application Play) du
     *                    fichier à charger.
     */
    public static void load(String serFilePath) {
        File serFile = new File(Play.applicationPath.getAbsolutePath(), serFilePath);

        try {

            //Si le nom envoyé se révèle être un dossier, un fichier est
            //créer avec les paramêtres par défaut.
            if (serFile.isDirectory() && serFile.mkdirs()) {
                serFile = new File(serFile.getAbsolutePath(), defaultFileName + defaultFileExtension);
            }

            //Essayez de créer le Fichier s'il n'existe pas.
            if (!serFile.exists()) {
                //Le fichier de Log est crée pour la première fois.
                if (serFile.createNewFile()) {
                    loggerObject = new LoggerObject();
                    SerialLogger.serFilePath = serFilePath;
                }


                //Le fichier de Log ne peut être créé.
                else {
                    loggerObject = null;
                    SerialLogger.serFilePath = null;
                }
                return;
            }
            loggerObject = (LoggerObject) (Serialization.playdeserialise(serFilePath));

            //L'action précédente peut renvoyer un objet null si l'opération n'a pas abouti.
            if (loggerObject != null) {
                SerialLogger.serFilePath = serFilePath;
                defineLogger();
            }

        } catch (Exception ignored) {
        }
    }

    /**
     * Chemin relatif (partant de la racine de l'application Play) au Fichier de Logging (valide).
     * <p>
     * Est utilisé au lieu d'un fichier pour éviter de bloquer la lecture
     * ou l'écriture dans le fichier lors de l'execution de l'application.
     */
    private static String serFilePath = defaultRelativeFilePath;

    /**
     * @return L'objet utilisé pour le logging.
     */
    public static LoggerObject getLoggerObject() {
        return loggerObject;
    }

    /**
     * @return Ressource de Logging de cette classe Logger.
     */
    public static LoggingRessource getRessource() {
        return ressource;
    }

    public static void resetLogs() {
        loggerObject.reset();
    }
}
