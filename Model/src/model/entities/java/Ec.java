package model.entities.java;

import java.util.Map;
import java.util.Set;


public class Ec {
    private Long idEc;
    private Long idFilUe;
    private String libelleEc;
    private Double coef;
    private Map<String, String> notesEcEtudiant;
    private Set<TypeControle> lisTypeCntrle;
    
    public Ec() {
        super();
    }

    public void setIdEc(Long idEc) {
        this.idEc = idEc;
    }

    public Long getIdEc() {
        return idEc;
    }

    public void setIdFilUe(Long idFilUe) {
        this.idFilUe = idFilUe;
    }

    public Long getIdFilUe() {
        return idFilUe;
    }

    public void setLibelleEc(String libelleEc) {
        this.libelleEc = libelleEc;
    }

    public String getLibelleEc() {
        return libelleEc;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    public Double getCoef() {
        return coef;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Implement this method
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
                 "null instanceof [type]" also returns false */
        if (!(o instanceof Ec)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Ec c = (Ec) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getIdEc().compareTo(this.idEc) == 0;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.idEc.hashCode();
    }

    public void setLisTypeCntrle(Set<TypeControle> lisTypeCntrle) {
        this.lisTypeCntrle = lisTypeCntrle;
    }

    public Set<TypeControle> getLisTypeCntrle() {
        return lisTypeCntrle;
    }

    public String getNoteEcEtudiant(String num){
        //System.out.println("size not : "+notes.size());
        if (this.notesEcEtudiant != null && this.notesEcEtudiant.size() != 0) {
            if (null == notesEcEtudiant.get(num))
                return "ABS";
            return notesEcEtudiant.get(num);
        }
        return "-";
        
        /*if (this.notes != null && this.notes.size() != 0) {
            List<Note> nt = this.notes
                                .stream()
                                .filter(n -> n.getNumero().equals(num))
                                .collect(Collectors.toList());
            if (nt != null && nt.size() != 0) {
                return nt.get(0)
                         .getNote()
                         .toString();
            }
            return "ABS";
        }*/
        
    }

    public void setNotesEcEtudiant(Map<String, String> notesEcEtudiant) {
        this.notesEcEtudiant = notesEcEtudiant;
    }

    public Map<String, String> getNotesEcEtudiant() {
        return notesEcEtudiant;
    }
}
