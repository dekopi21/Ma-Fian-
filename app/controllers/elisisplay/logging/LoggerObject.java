package controllers.elisisplay.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Objet utilisé pour le logging.
 */
public class LoggerObject implements Serializable{

    private static final long serialVersionUID = 1350042881346723701L;

    /**
     *
     */
    protected ArrayList<LogMessage> logMessages = new ArrayList<>();

    public ArrayList<LogMessage> getLogMessages() {
        return logMessages;
    }

    /**
     *
     */
    public void init(){
        if (logMessages == null)
            logMessages = new ArrayList<>();
    }

    /**
     *
     * @param logMessage
     */
    public void addLogMessage(LogMessage logMessage)
    {
        init();
        logMessages.add(logMessage);
    }

    /**
     *
     * @param logMessageCollection
     */
    public void addAllLogMessage(Collection<LogMessage> logMessageCollection)
    {
        init();
        logMessages.addAll(logMessageCollection);
    }

    public void reset(){
        logMessages.clear();
    }
}
