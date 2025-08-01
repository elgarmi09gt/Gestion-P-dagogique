package model.updatableview;

import java.sql.Timestamp;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon May 31 16:18:40 UTC 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DroitNiveauPaysVORowImpl extends ViewRowImpl {
    public static final int ENTITY_DROITNIVEAUPAYSEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdDnp {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getIdDnp();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setIdDnp((DBSequence) value);
            }
        }
        ,
        IdNiveau {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getIdNiveau();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setIdNiveau((Long) value);
            }
        }
        ,
        IdPays {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getIdPays();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setIdPays((Long) value);
            }
        }
        ,
        DroitInsAdm {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getDroitInsAdm();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setDroitInsAdm((Integer) value);
            }
        }
        ,
        DroitInsPed {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getDroitInsPed();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setDroitInsPed((Integer) value);
            }
        }
        ,
        CoutFormation {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getCoutFormation();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setCoutFormation((Integer) value);
            }
        }
        ,
        DateModification {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getDateModification();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setDateModification((Timestamp) value);
            }
        }
        ,
        DateCreation {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getDateCreation();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setDateCreation((Timestamp) value);
            }
        }
        ,
        UtiCree {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getUtiCree();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setUtiCree((Long) value);
            }
        }
        ,
        UtiModifie {
            protected Object get(DroitNiveauPaysVORowImpl obj) {
                return obj.getUtiModifie();
            }

            protected void put(DroitNiveauPaysVORowImpl obj, Object value) {
                obj.setUtiModifie((Long) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(DroitNiveauPaysVORowImpl object);

        protected abstract void put(DroitNiveauPaysVORowImpl object, Object value);

        protected int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        protected static final int firstIndex() {
            return firstIndex;
        }

        protected static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        protected static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int IDDNP = AttributesEnum.IdDnp.index();
    public static final int IDNIVEAU = AttributesEnum.IdNiveau.index();
    public static final int IDPAYS = AttributesEnum.IdPays.index();
    public static final int DROITINSADM = AttributesEnum.DroitInsAdm.index();
    public static final int DROITINSPED = AttributesEnum.DroitInsPed.index();
    public static final int COUTFORMATION = AttributesEnum.CoutFormation.index();
    public static final int DATEMODIFICATION = AttributesEnum.DateModification.index();
    public static final int DATECREATION = AttributesEnum.DateCreation.index();
    public static final int UTICREE = AttributesEnum.UtiCree.index();
    public static final int UTIMODIFIE = AttributesEnum.UtiModifie.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DroitNiveauPaysVORowImpl() {
    }

    /**
     * Gets DroitNiveauPaysEO entity object.
     * @return the DroitNiveauPaysEO
     */
    public EntityImpl getDroitNiveauPaysEO() {
        return (EntityImpl) getEntity(ENTITY_DROITNIVEAUPAYSEO);
    }

    /**
     * Gets the attribute value for ID_DNP using the alias name IdDnp.
     * @return the ID_DNP
     */
    public DBSequence getIdDnp() {
        return (DBSequence) getAttributeInternal(IDDNP);
    }

    /**
     * Sets <code>value</code> as attribute value for ID_DNP using the alias name IdDnp.
     * @param value value to set the ID_DNP
     */
    public void setIdDnp(DBSequence value) {
        setAttributeInternal(IDDNP, value);
    }

    /**
     * Gets the attribute value for ID_NIVEAU using the alias name IdNiveau.
     * @return the ID_NIVEAU
     */
    public Long getIdNiveau() {
        return (Long) getAttributeInternal(IDNIVEAU);
    }

    /**
     * Sets <code>value</code> as attribute value for ID_NIVEAU using the alias name IdNiveau.
     * @param value value to set the ID_NIVEAU
     */
    public void setIdNiveau(Long value) {
        setAttributeInternal(IDNIVEAU, value);
    }

    /**
     * Gets the attribute value for ID_PAYS using the alias name IdPays.
     * @return the ID_PAYS
     */
    public Long getIdPays() {
        return (Long) getAttributeInternal(IDPAYS);
    }

    /**
     * Sets <code>value</code> as attribute value for ID_PAYS using the alias name IdPays.
     * @param value value to set the ID_PAYS
     */
    public void setIdPays(Long value) {
        setAttributeInternal(IDPAYS, value);
    }

    /**
     * Gets the attribute value for DROIT_INS_ADM using the alias name DroitInsAdm.
     * @return the DROIT_INS_ADM
     */
    public Integer getDroitInsAdm() {
        return (Integer) getAttributeInternal(DROITINSADM);
    }

    /**
     * Sets <code>value</code> as attribute value for DROIT_INS_ADM using the alias name DroitInsAdm.
     * @param value value to set the DROIT_INS_ADM
     */
    public void setDroitInsAdm(Integer value) {
        setAttributeInternal(DROITINSADM, value);
    }

    /**
     * Gets the attribute value for DROIT_INS_PED using the alias name DroitInsPed.
     * @return the DROIT_INS_PED
     */
    public Integer getDroitInsPed() {
        return (Integer) getAttributeInternal(DROITINSPED);
    }

    /**
     * Sets <code>value</code> as attribute value for DROIT_INS_PED using the alias name DroitInsPed.
     * @param value value to set the DROIT_INS_PED
     */
    public void setDroitInsPed(Integer value) {
        setAttributeInternal(DROITINSPED, value);
    }

    /**
     * Gets the attribute value for COUT_FORMATION using the alias name CoutFormation.
     * @return the COUT_FORMATION
     */
    public Integer getCoutFormation() {
        return (Integer) getAttributeInternal(COUTFORMATION);
    }

    /**
     * Sets <code>value</code> as attribute value for COUT_FORMATION using the alias name CoutFormation.
     * @param value value to set the COUT_FORMATION
     */
    public void setCoutFormation(Integer value) {
        setAttributeInternal(COUTFORMATION, value);
    }

    /**
     * Gets the attribute value for DATE_MODIFICATION using the alias name DateModification.
     * @return the DATE_MODIFICATION
     */
    public Timestamp getDateModification() {
        return (Timestamp) getAttributeInternal(DATEMODIFICATION);
    }

    /**
     * Sets <code>value</code> as attribute value for DATE_MODIFICATION using the alias name DateModification.
     * @param value value to set the DATE_MODIFICATION
     */
    public void setDateModification(Timestamp value) {
        setAttributeInternal(DATEMODIFICATION, value);
    }

    /**
     * Gets the attribute value for DATE_CREATION using the alias name DateCreation.
     * @return the DATE_CREATION
     */
    public Timestamp getDateCreation() {
        return (Timestamp) getAttributeInternal(DATECREATION);
    }

    /**
     * Sets <code>value</code> as attribute value for DATE_CREATION using the alias name DateCreation.
     * @param value value to set the DATE_CREATION
     */
    public void setDateCreation(Timestamp value) {
        setAttributeInternal(DATECREATION, value);
    }

    /**
     * Gets the attribute value for UTI_CREE using the alias name UtiCree.
     * @return the UTI_CREE
     */
    public Long getUtiCree() {
        return (Long) getAttributeInternal(UTICREE);
    }

    /**
     * Sets <code>value</code> as attribute value for UTI_CREE using the alias name UtiCree.
     * @param value value to set the UTI_CREE
     */
    public void setUtiCree(Long value) {
        setAttributeInternal(UTICREE, value);
    }

    /**
     * Gets the attribute value for UTI_MODIFIE using the alias name UtiModifie.
     * @return the UTI_MODIFIE
     */
    public Long getUtiModifie() {
        return (Long) getAttributeInternal(UTIMODIFIE);
    }

    /**
     * Sets <code>value</code> as attribute value for UTI_MODIFIE using the alias name UtiModifie.
     * @param value value to set the UTI_MODIFIE
     */
    public void setUtiModifie(Long value) {
        setAttributeInternal(UTIMODIFIE, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}

