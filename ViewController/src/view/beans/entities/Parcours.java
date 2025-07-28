package view.beans.entities;

public class Parcours {
    private Long code;
    private String libelle;
    public Parcours() {
        super();
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
