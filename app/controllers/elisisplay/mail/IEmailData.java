package controllers.elisisplay.mail;

/**
 * Interface for Email Data classes.
 */
public interface IEmailData {
    
    String getAddressFrom();

    String getSubject();

    String getMessageContent();

    String getAddressTo();
}
