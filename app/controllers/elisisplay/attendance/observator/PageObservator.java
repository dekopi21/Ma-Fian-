package controllers.elisisplay.attendance.observator;

import controllers.elisisplay.attendance.objects.Page;
import controllers.elisisplay.attendance.serials.All;
import controllers.elisisplay.attendance.serials.Pages;
import controllers.elisisplay.secure.ApplicationSection;
import controllers.elisisplay.utils.Utils;
import play.mvc.Http;

/**
 * Observateur des pages.
 */
public class PageObservator extends Observator<Page> {

    public PageObservator() {
        serial = new Pages(false);
    }

    private void record(Http.Request request, ApplicationSection section, String code, String description) {
        serial.add(new Page(request, section, code, description));
    }

    private void record(Http.Request request, ApplicationSection section, String description) {
        serial.add(new Page(request, section, Utils.generateCode("PAGE", All.pages().size() + 1, controllers.elisisplay.attendance.Observator.defaultSerialLenght), description));
    }

    public void observe(Http.Request request, ApplicationSection section, String code, String description) {
        record(request, section, code, description);
    }

    public void observe(Http.Request request, ApplicationSection section, String description) {
        record(request, section, description);
    }
}
