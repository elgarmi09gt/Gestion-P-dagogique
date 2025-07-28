package view.beans.entities;


public class Ue {
    private Integer id_ue;
    private Long id_pma;
    private String ue;
    
    public Ue() {
        super();
    }

    public Ue(Integer id_ue, String ue) {
        this.id_ue = id_ue;
        this.ue = ue;
    }

    public Ue(Integer id_ue, Long id_pma, String ue) {
        this.id_ue = id_ue;
        this.id_pma = id_pma;
        this.ue = ue;
    }

    public void setId_ue(Integer id_ue) {
        this.id_ue = id_ue;
    }

    public Integer getId_ue() {
        return id_ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public String getUe() {
        return ue;
    }

    public void setId_pma(Long id_pma) {
        this.id_pma = id_pma;
    }

    public Long getId_pma() {
        return id_pma;
    }
}
