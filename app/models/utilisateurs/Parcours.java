package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "parcours")
public class Parcours extends ElisisModel {

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "Diplome", nullable = false, unique = true)
    public List<Diplome> diplomes;

    @Column(name = "Optention_Diplome", nullable = false)
    public Calendar OptententionDiplome;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "certificat", unique = true)
    public List<Certificat> certificats;

    @Column(name = "Optention_certificat", nullable = false)
    public Calendar OptentionCertificat;


    @Column(name = "qualification", nullable = false)
    public int qualification;

    /**
     *
     * @param optententionDiplome
     * @param optentionCertificat
     */
    public Parcours( Calendar optententionDiplome,  Calendar optentionCertificat) {
        this.diplomes = new ArrayList<Diplome>();
        OptententionDiplome = optententionDiplome;
        this.certificats = new ArrayList<Certificat>();
        OptentionCertificat = optentionCertificat;
        this.qualification = 0;
      edit_date();
        setCode();
    }

    @Override
    public String abbvr() {
        return "PARC";
    }
}
