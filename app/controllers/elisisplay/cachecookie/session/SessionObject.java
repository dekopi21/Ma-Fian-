package controllers.elisisplay.cachecookie.session;

import java.util.Map;

/**
 * Abstract class who implements partially, {@code ISessionObject}.
 * @see ISessionObject
 */
public abstract class SessionObject implements ISessionObject{

    /**
     * There must be a date field that contains the last date this object value changed.
     */
    protected abstract void changeLastTimeAccess();

    /**
     * THis can be used to flush or persist session data.
     * @return All the session objects.
     */
    protected abstract Map<String, Object> getData();
}
