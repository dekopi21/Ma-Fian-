package controllers.elisisplay.mail;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.DateTime;

import java.util.Objects;

/**
 * Email default Data.
 */
public class EmailData implements IEmailData{

    /**
     * Received date of email
     */
    private DateTime receivedDateTime;

    /**
     * Email content.
     */
    private String messageContent;

    /**
     * Email message subject.
     */
    private String subject;

    /**
     * Email sender address.
     */
    private String addressFrom;

    /**
     * Email receiver address.
     */
    private String addressTo;

    /**
     * @return Email content.
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * Sets email content.
     *
     * @param messageContent the content of email
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent.trim();
    }

    /**
     * @return Email message receive DateTime.
     */
    public DateTime getReceivedDateTime() {
        return receivedDateTime;
    }

    /**
     * Sets email receive DateTime.
     *
     * @param receivedDateTime received date of email
     */
    public void setReceivedDateTime(DateTime receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    /**
     * @return Email message subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets email subject.
     *
     * @param subject email subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return Email sender address.
     */
    public String getAddressFrom() {
        return addressFrom;
    }

    /**
     * Sets email sender address.
     *
     * @param addressFrom sender email address
     */
    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    /**
     * @return Email receiver address.
     */
    public String getAddressTo() {
        return addressTo;
    }

    /**
     * Sets email receiver address.
     *
     * @param addressTo receiver email address
     */
    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, messageContent, addressTo);
    }

    // this method is needed for comparing emailData instances in tests
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        EmailData emailData = (EmailData) obj;
        return new EqualsBuilder()
                .append(subject, emailData.getSubject())
                .append(messageContent, emailData.getMessageContent())
                .append(addressTo, emailData.getAddressTo())
                .isEquals();
    }

    @Override
    public String toString() {
        return "EmailData [receivedDateTime=" + receivedDateTime + ", messageContent=" + messageContent
                + ", subject=" + subject + ", addressFrom=" + addressFrom + ", addressTo=" + addressTo
                + ']';
    }
}
