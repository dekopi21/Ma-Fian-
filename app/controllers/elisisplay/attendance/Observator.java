package controllers.elisisplay.attendance;

import controllers.elisisplay.attendance.objects.AttendanceTypeObject;
import controllers.elisisplay.attendance.objects.Page;
import controllers.elisisplay.attendance.objects.Visitor;
import controllers.elisisplay.attendance.objects.VisitorIdentificationMethod;
import controllers.elisisplay.attendance.observator.ActionObservator;
import controllers.elisisplay.attendance.observator.PageAttendedObservator;
import controllers.elisisplay.attendance.observator.PageObservator;
import controllers.elisisplay.attendance.observator.VisitorObservator;
import controllers.elisisplay.attendance.serials.All;
import controllers.elisisplay.secure.ApplicationSection;
import controllers.elisisplay.utils.Utils;
import play.mvc.Http.Request;
import play.mvc.Http.Response;

/**
 * Classe qui permet de recueillir les divers statistiques d'utilisations
 * de la plateforme.
 */
public class Observator implements IObservator{

    private static final int defaultNbRecordsBeforeFlush = 5;

    private int nbRecordsBeforeFlush = defaultNbRecordsBeforeFlush;

    private int nbRecords;

    public static final int defaultSerialLenght = 20;

    /**
     * Observateur des actions.
     */
    private ActionObservator actionObservator;

    /**
     * Observateur des pages.
     */
    private PageObservator pageObservator;

    /**
     * Observateur des visites.
     */
    private PageAttendedObservator attendedObservator;

    /**
     * Observateur des visiteurs.
     */
    private VisitorObservator visitorObservator;

    /**
     * Méthode utilisée pour reconnaitre les visiteurs.
     */
    VisitorIdentificationMethod identificationMethod;

    public Observator(int recordActionsInterval, int recordAttendsInterval) {
        pageObservator = new PageObservator();
        visitorObservator = new VisitorObservator();
        actionObservator = new ActionObservator(recordActionsInterval);
        attendedObservator = new PageAttendedObservator(recordAttendsInterval);
        safeInit();
    }

    public Observator(int recordActionsInterval, int recordAttendsInterval, int nbRecordsBeforeFlush) {
        pageObservator = new PageObservator();
        visitorObservator = new VisitorObservator();
        actionObservator = new ActionObservator(recordActionsInterval);
        attendedObservator = new PageAttendedObservator(recordAttendsInterval);
        this.nbRecordsBeforeFlush = nbRecordsBeforeFlush;
        safeInit();
    }

    public Observator(int recordActionsInterval, int recordAttendsInterval, VisitorIdentificationMethod identificationMethod) {
        pageObservator = new PageObservator();
        visitorObservator = new VisitorObservator();
        actionObservator = new ActionObservator(recordActionsInterval);
        attendedObservator = new PageAttendedObservator(recordAttendsInterval);
        this.identificationMethod = identificationMethod;
        init(identificationMethod);
    }

    public void observeAction(Request request, Response response) {
        actionObservator.observe(request, response, null, null);
    }

    public void observePage(Request request, ApplicationSection section, String code, String description) {
        pageObservator.observe(request, section, code, description);
    }

    public void observePageAttended(Request request) {
        attendedObservator.observe(request);
    }

    public void observeVisitor(Visitor visitor) {
        visitorObservator.observe(visitor);
    }

    public void observe(Request request, Response response, ApplicationSection applicationSection,
                        Page actual, Page requested, String code, String description,
                        Visitor visitor) {
        pageObservator.observe(request, applicationSection, code, description);
        visitorObservator.observe(visitor);
        actionObservator.observe(request, response, actual, requested);
        attendedObservator.observe(request);
        _flush();
    }

    private void recordInc() {
        ++nbRecords;
    }

    public void observe(Request request, Response response, ApplicationSection applicationSection,
                        String description, Visitor visitor) {
        pageObservator.observe(request, applicationSection, description);
        actionObservator.observe(request, response,
                (Page) Utils.retrieveObjectFromList(All.pages(), "method", request.invokedMethod), null);
        visitorObservator.observe(visitor);
        attendedObservator.observe(request);
        _flush();
    }

    public boolean load(AttendanceTypeObject typeObject) {
        switch (typeObject) {
            case VISITOR:
                return visitorObservator.load();
            case PAGE:
                return pageObservator.load();
            case PAGEATTENDED:
                return attendedObservator.load();
            case ACTIONONPAGE:
                return actionObservator.load();
        }
        return false;
    }

    private void _flush() {
        recordInc();
        //if ((nbRecords % nbRecordsBeforeFlush) == 0)
            flush();
    }

    public boolean flush(AttendanceTypeObject typeObject) {
        switch (typeObject) {
            case VISITOR:
                return visitorObservator.flush();
            case PAGE:
                return pageObservator.flush();
            case PAGEATTENDED:
                return attendedObservator.flush();
            case ACTIONONPAGE:
                return actionObservator.flush();
        }
        return false;
    }

    public boolean flush() {
        return visitorObservator.flush() && pageObservator.flush()
                && attendedObservator.flush() && actionObservator.flush();
    }

    public boolean load() {
        return visitorObservator.load() && pageObservator.load()
                && attendedObservator.load() && actionObservator.load();
    }

    public void init() {
        visitorObservator.init();
        pageObservator.init();
        attendedObservator.init();
        actionObservator.init();
    }

    private void safeInit() {
        visitorObservator.safeInit();
        pageObservator.safeInit();
        attendedObservator.safeInit();
        actionObservator.safeInit();
    }

    public void init(VisitorIdentificationMethod identificationMethod) {
    }
}
