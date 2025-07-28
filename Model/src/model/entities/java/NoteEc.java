package model.entities.java;


public class NoteEc {
    private Long idNote;
    private Long idFilEc;
    private String numero;
    private String note;
    public NoteEc() {
        super();
    }

    public NoteEc(Long idNote, Long idFilEc, String numero, String note) {
        this.idNote = idNote;
        this.idFilEc = idFilEc;
        this.numero = numero;
        this.note = note;
    }

    public void setIdNote(Long idNote) {
        this.idNote = idNote;
    }

    public Long getIdNote() {
        return idNote;
    }

    public void setIdFilEc(Long idFilEc) {
        this.idFilEc = idFilEc;
    }

    public Long getIdFilEc() {
        return idFilEc;
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
        if (!(o instanceof NoteEc)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        NoteEc c = (NoteEc) o;

        // Compare the data members and return accordingly
        //System.out.println("xxxxx " + c.getId_ue().compareTo(this.id_ue));
        return c.getIdNote().compareTo(this.idNote) == 0;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.idNote.hashCode();
    }
}
