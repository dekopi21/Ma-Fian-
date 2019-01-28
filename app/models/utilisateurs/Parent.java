package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Parent extends ElisisModel {
    @ManyToOne
    public Utilisateur utilisateur;

    @OneToMany
    public List<Apprenant> apprenant;

    @OneToMany
    public List<Enseignant> enseignant;

    public Parent(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.apprenant = new ArrayList<Apprenant>();
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return "PARENT";
    }
}
