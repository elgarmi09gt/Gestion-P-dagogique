package view.beans.entities;


public class EcDetailNote {
    private Long idFilEc;
    private Long idFilUe;
    private String libelleEc;
    public EcDetailNote() {
        super();
    }

    public void setIdFilEc(Long idFilEc) {
        this.idFilEc = idFilEc;
    }

    public Long getIdFilEc() {
        return idFilEc;
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
}
