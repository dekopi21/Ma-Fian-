package controllers.elisisplay.attendance.serials;

import controllers.elisisplay.attendance.Attendance;
import controllers.elisisplay.attendance.objects.ActionOnPage;
import controllers.elisisplay.serial.ElisisSerial;

/**
 * Actions on Objects serial file.
 */
public class ActionsOnPages extends ElisisSerial<ActionOnPage> {

    public ActionsOnPages(boolean initSerial) {
        super(initSerial);
    }

    private static final long serialVersionUID = 1000000000000000003L;

    @Override
    public String filename() {
        return "actionsonpages.ser";
    }

    @Override
    public String dirPlayPath() {
        return Attendance.attendanceDirPath;
    }
}
