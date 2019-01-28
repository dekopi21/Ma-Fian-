package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Enseignant extends ElisisModel {
    @ManyToOne(cascade = CascadeType.ALL)
    public Utilisateur utilisateur;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Parcours> parcours;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Etablissement> etablissement;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Matiere> matieres;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Apprenant> apprenant;

    @OneToMany
    public List<Note> note;

    public Enseignant(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.parcours = new ArrayList<Parcours>();
        this.etablissement = new ArrayList<Etablissement>();
        this.matieres = new ArrayList<Matiere>();
        this.apprenant = new ArrayList<Apprenant>();
        this.note = new ArrayList<Note>();
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return "TEACHER";
    }
}
