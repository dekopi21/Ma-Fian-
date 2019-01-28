package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "competance")
public class Competence extends ElisisModel {

    @Column(name = "libelle_competance",unique = true, nullable = false, length = 50)
    public String libelle;

    @Lob
    @Column(name = "description_competance",nullable = false)
    public String descriptioin;

    @Column(nullable = false, updatable = false)
    private Calendar date_edition;

    @ManyToOne(cascade = CascadeType.ALL)
    public Matiere matiere;

    public Competence(String libelle, String descriptioin ) {
        this.libelle = libelle;
        this.descriptioin = descriptioin;
        this.date_edition = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
    }

    @Override
    public String abbvr() {
        return null;
    }
}
