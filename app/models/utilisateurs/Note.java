package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Note extends ElisisModel {
    @Column()
    public double note;

    @ManyToOne
    public Enseignant enseignant;

    @ManyToOne
    public  Parent parent;

    @Override
    public String abbvr() {
        return null;
    }

    /**
     *
     * @param note
     * @param enseignant
     * @param parent
     */
    public Note(double note, Enseignant enseignant, Parent parent) {
        this.note = note;
        this.enseignant = enseignant;
        this.parent = parent;
        setCode();
        edit_date();
    }
}
