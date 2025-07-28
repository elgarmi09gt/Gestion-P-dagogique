package view.beans.entities;


public class Renonciation {
    private Long etdId; 
    private Long prcrsMaq; 
    private Long filEc; 
    private String RenonceDate;

    public void setEtdId(Long etdId) {
        this.etdId = etdId;
    }

    public Long getEtdId() {
        return etdId;
    }

    public void setPrcrsMaq(Long prcrsMaq) {
        this.prcrsMaq = prcrsMaq;
    }

    public Long getPrcrsMaq() {
        return prcrsMaq;
    }

    public void setFilEc(Long filEc) {
        this.filEc = filEc;
    }

    public Long getFilEc() {
        return filEc;
    }

    public void setRenonceDate(String RenonceDate) {
        this.RenonceDate = RenonceDate;
    }

    public String getRenonceDate() {
        return RenonceDate;
    }

    public Renonciation() {
        super();
    }
}
