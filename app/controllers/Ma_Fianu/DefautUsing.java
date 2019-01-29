package controllers.Ma_Fianu;

import controllers.elisisplay.crypto.Crypto;
import models.utilisateurs.CompteUtilisateur;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class DefautUsing  extends Job {

    public static String ADMINUSERNAME="dekopi";

    public static String ADMINUSEREMAIL="dekopi@gmail.com";

    public static String ADMINUSERPASSWORD="P@$sWord2000Admin";

    public static void  AdminAccount(){
       if(CompteUtilisateur.find("byIsAdmin", false).<CompteUtilisateur>first().isAdmin)
        new CompteUtilisateur(DefautUsing.ADMINUSEREMAIL, Crypto.hexMD5(DefautUsing.ADMINUSERPASSWORD)).save();
    }

    public void doJob(){
        AdminAccount();
    }
}
