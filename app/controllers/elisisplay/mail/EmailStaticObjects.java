package controllers.elisisplay.mail;

/**
 * Created by Bienvenu on 25/08/2018 in demande_d_installation.
 */
public class EmailStaticObjects {

    /**
     * Google Mail SMTP address.
     */
    public static final String SMTP_GOOGLEMAIL_COM = "smtp.googlemail.com";

    /**
     * SMTP port.
     * SSL encryption is started automatically before any SMTP level communication.
     */
    public static final String EMAIL_SMTPS_PORT = "465";

    /**
     *
     * It is almost like standard SMTP port. MSA should accept email after authentication
     * (e.g. after SMTP AUTH).
     */
    public static final String EMAIL_MSA_PORT = "587";

}
