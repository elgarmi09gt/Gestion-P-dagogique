package view.beans.entities;


public class Reclamation {
    private Long pmaId;
    private Long calId;
    private Long filUe;
    private Long filEc;
    private Long tpCntrl;
    private Number ancNote;
    private Number newcNote;
    public Reclamation() {
        super();
    }

    public void setPmaId(Long pmaId) {
        this.pmaId = pmaId;
    }

    public Long getPmaId() {
        return pmaId;
    }

    public void setFilUe(Long filUe) {
        this.filUe = filUe;
    }

    public Long getFilUe() {
        return filUe;
    }

    public void setFilEc(Long filEc) {
        this.filEc = filEc;
    }

    public Long getFilEc() {
        return filEc;
    }

    public void setTpCntrl(Long tpCntrl) {
        this.tpCntrl = tpCntrl;
    }

    public Long getTpCntrl() {
        return tpCntrl;
    }

    public void setAncNote(Number ancNote) {
        this.ancNote = ancNote;
    }

    public Number getAncNote() {
        return ancNote;
    }

    public void setNewcNote(Number newcNote) {
        this.newcNote = newcNote;
    }

    public Number getNewcNote() {
        return newcNote;
    }

    public void setCalId(Long calId) {
        this.calId = calId;
    }

    public Long getCalId() {
        return calId;
    }
    
    @Override
    public int hashCode() {
        // TODO Implement this method
        return this.getFilEc().hashCode()+getTpCntrl().hashCode();
    }
}