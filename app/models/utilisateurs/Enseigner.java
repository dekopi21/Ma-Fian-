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
public class Enseigner extends ElisisModel {
    @ManyToOne
    public Enseignant enseignant;

    @ManyToOne
    public Apprenant apprenant;

    @OneToMany
    public List<Matiere> matieres;

    /**
     *
     * @param enseignant
     * @param apprenant
     */
    public Enseigner(Enseignant enseignant, Apprenant apprenant) {
        this.enseignant = enseignant;
        this.apprenant = apprenant;
        this.matieres = new ArrayList<Matiere>();
    }

    @Override
    public String abbvr() {
        return "ENGRER";
    }
}
