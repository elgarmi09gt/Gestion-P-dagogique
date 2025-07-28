package model.entities.java;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReclamationNote {
        private int id;
        private String numero_carte;
        private String nom;
        private String prenom;
        @JsonIgnore
        private String telephone;
        private String email_ucad;
        @JsonIgnore
        private String etablissement;
        private String annee;
        private int code_annee;
        private String nivSection;
        private int code_niv_sec;
        private String lib_option;
        private String code_option;
        private String semestre;
        private int code_semestre;
        private String session;
        private int code_session;
        private String libUe;
        private int code_ue;
        private String codification_ue;
        @JsonIgnore
        private String noteUe;
        private String libEc;
        private int code_ec;
        private String codification_ec;
        @JsonIgnore
        private String noteec;
        private String objet;
        @JsonIgnore
        private String motif_rejet;
        @JsonIgnore
        private String date_reclamation;
        @JsonIgnore
        private String date_traitement;
        private int etat;
    public ReclamationNote() {
        super();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNumero_carte(String numero_carte) {
        this.numero_carte = numero_carte;
    }

    public String getNumero_carte() {
        return numero_carte;
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

    public void setEmail_ucad(String email_ucad) {
        this.email_ucad = email_ucad;
    }

    public String getEmail_ucad() {
        return email_ucad;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getAnnee() {
        return annee;
    }

    public void setNivSection(String nivSection) {
        this.nivSection = nivSection;
    }

    public String getNivSection() {
        return nivSection;
    }

    public void setCode_niv_sec(int code_niv_sec) {
        this.code_niv_sec = code_niv_sec;
    }

    public int getCode_niv_sec() {
        return code_niv_sec;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setCode_semestre(int code_semestre) {
        this.code_semestre = code_semestre;
    }

    public int getCode_semestre() {
        return code_semestre;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public void setLibUe(String libUe) {
        this.libUe = libUe;
    }

    public String getLibUe() {
        return libUe;
    }

    public void setNoteUe(String noteUe) {
        this.noteUe = noteUe;
    }

    public String getNoteUe() {
        return noteUe;
    }

    public void setLibEc(String libEc) {
        this.libEc = libEc;
    }

    public String getLibEc() {
        return libEc;
    }

    public void setNoteec(String noteec) {
        this.noteec = noteec;
    }

    public String getNoteec() {
        return noteec;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getObjet() {
        return objet;
    }

    public void setMotif_rejet(String motif_rejet) {
        this.motif_rejet = motif_rejet;
    }

    public String getMotif_rejet() {
        return motif_rejet;
    }

    public void setDate_reclamation(String date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_traitement(String date_traitement) {
        this.date_traitement = date_traitement;
    }

    public String getDate_traitement() {
        return date_traitement;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getEtat() {
        return etat;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setCode_annee(int code_annee) {
        this.code_annee = code_annee;
    }

    public int getCode_annee() {
        return code_annee;
    }

    public void setLib_option(String lib_option) {
        this.lib_option = lib_option;
    }

    public String getLib_option() {
        return lib_option;
    }

    public void setCode_option(String code_option) {
        this.code_option = code_option;
    }

    public String getCode_option() {
        return code_option;
    }

    public void setCode_session(int code_session) {
        this.code_session = code_session;
    }

    public int getCode_session() {
        return code_session;
    }

    public void setCode_ue(int code_ue) {
        this.code_ue = code_ue;
    }

    public int getCode_ue() {
        return code_ue;
    }

    public void setCodification_ue(String codification_ue) {
        this.codification_ue = codification_ue;
    }

    public String getCodification_ue() {
        return codification_ue;
    }

    public void setCode_ec(int code_ec) {
        this.code_ec = code_ec;
    }

    public int getCode_ec() {
        return code_ec;
    }

    public void setCodification_ec(String codification_ec) {
        this.codification_ec = codification_ec;
    }

    public String getCodification_ec() {
        return codification_ec;
    }
}
