package view.beans.entities;


public class FilEcTypeCntrle {

    private Long fil_ec;
    private Long type_cntrl_id;
    private String type_cntrl;

    public FilEcTypeCntrle(Long fil_ec, Long type_cntrl_id, String type_cntrl) {
        this.fil_ec = fil_ec;
        this.type_cntrl_id = type_cntrl_id;
        this.type_cntrl = type_cntrl;
    }

    public void setFil_ec(Long fil_ec) {
        this.fil_ec = fil_ec;
    }

    public Long getFil_ec() {
        return fil_ec;
    }

    public void setType_cntrl_id(Long type_cntrl_id) {
        this.type_cntrl_id = type_cntrl_id;
    }

    public Long getType_cntrl_id() {
        return type_cntrl_id;
    }

    public void setType_cntrl(String type_cntrl) {
        this.type_cntrl = type_cntrl;
    }

    public String getType_cntrl() {
        return type_cntrl;
    }

    public FilEcTypeCntrle() {
        super();
    }
}
