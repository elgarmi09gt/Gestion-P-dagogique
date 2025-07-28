package saisie_notes_mode_ens;

import view.beans.entities.semestre.Note;

public class NoteModeEns {
    private Long IdNoteModeEnseignement ;                   
    private Float Note ;    
    private String Numero  ;
    private String PrenomNom;  
    private String LieuNaissance;  
    private Long IdEtudiant;   
    public NoteModeEns() {
        super();
    }

    public NoteModeEns(Long IdNoteModeEnseignement, Float Note, String Numero, String PrenomNom, String LieuNaissance,Long IdEtudiant) {
        this.IdNoteModeEnseignement = IdNoteModeEnseignement;
        this.Note = Note;
        this.Numero = Numero;
        this.PrenomNom = PrenomNom;
        this.LieuNaissance = LieuNaissance;
        this.IdEtudiant = IdEtudiant;
    }

    public void setIdNoteModeEnseignement(Long IdNoteModeEnseignement) {
        this.IdNoteModeEnseignement = IdNoteModeEnseignement;
    }

    public Long getIdNoteModeEnseignement() {
        return IdNoteModeEnseignement;
    }

    public void setNote(Float Note) {
        this.Note = Note;
    }

    public Float getNote() {
        return Note;
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

    public void setLieuNaissance(String LieuNaissance) {
        this.LieuNaissance = LieuNaissance;
    }

    public String getLieuNaissance() {
        return LieuNaissance;
    }

    public void setIdEtudiant(Long IdEtudiant) {
        this.IdEtudiant = IdEtudiant;
    }

    public Long getIdEtudiant() {
        return IdEtudiant;
    }
}
