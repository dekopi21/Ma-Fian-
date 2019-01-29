package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Table(name = "gallerie")
public class Gallerie extends ElisisModel {

    @Column(name = "chemin_absolu_image", nullable = false, unique = true)
    public String nomCompletImage;

    @ManyToOne
    public CompteUtilisateur compteUtilisateur;

    /**
     *
     * @param nomCompletImage
     */
    public Gallerie(String nomCompletImage) {
        this.nomCompletImage = nomCompletImage;
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return null;
    }
}
