package controllers.elisisplay.logging;

import java.util.ArrayList;

/**
 * Created by Bienvenu on 27/08/2018 in controllers.elisisplay.
 */
public enum LoggingRessource {
    BINSERIALISATION;

    LoggingRessource() {
    }

    public ArrayList<LogMessage> getLogs() {
        switch (this) {
            case BINSERIALISATION:
                return SerialLogger.getLoggerObject().getLogMessages();

            default:
                return SerialLogger.getLoggerObject().getLogMessages();
        }
    }
}
