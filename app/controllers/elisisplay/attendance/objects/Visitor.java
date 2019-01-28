package controllers.elisisplay.attendance.objects;

import controllers.elisisplay.attendance.Observator;
import controllers.elisisplay.attendance.serials.All;
import controllers.elisisplay.secure.UserType;
import controllers.elisisplay.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Un visiteur de la plateforme.
 *
 * Englobe les visiteurs uniques, les utilisateurs loggés et les admins.
 */
public class Visitor implements Serializable {

    /**
     * Visitor ID.
     */
    public String code;

    /**
     * Hash code qui identifie un utilisateur, déterminer grâce à la
     * méthode de <tt>Finger Print</tt>.
     */
    String fingerPrintHash;

    /**
     * Hash code qui identifie un utilisateur, déterminez grâce
     * à la méthode de <tt>uberCookie</tt>.
     */
    String uberCookie;

    /**
     * Name of the browser used by this visitor.
     */
    public String browser;

    /**
     * Authentification token assigned to this user.
     */
    public String authToken;

    /**
     * Date de la première activité sur la pateforme.
     */
    public GregorianCalendar firstActivity;

    /**
     * Date de la dernière activité sur la pateforme.
     */
    public GregorianCalendar lastActivity;

    /**
     * Nombre de pages visités
     */
    public int nbWebpageAttended;

    /**
     * Type d'utilisateur de la plateforme.
     */
    public UserType typeUtilisateur;

    /**
     * Indique si cet utilisateur est un visteur simple ou a un compte sur la
     * plateforme.
     */
    public boolean hasAccount;

    /**
     * Code du compte utilisateur de cet utilisateur (s'il a un compte).
     */
    public String userAccountCode;

    /**
     * Email de cet utilisateur.
     * A la même fonction que le code du compte utilisateur.
     * Peut aussi servir dans le cas d'inscription avec les emails.
     */
    public String email;

    /**
     * Adresse de l'utilsateur.
     */
    //public Adress adress;

    /**
     * Adresse Ip de ce visiteur.
     */
    private ArrayList<String> remoteAdresses;

    public int visitorIdentificationPourcent(){
        return 0;
    }

    public Visitor(String fingerPrintHash, String uberCookie, String browser,
                   String authToken, GregorianCalendar firstActivity, GregorianCalendar lastActivity,
                   int nbWebpageAttended, UserType typeUtilisateur, boolean hasAccount, String userAccountCode,
                   String email, ArrayList<String> remoteAdresses) {
        this.code = Utils.generateCode("VISITOR", All.visitors().size() + 1, Observator.defaultSerialLenght);;
        this.fingerPrintHash = fingerPrintHash;
        this.uberCookie = uberCookie;
        this.browser = browser;
        this.authToken = authToken;
        this.firstActivity = firstActivity;
        this.lastActivity = lastActivity;
        this.nbWebpageAttended = nbWebpageAttended;
        this.typeUtilisateur = typeUtilisateur;
        this.hasAccount = hasAccount;
        this.userAccountCode = userAccountCode;
        this.email = email;
        //this.adress = adress;
        this.remoteAdresses = remoteAdresses;
    }

    public ArrayList<String> getRemoteAdresses() {
        return remoteAdresses;
    }
}