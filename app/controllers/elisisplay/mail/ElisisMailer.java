package controllers.elisisplay.mail;

import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.logging.TypeLogMessageError;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import play.libs.Mail;
import play.mvc.Mailer;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bienvenu on 25/08/2018 in demande_d_installation.
 */
public class ElisisMailer extends Mailer {

    /**
     * Default mailer host used.
     */
    public static final String DEFAULT_MAILER_HOST = EmailStaticObjects.SMTP_GOOGLEMAIL_COM;

    /**
     * Default mail port used.
     */
    public static final String DEFAULT_MAILER_PORT = EmailStaticObjects.EMAIL_SMTPS_PORT;

    /**
     * Message send when Client does not support HtmlMessage.
     */
    public static final String CLIENT_DOES_NOT_SUPPORT_HTML_MSH = "Your email client does not support HTML, too bad :(";

    /**
     * Define if the mail service run or not.
     */
    private static final boolean IS_ENABLED = true;

    /**
     * Define if sending email is secure or not.
     */
    public static final boolean START_TLS_ENABLED = true;


    /**
     * Send email.
     *
     * @param body             body
     * @param subject          subject
     * @param emailAddressTo   Receiver address
     * @param emailAddressFrom Sender address
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendEmail(String body, String subject, String emailAddressTo, String emailAddressFrom) {

        if ((emailAddressFrom == null) || StringUtils.isEmpty(emailAddressFrom)) {
            return false;
        }

        if ((emailAddressTo == null) || StringUtils.isEmpty(emailAddressTo)) {
            return false;
        }

        if ((subject == null) || StringUtils.isEmpty(subject)) {
            return false;
        }

        if ((body == null) || StringUtils.isEmpty(body)) {
            return false;
        }

        boolean emailSent = false;
        try {
            Email email = new SimpleEmail();
            email.setHostName(DEFAULT_MAILER_HOST);
            email.setSmtpPort(Integer.valueOf(DEFAULT_MAILER_PORT));
            email.setSSLOnConnect(true);
            email.setFrom(emailAddressFrom);
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(emailAddressTo);
            email.send();
            emailSent = true;
        } catch (EmailException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
        }
        return emailSent;
    }

    /**
     * Send email to many receivers.
     * <p>
     * This can be used to send email to a service.
     *
     * @param receivers Email receivers address
     * @param subject   Email subject
     * @param content   Email content
     * @param isHtmlMsg If Email is a HtmlEmail
     * @param username  Sender username
     * @param password  Sender password
     * @param sender    Sender of email
     * @return {@code true} if email has been sent.
     */
    @SuppressWarnings("BooleanParameter")
    public static boolean sendMail(Iterable<String> receivers, String subject, String content, boolean isHtmlMsg,
                                   String username, String password, String sender) {

        if (IS_ENABLED) {

            Email email = new HtmlEmail();
            email.setHostName(DEFAULT_MAILER_HOST);
            email.setStartTLSEnabled(START_TLS_ENABLED);
            if (START_TLS_ENABLED) {
                email.setSslSmtpPort(EmailStaticObjects.EMAIL_MSA_PORT);
            } else {
                email.setSmtpPort(Integer.valueOf(EmailStaticObjects.EMAIL_SMTPS_PORT));
            }

            if ((username != null) && (!username.trim().isEmpty())) {
                email.setAuthentication(username, password);
            }

            email.setDebug(true);
            try {
                for (String receiver : receivers) email.addTo(receiver);

                email.setFrom(sender);
                email.setSubject(subject);
                email.setCharset("UTF-8");
                if (isHtmlMsg) {
                    ((HtmlEmail) email).setHtmlMsg(content);
                } else {
                    ((HtmlEmail) email).setTextMsg(content);
                }
                email.send();
                email.getMailSession();

            } catch (EmailException e) {
                Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Send email.
     * <p>
     * The Email exception is not caught.
     * <p>
     * This can be used for some test.
     *
     * @param hostName      hostname
     * @param smtpPort      smtpPort
     * @param authenticator authenticator
     * @param sender        sender address.
     * @param subject       Email subject
     * @param message       Email message
     * @param receiver      Email receiver address
     * @throws EmailException When parameters are not valid.
     */
    private static void sendEmail(String hostName, int smtpPort,
                                  DefaultAuthenticator authenticator, String sender, String subject,
                                  String message, String receiver) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(hostName);
        email.setSmtpPort(smtpPort);
        email.setAuthenticator(authenticator);
        email.setFrom(sender);
        email.setSubject(subject);
        email.setMsg(message);
        email.addTo(receiver);
        email.send();
    }

    /**
     * Send a email.
     *
     * @param emailData Email Data object.
     * @param username  username
     * @param password  passord
     * @return {@code true} if the email has been sent.
     */
    @SuppressWarnings("FeatureEnvy")
    public static boolean sendEmail(IEmailData emailData, String username, String password) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(DEFAULT_MAILER_HOST);
            email.setSmtpPort(Integer.parseInt(DEFAULT_MAILER_PORT));
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setSSLOnConnect(START_TLS_ENABLED);
            email.setFrom(emailData.getAddressFrom());
            email.setSubject(emailData.getSubject());
            email.setMsg(emailData.getMessageContent());
            email.addTo(emailData.getAddressTo());
            email.send();
            return true;
        } catch (EmailException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }

    /**
     * Send email to multiple receivers.
     *
     * @param mailAddress             Receivers mail address.
     * @param title                   Email title.
     * @param content                 Email content.
     * @param username                Sender username.
     * @param password                Sender password.
     * @param from                    Email sender address.
     * @param fromname                Email sender name.
     * @param mailAddressEndSeparator Characters that separate email receivers address in parameter {@code mailAddress}.
     * @return {@code true} if the Email has been sent.
     */
    public static boolean send(String mailAddress, String title, String content, String username,
                               String password, String from, String fromname, String mailAddressEndSeparator) {
        if (StringUtils.isBlank(mailAddress)) {
            return false;
        }

        try {
            Email email = new HtmlEmail();
            email.setHostName(DEFAULT_MAILER_HOST);
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setSmtpPort(Integer.parseInt(DEFAULT_MAILER_PORT));
            email.setFrom(from, fromname);
            email.setSubject(title);
            email.setMsg(content);
            email.addTo(mailAddress.split(mailAddressEndSeparator));
            email.send();
            return true;
        } catch (NumberFormatException | EmailException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }

    /**
     * Send Simple Text Email.
     *
     * @param port                  Port used to send the Email.
     * @param msg                   Message sent with the Attachement
     * @param host                  Host
     * @param username              Sender authentication username
     * @param password              Sender authentication password
     * @param from                  Email sender Address
     * @param subject               Email Subject
     * @param addTo                 Email Receiver Address
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendSimpleTextEmail(String host, int port, String from, String username,
                                              String password, String subject, String msg, String addTo) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(port);
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setSSLOnConnect(true);
            email.setFrom(from);
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(addTo);
            email.send();
            return true;
        } catch (EmailException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }

    /**
     * Send Email with Local attachement.
     *
     * @param attachmentPath        Local url of attachement.
     * @param msg                   Message sent with the Attachement
     * @param attachmentDescription Description of the Attachement.
     * @param host                  Host
     * @param attachmentName        Attachement Name (e.g. Welcome User).
     * @param from                  Email sender Address
     * @param subject               Email Subject
     * @param addTo                 Email Receiver Address
     * @param addToName             Email Receiver Name
     * @param fromName              EMail Sender Name
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendEmailWithLocalAttachment(String host, String from,
                                                       String subject, String msg, String addTo,
                                                       String attachmentPath, String attachmentDescription,
                                                       String attachmentName, String addToName, String fromName) {
        return sendEmailWithLocalAttachment(host, from, null, null, subject, msg, addTo, attachmentPath, attachmentDescription, attachmentName, addToName, fromName);
    }

    /**
     * Send Email with Local attachement.
     *
     * @param attachmentPath        Local url of attachement.
     * @param msg                   Message sent with the Attachement
     * @param attachmentDescription Description of the Attachement.
     * @param host                  Host
     * @param username              Sender authentication username
     * @param password              Sender authentication password
     * @param attachmentName        Attachement Name (e.g. Welcome User).
     * @param from                  Email sender Address
     * @param subject               Email Subject
     * @param addTo                 Email Receiver Address
     * @param addToName             Email Receiver Name
     * @param fromName              EMail Sender Name
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendEmailWithLocalAttachment(String host, String from, String username,
                                                       String password, String subject, String msg, String addTo,
                                                       String attachmentPath, String attachmentDescription,
                                                       String attachmentName, String addToName, String fromName) {
        try {
            // Create the attachment
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(attachmentPath);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription(attachmentDescription);
            attachment.setName(attachmentName);

            // Create the email message
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(host);
            email.addTo(addTo, addToName);
            email.setFrom(from, fromName);
            email.setSubject(subject);
            email.setMsg(msg);

            // add the attachment
            email.attach(attachment);

            if (controllers.elisisplay.lang.StringUtils.isNotEmpty(username) && controllers.elisisplay.lang.StringUtils.isNotEmpty(password))
                email.setAuthentication(username, password);
            // send the email
            email.send();
            return true;
        } catch (EmailException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }

    /**
     * Send Email with an online attachement.
     * <p>
     * When the message is sent, the file will be downloaded and attached to the message automatically.
     *
     * @param msg                   Message sent with the Attachement
     * @param attachmentDescription Description of the Attachement.
     * @param host                  Host
     * @param attachmentUrl         Attachement Url (e.g. /pdf/welcome.pdf).
     * @param attachmentName        Attachement Name (e.g. Welcome User).
     * @param from                  Email sender Address
     * @param subject               Email Subject
     * @param addTo                 Email Receiver Address
     * @param addToName             Email Receiver Name
     * @param fromName              EMail Sender Name
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendEmailWithOnlineAttachment(String host, String from, String subject, String msg, String addTo,
                                                        String attachmentUrl, String attachmentDescription,
                                                        String attachmentName, String addToName, String fromName) {
        return sendEmailWithOnlineAttachment(host, from, null, null, subject, msg, addTo, attachmentUrl,
                attachmentDescription, attachmentName, addToName, fromName);
    }

    /**
     * Send Email with an online attachement.
     * <p>
     * When the message is sent, the file will be downloaded and attached to the message automatically.
     *
     * @param msg                   Message sent with the Attachement
     * @param attachmentDescription Description of the Attachement.
     * @param host                  Host
     * @param username              Sender authentication username
     * @param password              Sender authentication password
     * @param attachmentUrl         Attachement Url (e.g. /pdf/welcome.pdf).
     * @param attachmentName        Attachement Name (e.g. Welcome User).
     * @param from                  Email sender Address
     * @param subject               Email Subject
     * @param addTo                 Email Receiver Address
     * @param addToName             Email Receiver Name
     * @param fromName              EMail Sender Name
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendEmailWithOnlineAttachment(String host, String from, String username,
                                                        String password, String subject, String msg, String addTo,
                                                        String attachmentUrl, String attachmentDescription,
                                                        String attachmentName, String addToName, String fromName) {
        try {
            // Create the attachment
            EmailAttachment attachment = new EmailAttachment();
            attachment.setURL(new URL(attachmentUrl));
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription(attachmentDescription);
            attachment.setName(attachmentName);

            // Create the email message
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(host);
            email.addTo(addTo, addToName);
            email.setFrom(from, fromName);
            email.setSubject(subject);
            email.setMsg(msg);

            // add the attachment
            email.attach(attachment);

            if (controllers.elisisplay.lang.StringUtils.isNotEmpty(username) && controllers.elisisplay.lang.StringUtils.isNotEmpty(password))
                email.setAuthentication(username, password);
            // send the email
            email.send();
            return true;
        } catch (EmailException | MalformedURLException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }


    /**
     * Send Html Formatted Email with an attachement.
     *
     * @param host           Host
     * @param attachmentUrl  Attachement Url (e.g. /pdf/welcome.pdf).
     * @param attachmentName Attachement Name (e.g. Welcome User).
     * @param from           Email sender Address
     * @param subject        Email Subject
     * @param addTo          Email Receiver Address
     * @param addToName      Email Receiver Name
     * @param fromName       EMail Sender Name
     * @param alternativeMsg Alternative message to use when the Html image can't display.
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendHtmlFormattedEmail(String host, String from,
                                                 String subject, String addTo,
                                                 String attachmentUrl,
                                                 String attachmentName, String addToName, String fromName,
                                                 String alternativeMsg) {
        return sendHtmlFormattedEmail(host, from, null, null, subject, addTo, attachmentUrl, attachmentName, addToName, fromName, alternativeMsg);
    }

    /**
     * Send Html Formatted Email with an attachement.
     *
     * @param host           Host
     * @param username       Sender authentication username
     * @param password       Sender authentication password
     * @param attachmentUrl  Attachement Url (e.g. /pdf/welcome.pdf).
     * @param attachmentName Attachement Name (e.g. Welcome User).
     * @param from           Email sender Address
     * @param subject        Email Subject
     * @param addTo          Email Receiver Address
     * @param addToName      Email Receiver Name
     * @param fromName       EMail Sender Name
     * @param alternativeMsg Alternative message to use when the Html image can't display.
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendHtmlFormattedEmail(String host, String from, String username,
                                                 String password, String subject, String addTo,
                                                 String attachmentUrl,
                                                 String attachmentName, String addToName, String fromName,
                                                 String alternativeMsg) {
        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName(host);
            email.addTo(addTo, addToName);
            email.setFrom(from, fromName);
            email.setSubject(subject);

            // embed the image and get the content id
            URL url = new URL(attachmentUrl);
            String cid = email.embed(url, attachmentName);

            // set the html message
            email.setHtmlMsg("<html> " + attachmentName + " - <img src=\"cid:" + cid + "\"></html>");

            // set the alternative message
            email.setTextMsg(alternativeMsg);

            if (controllers.elisisplay.lang.StringUtils.isNotEmpty(username) && controllers.elisisplay.lang.StringUtils.isNotEmpty(password))
                email.setAuthentication(username, password);

            // send the email
            email.send();
            return true;
        } catch (EmailException | MalformedURLException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }

    /**
     * Send Template Html Email.
     * <p>
     * The previous example showed how to create a HTML email with embedded images but you need to
     * know all images upfront which is inconvenient when using a HTML email template.
     * The ImageHtmlEmail helps you solving this problem by converting all external images to inline images.
     *
     * @param host               Host
     * @param from               Email sender Address
     * @param subject            Email Subject
     * @param addTo              Email Receiver Address
     * @param addToName          Email Receiver Name
     * @param fromName           EMail Sender Name
     * @param alternativeMsg     Alternative message to use when the Html image can't display.
     * @param applicationBaseUrl Application base url (e.g. localhost:9000 or www.yourDomain.com).
     * @param imageRelativeUrl   Relative path url of the image file (e.g. /images/img.png).
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendHtmlTemplatedImageEmail(String host, String from,
                                                      String subject, String addTo,
                                                      String addToName, String fromName,
                                                      String alternativeMsg, String applicationBaseUrl,
                                                      String imageRelativeUrl) {
        return sendHtmlTemplatedImageEmail(host, from, null, null, subject, addTo, addToName, fromName, alternativeMsg, applicationBaseUrl, imageRelativeUrl);
    }

    /**
     * Send Template Html Email.
     * <p>
     * The previous example showed how to create a HTML email with embedded images but you need to
     * know all images upfront which is inconvenient when using a HTML email template.
     * The ImageHtmlEmail helps you solving this problem by converting all external images to inline images.
     *
     * @param host               Host
     * @param from               Email sender Address
     * @param username           Sender authentication username
     * @param password           Sender authentication password
     * @param subject            Email Subject
     * @param addTo              Email Receiver Address
     * @param addToName          Email Receiver Name
     * @param fromName           EMail Sender Name
     * @param alternativeMsg     Alternative message to use when the Html image can't display.
     * @param applicationBaseUrl Application base url (e.g. localhost:9000 or www.yourDomain.com).
     * @param imageRelativeUrl   Relative path url of the image file (e.g. /images/img.png).
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendHtmlTemplatedImageEmail(String host, String from, String username,
                                                      String password, String subject, String addTo,
                                                      String addToName, String fromName,
                                                      String alternativeMsg, String applicationBaseUrl,
                                                      String imageRelativeUrl) {
        try {
            // define you base URL to resolve relative resource locations
            URL url = new URL(applicationBaseUrl);

            // create the email message
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName(host);
            email.addTo(addTo, addToName);
            email.setFrom(from, fromName);
            email.setSubject(subject);

            // load your HTML email template
            String htmlEmailTemplate = ".... <img src=\" " + applicationBaseUrl + imageRelativeUrl + "\"> ....";

            // set the html message
            email.setHtmlMsg(htmlEmailTemplate);

            // set the alternative message
            email.setTextMsg(alternativeMsg);

            if (controllers.elisisplay.lang.StringUtils.isNotEmpty(username) && controllers.elisisplay.lang.StringUtils.isNotEmpty(password))
                email.setAuthentication(username, password);

            // send the email
            email.send();
            return true;
        } catch (EmailException | MalformedURLException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }


    /*
    Authentication

    If you need to authenticate to your SMTP server, you can call the
    setAuthentication(userName,password) method before sending your email.
    This will create an instance of DefaultAuthenticator which will be used by the JavaMail
    API when the email is sent. Your server must support RFC2554 in order for this to work.

    security

    Nowadays you should not use plain SMTP protocol when using public SMTP servers
    but there is a some confusion regarding the available options.

    Two commons options are using

    STARTTLS on port 25
    SSL on port 465

    The following definitions were taken from Wikipedia

    STARTTLS is an extension to plain text communication protocols, which offers a way to
    upgrade a plain text connection to an encrypted (TLS or SSL) connection instead of using a
    separate port for encrypted communication.
    Transport Layer security (TLS) and its predecessor, Secure Sockets Layer (SSL), are
    cryptographic protocols that provide communication security over the Internet.TLS and
    SSL encrypt the segments of network connections above the Transport Layer, using asymmetric
    cryptography for key exchange, symmetric encryption for privacy, and message authentication
    codes for message integrity.

    In addition you can force the following security checks (which are disabled by default)

    When using a secured transport (STARTTLS or SSL) you can force validating the server's
    certificate by calling Email.setSSLCheckServerIdentity(true).
    Enforce using STARTTLS by calling Email.setStartTLSRequired(true)

    Handling Bounced Messages

    Normally, messages which cannot be delivered to a recipient are returned to the sender
    (specified with the from property). However, in some cases, you'll want these to be sent
    to a different address. To do this, simply call the setBounceAddress(emailAddressString)
    method before sending your email.

    Technical notes: When SMTP servers cannot deliver mail, they do not pay any attention to the contents
    of the message to determine where the error notification should be sent. Rather, they refer to the SMTP
    "envelope sender" value. JavaMail sets this value according to the value of the mail.smtp.from property
    on the JavaMail Session. (Commons Email initializes the JavaMail Session using System.getProperties())
    If this property has not been set, then JavaMail uses the "from" address. If your email bean has the
    bounceAddress property set, then Commons Email uses it to set the value of mail.smtp.from when the
    Session is initialized, overriding any other value which might have been set.

    Note: This is the only way to control the handling of bounced email. Specifically, the "Errors-to:"
    SMTP header is deprecated and cannot be trusted to control how a bounced message will be handled.
    Also note that it is considered bad practice to send email with an untrusted "from" address unless
    you also set the bounce address. If your application allows users to enter an address which is used
    as the "from" address on an email, you should be sure to set the bounce address to a known good address.

     */

    /**
     * Send simple email with Play.
     *
     * @param receiverAddress Email Receiver address.
     * @param senderAddress   Email Sender address.
     * @param subject         Email Subject.
     * @param message         Email content.
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendPlaySimpleEmail(String receiverAddress, String senderAddress, String subject, String message) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setFrom(senderAddress);
            email.addTo(receiverAddress);
            email.setSubject(subject);
            email.setMsg(message);
            Mail.send(email);
            return true;
        } catch (EmailException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }

    /**
     * Send Html Email with Play default implementation.
     *
     * @param receiverAddress Email Receiver address.
     * @param senderAddress   Email Sender address.
     * @param senderName      Email Sender Name.
     * @param subject         Email Subject.
     * @param imageUrl        Url of image to embed with the content.
     * @param imageName       Image embedded name.
     * @param htmlMsg         HTLM message (code) to send.
     * @param textMsg         Text message used if Client does not support HTML message.
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendPlayHtmlEmail(String receiverAddress, String senderAddress, String senderName, String subject,
                                            String imageUrl, String imageName, String htmlMsg, String textMsg, String message) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.addTo(receiverAddress);
            email.setFrom(senderAddress, senderName);
            email.setSubject(subject);
            email.setMsg(message);
            // embed the image and get the content id
            URL url = new URL(imageUrl);
            //TODO : Check this unused variable.
            String cid = email.embed(url, imageName);

            // set the html message
            email.setHtmlMsg(htmlMsg);
            // set the alternative message
            email.setTextMsg(textMsg);

            email.send();
            return true;
        } catch (EmailException | MalformedURLException e) {
            Logger.log(TypeLogMessageError.SENDINGEMAILERROR.setThrowable(e));
            return false;
        }
    }

    /**
     * Get HTmlEmail from a image and his name (or content to embeed with the email).
     *
     * @param imageName Image Name (or content to embed with him).
     * @param imageUrl  Url used to get (download the image).
     * @return HTmlEmail from a image and his name.
     */
    private static String getEmailHtml(String imageName, String imageUrl) {
        return "<html>" + imageName + " - <img src=\"cid:" + imageUrl + "\"></html>";
    }

    /**
     * Send Html Email with Play default implementation.
     *
     * @param receiverAddress Email Receiver address.
     * @param senderAddress   Email Sender address.
     * @param senderName      Email Sender Name.
     * @param subject         Email Subject.
     * @param imageUrl        Url of image to embed with the content.
     * @param imageName       Image embedded name.
     * @param message         Message add to the HTML Email
     * @return {@code true} if the email has been sent.
     */
    public static boolean sendPlayHtmlEmail(String receiverAddress, String senderAddress, String senderName, String subject,
                                            String imageUrl, String imageName, String message) {
        return sendPlayHtmlEmail(receiverAddress, senderAddress, senderName, subject,
                imageUrl, imageName, getEmailHtml(imageName, imageUrl), CLIENT_DOES_NOT_SUPPORT_HTML_MSH, message);
    }
}
