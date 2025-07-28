package model.entities.java;

import java.util.List;

public class ReclamationResponse {
    private List<ReclamationNote> reclamationNotes;
    public ReclamationResponse() {
        super();
    }

    public void setReclamationNotes(List<ReclamationNote> reclamationNotes) {
        this.reclamationNotes = reclamationNotes;
    }

    public List<ReclamationNote> getReclamationNotes() {
        return reclamationNotes;
    }
}
