package controllers.elisisplay.logging;

import controllers.elisisplay.reflect.Utils;
import controllers.elisisplay.utils.DateUtils;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Objet Message utilisé pour faciliter le Logging.
 */
public class LogMessage implements Serializable {

    private static final long serialVersionUID = 1350092951346723538L;

    public Level level;

    public String message = "";

    public String stackMessage = "";

    public GregorianCalendar dateMessage;

    public String dateMessageStr = "";

    public String callerMethod;

    public String calleeMethod;

    public LogMessage(Level level, String message, Throwable throwable) {
        this.level = level;
        this.message = message;
        this.stackMessage = throwable.getLocalizedMessage();
        this.dateMessage = new GregorianCalendar();
        dateMessageStr = DateUtils.formatFrDataTable(dateMessage);
        methodSignature(throwable);
    }

    public LogMessage(Level level, String message) {
        this.level = level;
        this.message = message;
        this.dateMessage = new GregorianCalendar();
        dateMessageStr = DateUtils.formatFrDataTable(dateMessage);
    }

    public LogMessage(TypeLogMessageError typeError, Throwable throwable) {
        methodSignature(throwable);
        this.level = typeError.getLevel();
        this.message = typeError.getMessage() + " \n " + typeError.name() + " " + typeError.getParticularMessage();
        this.dateMessage = new GregorianCalendar();
        this.stackMessage = throwable.getLocalizedMessage();
        dateMessageStr = DateUtils.formatFrDataTable(dateMessage);
    }

    public LogMessage(TypeLogMessageError typeError, Throwable throwable, String message) {
        methodSignature(throwable);
        this.level = typeError.getLevel();
        this.message = message;
        this.dateMessage = new GregorianCalendar();
        this.stackMessage = throwable.getLocalizedMessage();
        dateMessageStr = DateUtils.formatFrDataTable(dateMessage);
    }

    public LogMessage(TypeLogMessageError typeError) {
        this.level = typeError.getLevel();
        this.message = typeError.getMessage() + " \n " + typeError.name() + " " + typeError.getParticularMessage();
        this.dateMessage = new GregorianCalendar();
        if (typeError.getThrowable() != null)
            this.stackMessage = typeError.getThrowable().getLocalizedMessage();
        dateMessageStr = DateUtils.formatFrDataTable(dateMessage);
        if (typeError.getThrowable() != null) {
            methodSignature(typeError.getThrowable());
        }
    }

    private void setCalleeMethod(Throwable throwable) {
        this.calleeMethod = Utils.calleeMethodSignature(throwable);
    }

    private void setCallerMethod(Throwable throwable) {
        this.callerMethod = Utils.callerMethodSignature(throwable);
    }

    private void methodSignature(Throwable throwable) {
        setCalleeMethod(throwable);
        setCallerMethod(throwable);
    }
}
