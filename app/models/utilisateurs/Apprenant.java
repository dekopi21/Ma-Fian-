package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Apprenant extends ElisisModel {
    @ManyToOne
    public Utilisateur utilisateur;

    @ManyToOne
    public Parent parent;

    /**
     *
     * @param parent
     * @param utilisateur
     */
    public Apprenant(Parent parent, Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.parent = parent;
        setCode();
        edit_date();
    }

    /**
     *
     * @return String, the generation index of code
     */
    @Override
    public String abbvr() {
        return "APPRE";
    }
}
