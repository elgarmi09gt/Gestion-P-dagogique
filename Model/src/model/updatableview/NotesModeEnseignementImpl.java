package model.updatableview;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 27 08:44:22 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class NotesModeEnseignementImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public NotesModeEnseignementImpl() {
    }

    /**
     * Returns the variable value for id_cal_delib_var.
     * @return variable value for id_cal_delib_var
     */
    public Long getid_cal_delib_var() {
        return (Long) ensureVariableManager().getVariableValue("id_cal_delib_var");
    }

    /**
     * Sets <code>value</code> for variable id_cal_delib_var.
     * @param value value to bind as id_cal_delib_var
     */
    public void setid_cal_delib_var(Long value) {
        ensureVariableManager().setVariableValue("id_cal_delib_var", value);
    }

    /**
     * Returns the variable value for id_type_ctrl_var.
     * @return variable value for id_type_ctrl_var
     */
    public Long getid_type_ctrl_var() {
        return (Long) ensureVariableManager().getVariableValue("id_type_ctrl_var");
    }

    /**
     * Sets <code>value</code> for variable id_type_ctrl_var.
     * @param value value to bind as id_type_ctrl_var
     */
    public void setid_type_ctrl_var(Long value) {
        ensureVariableManager().setVariableValue("id_type_ctrl_var", value);
    }

    /**
     * Returns the variable value for id_fil_ec.
     * @return variable value for id_fil_ec
     */
    public Long getid_fil_ec() {
        return (Long) ensureVariableManager().getVariableValue("id_fil_ec");
    }

    /**
     * Sets <code>value</code> for variable id_fil_ec.
     * @param value value to bind as id_fil_ec
     */
    public void setid_fil_ec(Long value) {
        ensureVariableManager().setVariableValue("id_fil_ec", value);
    }
}

