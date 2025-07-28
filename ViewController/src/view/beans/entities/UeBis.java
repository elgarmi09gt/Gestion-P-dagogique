package view.beans.entities;

public class UeBis {
    private Long inspedsemue;
    private String ue;
    
    public UeBis() {
        super();
    }

    public UeBis(Long inspedsemue, String ue) {
        this.inspedsemue = inspedsemue;
        this.ue = ue;
    }

    public void setInspedsemue(Long inspedsemue) {
        this.inspedsemue = inspedsemue;
    }

    public Long getInspedsemue() {
        return inspedsemue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public String getUe() {
        return ue;
    }
}
