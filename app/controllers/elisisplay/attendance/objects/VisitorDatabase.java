package controllers.elisisplay.attendance.objects;

import controllers.elisisplay.cachecookie.session.UserLogState;
import controllers.elisisplay.model.ElisisModel;
import controllers.elisisplay.secure.UserType;

import javax.persistence.*;
import java.util.GregorianCalendar;

/**
 * Created by Bienvenu on 21/10/2018 in schoolar_dev.
 */
@Entity
@Table(name = "visitor")
public class VisitorDatabase extends ElisisModel {

    /**
     * Hssh composé obtenu à partir des deux hashs précédents.
     */
    public String finalHash;

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
    @Enumerated(EnumType.STRING)
    public UserType userType;

    /**
     * Indique si cet utilisateur est un visteur simple ou a un compte sur la
     * plateforme.
     */
    public boolean hasAccount;

    /**
     * Code du compte utilisateur de cet utilisateur (s'il a un compte).
     */
    //@OneToOne(fetch = FetchType.LAZY)
    //public User user;

    /**
     * Email de cet utilisateur.
     * A la même fonction que le code du compte utilisateur.
     * Peut aussi servir dans le cas d'inscription avec les emails.
     */
    @Column(unique = true)
    public String email;

    public int visitorIdentificationPourcent;

    @Enumerated(EnumType.STRING)
    public UserLogState status;

    @Override
    public String abbvr() {
        return "VSDB";
    }
}
