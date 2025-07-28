package view.beans.entities;

public class EcBis {

    private Long inspedsemue;
    private String ec;
    private Number coef;
    
    public EcBis() {
        super();
    }

    public void setInspedsemue(Long inspedsemue) {
        this.inspedsemue = inspedsemue;
    }

    public Long getInspedsemue() {
        return inspedsemue;
    }

    public EcBis(Long inspedsemue, String ec, Number coef) {
        this.inspedsemue = inspedsemue;
        this.ec = ec;
        this.coef = coef;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getEc() {
        return ec;
    }

    public void setCoef(Integer coef) {
        this.coef = coef;
    }

    public Number getCoef() {
        return coef;
    }
}
