package controllers.elisisplay.serial;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for Serials Objects.
 */
public interface IElisisSerial<S extends Serializable> extends IOneObjectSerial {

    /**
     * Number of serials objects recorded.
     *
     * @return The number of serials objects recorded.
     */
    int size();

    /**
     * Add objects to the exist one.
     *
     * @param objectsToAdd Objects to Add.
     */
    void add(List<S> objectsToAdd);

    /**
     * Add object to the exist one.
     *
     * @param objectToAdd Object to Add.
     */
    void add(S objectToAdd);

    /**
     * Remove object in the exist one.
     *
     * @param objectToRemove Object to remove.
     */
    void remove(S objectToRemove);

    void removeAll(S objectToRemove);

    /**
     * Remove objects in the exist one.
     *
     * @param objectsToRemove Object to remove.
     */
    void remove(List<S> objectsToRemove);

    /**
     * Last object saved.
     *
     * @return The last object saved on the serial object.
     */
    S last();

    /**
     * Last objects saved.
     *
     * @param interval Number of last objects to return.
     * @return The last {@code interval} objects saved.
     */
    ArrayList<S> last(int interval);

    /**
     * First object saved.
     *
     * @return The first object saved on the serial object.
     */
    ArrayList<S> first();

    /**
     * First objects saved.
     *
     * @param interval Number of first objects to return.
     * @return The first {@code interval} objects saved.
     */
    ArrayList<S> first(int interval);

    /**
     * Search objects which equals to the parameter passed.
     *
     * @param search The object with who compare the one saved on the serial.
     * @return Objects equals with the object passed.
     */
    ArrayList<S> search(S search);

    /**
     * Retrieve objects saved at a date.
     *
     * @param calendar The date objects were saved.
     * @return The objects saved at the date passed in parameter.
     */
    ArrayList<S> search(DateTime calendar);

    /**
     * @return all objects in serial file.
     */
    ArrayList<S> all();

    /**
     * Reset the serial file to contains empty object.
     *
     * @return {@code true} if the serial file has been reset.
     */
    boolean reset();
}
