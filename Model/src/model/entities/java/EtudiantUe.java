package model.entities.java;


public class EtudiantUe {
    private String numero;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String lieuNaissance;
    private String note;
    private int credit;
    private String resultat;
    public EtudiantUe() {
        super();
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getResultat() {
        return resultat;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Implement this method
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
                 "null instanceof [type]" also returns false */
        if (!(o instanceof EtudiantUe)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        EtudiantUe c = (EtudiantUe) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getNumero().compareTo(this.numero) == 0;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.numero.hashCode();
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }
}
