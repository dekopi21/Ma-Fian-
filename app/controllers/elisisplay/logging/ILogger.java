package controllers.elisisplay.logging;

/**
 * Interface qui définit les méthodes de Logging principales.
 */
public interface ILogger {

    /**
     *  Logger un message avec le Level FINE.
     */
    void fine();

    /**
     *  Logger un message avec le Level DEBUG.
     */
    void debug();

    /**
     *  Logger un message avec le Level CONFIG.
     */
    void config();

    /**
     *  Logger un message avec le Level INFO.
     */
    void info();

    /**
     *  Logger un message avec le Level WARNING.
     */
    void warning();

    /**
     *  Logger un message avec le Level SEVERE.
     */
    void severe();

    /**
     *  Logger un message avec le Level FATALE.
     */
    void fatal();

    /**
     * Fermer la ressource (support d'enregistrement des logs) de logging.
     */
    void close();

    /**
     * Utile pour enregistrer les Logs ajoutés mais non enregistrés dans
     * la ressource (support d'enregistrement des logs) de logging.
     */
    void flush();

    /**
     * Initialiser la ressource (support d'enregistrement des logs) de logging.
     */
    void init();
}
