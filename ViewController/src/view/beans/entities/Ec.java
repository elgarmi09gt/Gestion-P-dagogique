package view.beans.entities;


public class Ec {
    private Integer id_ec;
    private Integer id_pm;
    private Integer id_fil_ue;
    private String ec;
    private Double coef;

    public Ec(Integer id_ec, Integer id_pm, Integer id_fil_ue, String ec, Double coef) {
        this.id_ec = id_ec;
        this.id_pm = id_pm;
        this.id_fil_ue = id_fil_ue;
        this.ec = ec;
        this.coef = coef;
    }

    public void setId_pm(Integer id_pm) {
        this.id_pm = id_pm;
    }

    public Integer getId_pm() {
        return id_pm;
    }

    public void setId_fil_ue(Integer id_fil_ue) {
        this.id_fil_ue = id_fil_ue;
    }

    public Integer getId_fil_ue() {
        return id_fil_ue;
    }
    
    public Ec() {
        super();
    }

    public Ec(Integer id_ec, String ec, Double coef) {
        this.id_ec = id_ec;
        this.ec = ec;
        this.coef = coef;
    }
    
    public void setId_ec(Integer id_ec) {
        this.id_ec = id_ec;
    }

    public Integer getId_ec() {
        return id_ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getEc() {
        return ec;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    public Double getCoef() {
        return coef;
    }
}
