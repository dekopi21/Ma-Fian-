package controllers.Ma_Fianu;

import controllers.CRUD;
import controllers.Secure;
import models.utilisateurs.CompteUtilisateur;
import play.mvc.Before;



public class Dashboard extends CRUD {
    @Before
    static void setConnectedUser() {
        if(Secure.Security.isConnected()) {
            CompteUtilisateur user = CompteUtilisateur.find("byEmail", Secure.Security.connected()).first();
            renderArgs.put("user", user.utilisateurs.nom);
        }
    }

    public static void indexDashboard(){
        render();
    }
}
