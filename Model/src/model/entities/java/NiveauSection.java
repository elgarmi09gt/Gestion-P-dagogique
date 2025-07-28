package model.entities.java;

public class NiveauSection {
    private String code_niv_sec;
    private String code_fr;
    private String crt_code;
    private String actif;
    private String NbreEtudiant;
    private String spec;
    private String opt;
    private String grade;
    private String niveau;
    private String email;
    private String srt;
    private String matricule;
    public NiveauSection() {
        super();
    }
    
    public NiveauSection(String code_niv_sec,String crt_code,String actif,String NbreEtudiant,String spec,String opt,String grade,String niveau, String code_fr) {
        this.code_niv_sec = code_niv_sec;
        this.actif=actif;
        this.crt_code=crt_code;
        this.NbreEtudiant=NbreEtudiant;
        this.spec=spec;
        this.opt=opt;
        this.grade=grade;
        this.niveau=niveau;
        this.code_fr=code_fr;
    }
    public void setCode_niv_sec(String code_niv_sec) {
        this.code_niv_sec = code_niv_sec;
    }

    public String getCode_niv_sec() {
        return code_niv_sec;
    }

    public void setCrt_code(String crt_code) {
        this.crt_code = crt_code;
    }

    public String getCrt_code() {
        return crt_code;
    }

    public void setActif(String actif) {
        this.actif = actif;
    }

    public String getActif() {
        return actif;
    }

    public void setNbreEtudiant(String NbreEtudiant) {
        this.NbreEtudiant = NbreEtudiant;
    }

    public String getNbreEtudiant() {
        return NbreEtudiant;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSpec() {
        return spec;
    }

    public void setCode_fr(String code_fr) {
        this.code_fr = code_fr;
    }

    public String getCode_fr() {
        return code_fr;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getOpt() {
        return opt;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSrt(String srt) {
        this.srt = srt;
    }

    public String getSrt() {
        return srt;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMatricule() {
        return matricule;
    }
}
