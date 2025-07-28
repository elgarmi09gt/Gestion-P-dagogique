package view.beans.entities;

public class ModeCntrlEc {
    private Integer mode_cntrl_id;
    private String mode_cntrl;
    
    public ModeCntrlEc() {
        super();
    }

    public ModeCntrlEc(Integer mode_cntrl_id, String mode_cntrl) {
        this.mode_cntrl_id = mode_cntrl_id;
        this.mode_cntrl = mode_cntrl;
    }

    public void setMode_cntrl_id(Integer mode_cntrl_id) {
        this.mode_cntrl_id = mode_cntrl_id;
    }

    public Integer getMode_cntrl_id() {
        return mode_cntrl_id;
    }

    public void setMode_cntrl(String mode_cntrl) {
        this.mode_cntrl = mode_cntrl;
    }

    public String getMode_cntrl() {
        return mode_cntrl;
    }

}
