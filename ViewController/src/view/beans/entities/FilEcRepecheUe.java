package view.beans.entities;


public class FilEcRepecheUe {
    private Long id;
    private Double coef;
    private Controle controle;
    //private Number note;
    
    public FilEcRepecheUe() {
        super();
    }

    public FilEcRepecheUe(Long id, Double coef, Controle controle) {
        this.id = id;
        this.coef = coef;
        this.controle = controle;
        //this.note = note;
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

    public void setControle(Controle controle) {
        this.controle = controle;
    }

    public Controle getControle() {
        return controle;
    }

    /*  public void setNote(Number note) {
        this.note = note;
    }

    public Number getNote() {
        return note;
    } */
}
