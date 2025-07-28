package view.beans.entities;


public class ModeCntrleDetailNote {
    Long idModeCntrlEc;
    Long idTypeControle;
    String libelle;
    Long idFiliereEc;
    public ModeCntrleDetailNote() {
        super();
    }

    public void setIdModeCntrlEc(Long idModeCntrlEc) {
        this.idModeCntrlEc = idModeCntrlEc;
    }

    public Long getIdModeCntrlEc() {
        return idModeCntrlEc;
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
}
