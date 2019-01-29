package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;
import play.data.validation.Required;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CategorieUtilisateur")
public class Categorie extends ElisisModel {

    @Column(name = "libelle_categorie",unique = true, nullable = false, length = 50)
    public String libelle;

    @OneToMany(cascade = CascadeType.ALL)
    @Required
    public List<Utilisateur> utilisateurs;

    @OneToOne
    public Profession profession;

    /**
     *
     * @param nomCategorie
     */
    public Categorie(String nomCategorie) {
        this.libelle = nomCategorie;
        this.utilisateurs = new ArrayList<Utilisateur>();
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return null;
    }
}
