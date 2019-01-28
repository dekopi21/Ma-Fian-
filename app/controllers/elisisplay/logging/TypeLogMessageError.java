package controllers.elisisplay.logging;

/**
 * Types de messages affichés à l'utlisateur.
 *
 * Favorise l'internalisation.
 */
public enum TypeLogMessageError {
    UNKNOWERROR(Level.DEBUG, "Erreur inconnue."),
    NOERROR(Level.FINE, ""),
    FATALERROR(Level.FATAL, "Une erreur fatale s'est produite."),
    FILENOTFOUNDERROR(Level.SEVERE, "Ce fichier n'existe pas."),
    PERFORMIOACTIONERROR(Level.SEVERE, "L'action sur le système de fichiers a échoué."),
    CLASSNOTFOUNDERROR(Level.DEBUG, "La classe n'a pas été trouvé."),
    PERSISTENCEERROR(Level.WARNING, "Une erreur de persistence s'est produite."),
    SAVINGENTITYERROR(Level.DEBUG, "L'entité n'a pu être persisté."),
    JPAQUERYERROR(Level.DEBUG, "L'exécution de la requête a échoué."),
    UPDATEENTITYERROR(Level.DEBUG, "Une erreur s'est produite lors de la mise à jour de cette entité."),
    DELETEENTITYERROR(Level.DEBUG, "Une erreur s'est produite lors de la suppression de cette entité."),
    JOBPERFORMINGERROR(Level.FATAL, "Une erreur s'est produite lors de l'execution d'une tâche."),
    CACHEACCESSERROR(Level.WARNING, "Une erreur s'est produite lors de l'accès à une donnée dans le cache."),
    EMAILCHECKINGERROR(Level.DEBUG, "Une erreur s'est produite lors d'une opération sur cette adresse Mail."),
    CANTSERLOGSERROR(Level.DEBUG, "Une erreur s'est produite lors de la sérialisation des fichiers de Logs."),
    CANTSERIALIZEFILE(Level.DEBUG, "Une erreur s'est produite lors de la sérialisation des fichiers de Logs."),
    CANTDESERIALIZEFILE(Level.DEBUG, "Une erreur s'est produite lors de la sérialisation des fichiers de Logs."),
    FIELDDONTEXISTS(Level.DEBUG, "Ce champ n'existe pas dans la déclaration de l'objet actuel."),
    REFLECTIONERROR(Level.DEBUG, "Une erreur s'est produite lors de l'introspection sur cet objet."),
    CANTCREATEFILE(Level.DEBUG, "Une erreur s'est produite lors de la création du Fichier."),
    CANTSAVEIMAGEFILE(Level.DEBUG, "Une erreur s'est produite lors de l'enregistrement du fichier image."),
    ENTITYFINDINGERROR(Level.DEBUG, "Une erreur s'est produite lors de la recherche d'une entité."),
    SENDINGEMAILERROR(Level.DEBUG, "Une erreur s'est produite lors de l'envoi d'un email."),
    ;


    TypeLogMessageError(Level level, String message)
    {
        this.level = level;
        this.message = message;
    }

    private Level level;

    private String message = "";

    private Throwable throwable;

    private String particularMessage = "";

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getParticularMessage() {
        return particularMessage;
    }

    public TypeLogMessageError setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public TypeLogMessageError setMessage(String message) {
        this.message = this.name() + message;
        return this;
    }

    public TypeLogMessageError setThrowableAndParticularMessage(Throwable throwable, String particularMessage) {
        this.throwable = throwable;
        this.particularMessage = particularMessage;
        return this;
    }
}
