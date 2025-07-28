package view.beans.entities;

public class NiveauSection {
    private String code_niv_sec;
    private String crt_code;
    private String actif;
    private String NbreEtudiant;
    private String spec;
    private String opt;
    private String grade;
    private String niveau;
    public NiveauSection() {
        super();
    }
    
    public NiveauSection(String code_niv_sec,String crt_code,String actif,String NbreEtudiant,String spec,String opt,String grade,String niveau) {
        this.code_niv_sec = code_niv_sec;
        this.actif=actif;
        this.crt_code=crt_code;
        this.NbreEtudiant=NbreEtudiant;
        this.spec=spec;
        this.opt=opt;
        this.grade=grade;
        this.niveau=niveau;
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
}
