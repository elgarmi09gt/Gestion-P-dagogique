package view.beans.entities;


public class FiliereEc {
    private Long id;
    private Double coef;
    private Number note;
    
    public FiliereEc() {
        super();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    public Double getCoef() {
        return coef;
    }

    public void setNote(Number note) {
        this.note = note;
    }

    public Number getNote() {
        return note;
    }
}
