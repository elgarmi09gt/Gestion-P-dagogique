package view.beans.entities;

public class Etudiant {
    
    private String Numero;
    private String PrenomNom;
    private Long inspedSemUe;
    
    public Etudiant() {
        super();
    }

    public Etudiant(String Numero, String PrenomNom) {
        this.Numero = Numero;
        this.PrenomNom = PrenomNom;
    }

    public Etudiant(String Numero, String PrenomNom, Long inspedSemUe) {
        this.Numero = Numero;
        this.PrenomNom = PrenomNom;
        this.inspedSemUe = inspedSemUe;
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

    public void setInspedSemUe(Long inspedSemUe) {
        this.inspedSemUe = inspedSemUe;
    }

    public Long getInspedSemUe() {
        return inspedSemUe;
    }
}
