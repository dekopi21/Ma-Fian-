package controllers.elisisplay.logging;


import java.util.ArrayList;

/**
 * Classe Générale utilisé pour le Logging.
 * <p>
 * Implémente différentes ressources de Logging.
 */
public class Logger {

    public static boolean isInit = false;

    /**
     * Ressource de Logging actuelle.
     */
    public static LoggingRessource actualRessourceType = LoggingRessource.BINSERIALISATION;
    //public static LoggingRessource actualRessourceType = null;

    /**
     * Ressource de Logging par défaut.
     */
    public static final LoggingRessource defaultRessourceType = LoggingRessource.BINSERIALISATION;

    /**
     * Liste de messages de Logging enregistrés dans la ressource de logging actuelle.
     */
    private static ArrayList<LogMessage> actualLogs = null;

    /**
     * Liste de messages de Logging enregistrés dans la ressource de logging par défaut.
     */
    public static final ArrayList<LogMessage> defaultLogs = defaultRessourceType.getLogs();


    /**
     * Initialiser le Logging avec une une ressouce précise.
     *
     * @param ressource Ressource à utiliser pour le logging.
     */
    public static void init(LoggingRessource ressource) {
        if (ressource == null) {
            init();
            isInit = true;
            return;
        }

        switch (ressource) {
            case BINSERIALISATION:
                SerialLogger.init();
                break;

            default:
                SerialLogger.init();
                break;
        }
        isInit = true;
    }

    /**
     * Initialiser le Logging avec la ressource de logging par défaut.
     */
    public static void init() {
        init(defaultRessourceType);
    }

    /**
     * Initialiser la ressource de Logging avec la valeur par défaut,
     * si cette dernière n'a pas déjà été initialisée.
     */
    public static void safeInit() {
        if (actualRessourceType == null) {
            init();
        }
    }

    public static void log(controllers.elisisplay.logging.TypeLogMessageError message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.log(message);
                break;

            default:
                SerialLogger.log(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level FINE en utilisant la ressource définie
     * dans la méthode {@code init()}.
     *
     * @param message Le message de Logging.
     */
    public static void fine(String message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.fine(message);
                break;

            default:
                SerialLogger.fine(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level FINE en utilisant la ressource définie
     * dans la méthode {@code init()}.
     *
     * @param message   Le message de Logging.
     * @param throwable Exception levée à logger.
     */
    public static void fine(String message, Throwable throwable) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.fine(message, throwable);
                break;

            default:
                SerialLogger.fine(message, throwable);
                break;
        }
    }

    /**
     * Logger un message avec le Level DEBUG en utilisant la ressource définie
     * dans la méthode {@code init()}.
     *
     * @param message Le message de Logging.
     */
    public static void debug(String message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.debug(message);
                break;

            default:
                SerialLogger.debug(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level DEBUG en utilisant la ressource définie
     * dans la méthode {@code init()}.
     *
     * @param message   Le message de Logging.
     * @param throwable Exception levée à logger.
     */
    public static void debug(String message, Throwable throwable) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.debug(message, throwable);
                break;

            default:
                SerialLogger.debug(message, throwable);
                break;
        }
    }

    /**
     * Logger un message avec le Level CONFIG. en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message Le message de Logging.
     */
    public static void config(String message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.config(message);
                break;

            default:
                SerialLogger.config(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level CONFIG. en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message   Le message de Logging.
     * @param throwable Exception levée à logger.
     */
    public static void config(String message, Throwable throwable) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.config(message, throwable);
                break;

            default:
                SerialLogger.config(message, throwable);
                break;
        }
    }

    /**
     * Logger un message avec le Level INFO en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message Le message de Logging.
     */
    public static void info(String message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.info(message);
                break;

            default:
                SerialLogger.info(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level INFO en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message   Le message de Logging.
     * @param throwable Exception levée à logger.
     */
    public static void info(String message, Throwable throwable) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.info(message, throwable);
                break;

            default:
                SerialLogger.info(message, throwable);
                break;
        }
    }

    /**
     * Logger un message avec le Level WARNING en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message Le message de Logging.
     */
    public static void warning(String message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.warning(message);
                break;

            default:
                SerialLogger.warning(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level WARNING en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message   Le message de Logging.
     * @param throwable Exception levée à logger.
     */
    public static void warning(String message, Throwable throwable) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.warning(message, throwable);
                break;

            default:
                SerialLogger.warning(message, throwable);
                break;
        }
    }

    /**
     * Logger un message avec le Level SEVERE en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message Le message de Logging.
     */
    public static void severe(String message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.severe(message);
                break;

            default:
                SerialLogger.severe(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level SEVERE en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message   Le message de Logging.
     * @param throwable Exception levée à logger.
     */
    public static void severe(String message, Throwable throwable) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.severe(message, throwable);
                break;

            default:
                SerialLogger.severe(message, throwable);
                break;
        }
    }

    /**
     * Logger un message avec le Level FATALE en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message Le message de Logging.
     */
    public static void fatal(String message) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.fatal(message);
                break;

            default:
                SerialLogger.fatal(message);
                break;
        }
    }

    /**
     * Logger un message avec le Level FATALE en utilisant la resource définie
     * dans la méthode {@code init()}.
     *
     * @param message   Le message de Logging.
     * @param throwable Exception levée à logger.
     */
    public static void fatal(String message, Throwable throwable) {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.fine(message, throwable);
                break;

            default:
                SerialLogger.fine(message, throwable);
                break;
        }
    }


    /**
     * Fermer la ressource (support d'enregistrement des logs) de logging.
     */
    @Deprecated
    public static void close() {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.flush();
                break;

            default:
                SerialLogger.flush();
                break;
        }
    }


    /**
     * Utile pour enregistrer les Logs ajoutés mais non enregistrés dans
     * la ressource (support d'enregistrement des logs) de logging.
     */
    public static void flush() {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.flush();
                break;

            default:
                SerialLogger.flush();
                break;
        }
    }

    /**
     * Charger les logs enregistrés dans
     * la ressource (support d'enregistrement des logs) de logging.
     */
    public static void load() {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.load();
                break;

            default:
                SerialLogger.load();
                break;
        }
    }

    /**
     * Indique pour la classe de Logging {@code Logger}, la ressource de Logging
     * et les Logs actuels.
     *
     * @param ressource Ressource de Logging à utilisée par cette classe lors des
     *                  opérations.
     */
    public static void defineLogger(LoggingRessource ressource) {
        actualRessourceType = ressource;
        actualLogs = ressource.getLogs();
    }


    public static ArrayList<LogMessage> getActualLogs() {
        return actualLogs;
    }

    public static void resetLogs() {
        safeInit();
        switch (actualRessourceType) {
            case BINSERIALISATION:
                SerialLogger.resetLogs();
                break;

            default:
                SerialLogger.resetLogs();
                break;
        }
        //flush();
    }
}
