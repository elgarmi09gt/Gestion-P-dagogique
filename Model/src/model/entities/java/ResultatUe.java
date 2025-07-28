package model.entities.java;


public class ResultatUe {
    private Long idResultatUe;
    private Long idInscUe;
    private Long idFiliereUe;
    private String numero;
    private int credit;
    private String resultat;
    private String note;
    public ResultatUe() {
        super();
    }

    public ResultatUe(Long idResultatUe, Long idInscUe, Long idFiliereUe, String numero, int credit, String resultat, String note) {
        this.idResultatUe = idResultatUe;
        this.idInscUe = idInscUe;
        this.idFiliereUe = idFiliereUe;
        this.numero = numero;
        this.credit = credit;
        this.resultat = resultat;
        this.note = note;
    }

    public void setIdFiliereUe(Long idFiliereUe) {
        this.idFiliereUe = idFiliereUe;
    }

    public Long getIdFiliereUe() {
        return idFiliereUe;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setIdResultatUe(Long idResultatUe) {
        this.idResultatUe = idResultatUe;
    }

    public Long getIdResultatUe() {
        return idResultatUe;
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

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Implement this method
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
                 "null instanceof [type]" also returns false */
        if (!(o instanceof ResultatUe)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ResultatUe c = (ResultatUe) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getIdResultatUe().compareTo(this.idResultatUe) == 0;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.idResultatUe.hashCode();
    }

    public void setIdInscUe(Long idInscUe) {
        this.idInscUe = idInscUe;
    }

    public Long getIdInscUe() {
        return idInscUe;
    }
}
