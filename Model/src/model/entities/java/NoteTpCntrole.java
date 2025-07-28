package model.entities.java;


public class NoteTpCntrole {
    private Long idNote;
    private Long idTypeCntrle;
    private Long idFiliereEc;
    private String numero;
    private String note;
    public NoteTpCntrole() {
        super();
    }

    public NoteTpCntrole(Long idNote, Long idTypeCntrle, String numero, String note) {
        this.idNote = idNote;
        this.idTypeCntrle = idTypeCntrle;
        this.numero = numero;
        this.note = note;
    }

    public void setIdNote(Long idNote) {
        this.idNote = idNote;
    }

    public Long getIdNote() {
        return idNote;
    }

    public void setIdTypeCntrle(Long idTypeCntrle) {
        this.idTypeCntrle = idTypeCntrle;
    }

    public Long getIdTypeCntrle() {
        return idTypeCntrle;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
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
        if (!(o instanceof NoteTpCntrole)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        NoteTpCntrole c = (NoteTpCntrole) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getIdNote().compareTo(this.idNote) == 0;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.idNote.hashCode();
    }

    public void setIdFiliereEc(Long idFiliereEc) {
        this.idFiliereEc = idFiliereEc;
    }

    public Long getIdFiliereEc() {
        return idFiliereEc;
    }
}
