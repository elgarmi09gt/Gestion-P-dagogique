package model.updatableview;

import java.sql.ResultSet;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 30 09:59:25 GMT 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ResultatVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public ResultatVOImpl() {
    }

    /**
     * Returns the variable value for id_niveau.
     * @return variable value for id_niveau
     */
    public Long getid_niveau() {
        return (Long) ensureVariableManager().getVariableValue("id_niveau");
    }

    /**
     * Sets <code>value</code> for variable id_niveau.
     * @param value value to bind as id_niveau
     */
    public void setid_niveau(Long value) {
        ensureVariableManager().setVariableValue("id_niveau", value);
    }

    /**
     * Returns the variable value for id_option.
     * @return variable value for id_option
     */
    public Long getid_option() {
        return (Long) ensureVariableManager().getVariableValue("id_option");
    }

    /**
     * Sets <code>value</code> for variable id_option.
     * @param value value to bind as id_option
     */
    public void setid_option(Long value) {
        ensureVariableManager().setVariableValue("id_option", value);
    }

    /**
     * Returns the variable value for id_annee.
     * @return variable value for id_annee
     */
    public Long getid_annee() {
        return (Long) ensureVariableManager().getVariableValue("id_annee");
    }

    /**
     * Sets <code>value</code> for variable id_annee.
     * @param value value to bind as id_annee
     */
    public void setid_annee(Long value) {
        ensureVariableManager().setVariableValue("id_annee", value);
    }

    /**
     * executeQueryForCollection - for custom java data source support.
     */
    @Override
    protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
        super.executeQueryForCollection(qc, params, noUserParams);
    }

    /**
     * hasNextForCollection - for custom java data source support.
     */
    @Override
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - for custom java data source support.
     */
    @Override
    protected ViewRowImpl createRowFromResultSet(Object qc, ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - for custom java data source support.
     */
    @Override
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }

    /**
     * getCappedQueryHitCount - for custom java data source support.
     */
    @Override
    public long getCappedQueryHitCount(ViewRowSetImpl viewRowSet, Row[] masterRows, long oldCap, long cap) {
        long value = super.getCappedQueryHitCount(viewRowSet, masterRows, oldCap, cap);
        return value;
    }

    /**
     * Returns the variable value for id_etud.
     * @return variable value for id_etud
     */
    public Long getid_etud() {
        return (Long) ensureVariableManager().getVariableValue("id_etud");
    }

    /**
     * Sets <code>value</code> for variable id_etud.
     * @param value value to bind as id_etud
     */
    public void setid_etud(Long value) {
        ensureVariableManager().setVariableValue("id_etud", value);
    }

    /**
     * Returns the variable value for id_insc_ped.
     * @return variable value for id_insc_ped
     */
    public Long getid_insc_ped() {
        return (Long) ensureVariableManager().getVariableValue("id_insc_ped");
    }

    /**
     * Sets <code>value</code> for variable id_insc_ped.
     * @param value value to bind as id_insc_ped
     */
    public void setid_insc_ped(Long value) {
        ensureVariableManager().setVariableValue("id_insc_ped", value);
    }
}

