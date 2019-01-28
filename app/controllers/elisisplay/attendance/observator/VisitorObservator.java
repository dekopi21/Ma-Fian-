package controllers.elisisplay.attendance.observator;

import controllers.elisisplay.attendance.objects.Visitor;
import controllers.elisisplay.attendance.serials.Visitors;

import java.util.ArrayList;

/**
 * Created by Bienvenu on 30/10/2018 in schoolar_dev.
 */
public class VisitorObservator extends Observator<Visitor> {

    public VisitorObservator() {
        serial = new Visitors(false);
    }

    public ArrayList<Visitor> all() {
        return serial.all();
    }

    public void record(Visitor visitor) {
        serial.add(visitor);
    }

    public void observe(Visitor visitor) {
        record(visitor);
    }
}
