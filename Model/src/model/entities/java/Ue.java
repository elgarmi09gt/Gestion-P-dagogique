package model.entities.java;

import java.util.Map;
import java.util.Set;


public class Ue {
    private Long idUe;
    private Long idPma;
    private String libelleUe;
    private String codification;
    private Set<Ec> listEc;
    private Set<ResultatUe> listResultUe;
    private Map<String, ResultatUe> resulatsUe;

    public Ue() {
        super();
    }

    public void setIdUe(Long idUe) {
        this.idUe = idUe;
    }

    public Long getIdUe() {
        return idUe;
    }

    public void setIdPma(Long idPma) {
        this.idPma = idPma;
    }

    public Long getIdPma() {
        return idPma;
    }

    public void setLibelleUe(String libelleUe) {
        this.libelleUe = libelleUe;
    }

    public String getLibelleUe() {
        return libelleUe;
    }

    public void setListEc(Set<Ec> listEc) {
        this.listEc = listEc;
    }

    public Set<Ec> getListEc() {
        return listEc;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Implement this method
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
                 "null instanceof [type]" also returns false */
        if (!(o instanceof Ue)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Ue c = (Ue) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getIdUe().compareTo(this.idUe) == 0 ;
    }


    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.idUe.hashCode();
    }

    public void setListResultUe(Set<ResultatUe> listResultUe) {
        this.listResultUe = listResultUe;
    }

    public Set<ResultatUe> getListResultUe() {
        return listResultUe;
    }

    public void setResulatsUe(Map<String, ResultatUe> resulatsUe) {
        this.resulatsUe = resulatsUe;
    }

    public Map<String, ResultatUe> getResulatsUe() {
        return resulatsUe;
    }

    public String getNoteUeEtudiant(String num) {
        //System.out.println("size not : "+notes.size());
        if (this.resulatsUe != null && this.resulatsUe.size() != 0) {
            if (null == resulatsUe.get(num))
                return "ABS";
            return resulatsUe.get(num).getNote();
        }
        return "-";
    }
    
    public Integer getCreditUeEtudiant(String num) {
        //System.out.println("size not : "+notes.size());
        if (this.resulatsUe != null && this.resulatsUe.size() != 0) {
            if (null == resulatsUe.get(num))
                return 0;
            return resulatsUe.get(num).getCredit();
        }
        return 0;
    }

    public String getResultatUeEtudiant(String num) {
        //System.out.println("size not : "+notes.size());
        if (this.resulatsUe != null && this.resulatsUe.size() != 0) {
            if (null == resulatsUe.get(num))
                return "NV";
            return resulatsUe.get(num).getResultat();
        }
        return "NV";
    }

    public void setCodification(String codification) {
        this.codification = codification;
    }

    public String getCodification() {
        return codification;
    }
}
