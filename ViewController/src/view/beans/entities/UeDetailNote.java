package view.beans.entities;


public class UeDetailNote {
    private Long idFiliereUe;
    private Long idPma;
    private String libelleUe;
    private String codification;
    private Long idInspedue;
    //private Set<Ec> listEc;
    public UeDetailNote() {
        super();
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

    public void setCodification(String codification) {
        this.codification = codification;
    }

    public String getCodification() {
        return codification;
    }

    public void setIdFiliereUe(Long idFiliereUe) {
        this.idFiliereUe = idFiliereUe;
    }

    public Long getIdFiliereUe() {
        return idFiliereUe;
    }

    public void setIdInspedue(Long idInspedue) {
        this.idInspedue = idInspedue;
    }

    public Long getIdInspedue() {
        return idInspedue;
    }
}
