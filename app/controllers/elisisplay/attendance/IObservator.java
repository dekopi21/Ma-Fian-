package controllers.elisisplay.attendance;

import controllers.elisisplay.attendance.objects.AttendanceTypeObject;
import controllers.elisisplay.attendance.objects.Page;
import controllers.elisisplay.attendance.objects.Visitor;
import controllers.elisisplay.attendance.objects.VisitorIdentificationMethod;
import controllers.elisisplay.secure.ApplicationSection;
import play.mvc.Http.Request;
import play.mvc.Http.Response;

/**
 * Interface for mains observators classes.
 */
public interface IObservator {

    void observeAction(Request request, Response response);

    void observePage(Request request, ApplicationSection section, String code, String description);

    void observePageAttended(Request request);

    void observeVisitor(Visitor visitor);

    void observe(Request request, Response response, ApplicationSection applicationSection,
                 Page actual, Page requested, String code, String description,
                 Visitor visitor);

    void observe(Request request, Response response, ApplicationSection applicationSection,
                 String description, Visitor visitor);

    boolean load(AttendanceTypeObject typeObject);

    boolean flush(AttendanceTypeObject typeObject);

    boolean flush();

    boolean load();

    void init();

    void init(VisitorIdentificationMethod identificationMethod);
}
