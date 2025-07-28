package view.beans.entities;

public class EtudiantGroupeSaisie {
    private String Numero;
    private String PrenomNom;
    private boolean selected;
    
    public EtudiantGroupeSaisie() {
        super();
    }

    public EtudiantGroupeSaisie(String Numero, String PrenomNom, boolean selected) {
        this.Numero = Numero;
        this.PrenomNom = PrenomNom;
        this.selected = selected;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public String getNumero() {
        return Numero;
    }

    public void setPrenomNom(String PrenomNom) {
        this.PrenomNom = PrenomNom;
    }

    public String getPrenomNom() {
        return PrenomNom;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
