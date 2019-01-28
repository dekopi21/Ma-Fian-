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

    @OneToMany
    public List<Enseignant> enseignant;

    public Apprenant(Parent parent, Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.parent = parent;
        this.enseignant = new ArrayList<Enseignant>();
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return "APPRE";
    }
}
