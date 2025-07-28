package view.beans.entities;


public class EtudiantDelibUE {
    
    private String Numero;
    private String Prenom;
    private String Nom;
    private String DateNaissance;
    private String LieuNaissance;
    private Long inspedSemUe;
    
    public EtudiantDelibUE() {
        super();
    }

    public EtudiantDelibUE(String Numero, String Prenom, String Nom, String DateNaissance, String LieuNaissance,
                           Long inspedSemUe) {
        this.Numero = Numero;
        this.Prenom = Prenom;
        this.Nom = Nom;
        this.DateNaissance = DateNaissance;
        this.LieuNaissance = LieuNaissance;
        this.inspedSemUe = inspedSemUe;
    }

    public EtudiantDelibUE(String Numero, String Prenom, String Nom, String DateNaissance, String LieuNaissance) {
        this.Numero = Numero;
        this.Prenom = Prenom;
        this.Nom = Nom;
        this.DateNaissance = DateNaissance;
        this.LieuNaissance = LieuNaissance;
    }
    
    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public String getNumero() {
        return Numero;
    }

    public void setInspedSemUe(Long inspedSemUe) {
        this.inspedSemUe = inspedSemUe;
    }

    public Long getInspedSemUe() {
        return inspedSemUe;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getNom() {
        return Nom;
    }

    public void setDateNaissance(String DateNaissance) {
        this.DateNaissance = DateNaissance;
    }

    public String getDateNaissance() {
        return DateNaissance;
    }

    public void setLieuNaissance(String LieuNaissance) {
        this.LieuNaissance = LieuNaissance;
    }

    public String getLieuNaissance() {
        return LieuNaissance;
    }
}
