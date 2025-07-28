package model.entities.java;


public class ResultatSemestre {
    private Long idResultatSemestre;
    private Long idInscSemestre;
    private Long idSemestre;
    private String numero;
    private int credit;
    private String resultat;
    private String note;
    public ResultatSemestre() {
        super();
    }

    public ResultatSemestre(Long idResultatSemestre, Long idInscSemestre, Long idSemestre, String numero, int credit, String resultat, String note) {
        this.idResultatSemestre = idResultatSemestre;
        this.idInscSemestre = idInscSemestre;
        this.idSemestre =  idSemestre;
        this.numero = numero;
        this.credit = credit;
        this.resultat = resultat;
        this.note = note;
    }

    public void setIdInscSemestre(Long idInscSemestre) {
        this.idInscSemestre = idInscSemestre;
    }

    public Long getIdInscSemestre() {
        return idInscSemestre;
    }

    public void setIdResultatSemestre(Long idResultatSemestre) {
        this.idResultatSemestre = idResultatSemestre;
    }

    public Long getIdResultatSemestre() {
        return idResultatSemestre;
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
        if (!(o instanceof ResultatSemestre)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ResultatSemestre c = (ResultatSemestre) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getIdResultatSemestre().compareTo(this.idResultatSemestre) == 0;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.idResultatSemestre.hashCode();
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }
}
