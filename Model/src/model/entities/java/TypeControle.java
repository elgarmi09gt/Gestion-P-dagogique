package model.entities.java;

import java.util.Map;

public class TypeControle {
    Long idTypeControle;
    String libelle;
    Long idFiliereEc;
    private Map<String, String> notesTpCntrlEtudiant;
    public TypeControle() {
        super();
    }

    public TypeControle(Long idTypeControle, String libelle, Long idFiliereEc,
                        Map<String, String> notesTpCntrlEtudiant) {
        this.idTypeControle = idTypeControle;
        this.libelle = libelle;
        this.idFiliereEc = idFiliereEc;
        this.notesTpCntrlEtudiant = notesTpCntrlEtudiant;
    }

    public void setIdTypeControle(Long idTypeControle) {
        this.idTypeControle = idTypeControle;
    }

    public Long getIdTypeControle() {
        return idTypeControle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdFiliereEc(Long idFiliereEc) {
        this.idFiliereEc = idFiliereEc;
    }

    public Long getIdFiliereEc() {
        return idFiliereEc;
    }

    public void setNotesTpCntrlEtudiant(Map<String, String> notesTpCntrlEtudiant) {
        this.notesTpCntrlEtudiant = notesTpCntrlEtudiant;
    }

    public Map<String, String> getNotesTpCntrlEtudiant() {
        return notesTpCntrlEtudiant;
    }
    
    @Override
    public boolean equals(Object o) {
        // TODO Implement this method
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
                 "null instanceof [type]" also returns false */
        if (!(o instanceof TypeControle)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        TypeControle c = (TypeControle) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getIdTypeControle().compareTo(this.getIdTypeControle()) == 0 ;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.idTypeControle.hashCode()+getIdFiliereEc().hashCode();
    }

    public String getNoteTcEtudiant(String num) {
        if (this.notesTpCntrlEtudiant != null && this.notesTpCntrlEtudiant.size() != 0) {
            if (null == notesTpCntrlEtudiant.get(num))
                return "ABS";
            return notesTpCntrlEtudiant.get(num);
        }
        return "-";
    }
}
