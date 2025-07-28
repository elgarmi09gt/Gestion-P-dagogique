package view.beans.entities;


public class NoteModeEnsInterDetailNote {
    Long idNoteInter;
    Long idModeCntrlEc;
    Long idInsPedUe;
    private String note;
    public NoteModeEnsInterDetailNote() {
        super();
    }

    public void setIdNoteInter(Long idNoteInter) {
        this.idNoteInter = idNoteInter;
    }

    public Long getIdNoteInter() {
        return idNoteInter;
    }

    public void setIdModeCntrlEc(Long idModeCntrlEc) {
        this.idModeCntrlEc = idModeCntrlEc;
    }

    public Long getIdModeCntrlEc() {
        return idModeCntrlEc;
    }

    public void setIdInsPedUe(Long idInsPedUe) {
        this.idInsPedUe = idInsPedUe;
    }

    public Long getIdInsPedUe() {
        return idInsPedUe;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
