package model.entities;

import java.sql.Timestamp;

import model.updatableview.FormationsVORowImpl;

import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Sep 17 15:28:12 GMT 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class NiveauxFormationsEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdNiveauFormation {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getIdNiveauFormation();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setIdNiveauFormation((DBSequence) value);
            }
        }
        ,
        IdNiveau {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getIdNiveau();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setIdNiveau((DBSequence) value);
            }
        }
        ,
        IdFormation {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getIdFormation();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setIdFormation((DBSequence) value);
            }
        }
        ,
        LibelleLong {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getLibelleLong();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setLibelleLong((String) value);
            }
        }
        ,
        LibelleCourt {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getLibelleCourt();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setLibelleCourt((String) value);
            }
        }
        ,
        ASelection {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getASelection();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setASelection((Integer) value);
            }
        }
        ,
        AutorisationPermise {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getAutorisationPermise();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setAutorisationPermise((Integer) value);
            }
        }
        ,
        UtiliseEvaluation {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getUtiliseEvaluation();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setUtiliseEvaluation((Integer) value);
            }
        }
        ,
        Presentielle {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getPresentielle();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setPresentielle((Integer) value);
            }
        }
        ,
        NbreInsPermise {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getNbreInsPermise();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setNbreInsPermise((Integer) value);
            }
        }
        ,
        Diplomante {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getDiplomante();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setDiplomante((Integer) value);
            }
        }
        ,
        Ouvert {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getOuvert();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setOuvert((Integer) value);
            }
        }
        ,
        IdModePaiement {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getIdModePaiement();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setIdModePaiement((Integer) value);
            }
        }
        ,
        Mensualite {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getMensualite();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setMensualite((Integer) value);
            }
        }
        ,
        CoutFormation {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getCoutFormation();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setCoutFormation((Long) value);
            }
        }
        ,
        DroitInsPed {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getDroitInsPed();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setDroitInsPed((Integer) value);
            }
        }
        ,
        DroitInsAdm {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getDroitInsAdm();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setDroitInsAdm((Integer) value);
            }
        }
        ,
        CoutFormationEtr {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getCoutFormationEtr();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setCoutFormationEtr((Long) value);
            }
        }
        ,
        DroitInsAdmEtr {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getDroitInsAdmEtr();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setDroitInsAdmEtr((Integer) value);
            }
        }
        ,
        DroitInsPedEtr {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getDroitInsPedEtr();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setDroitInsPedEtr((Integer) value);
            }
        }
        ,
        TagSemestrialisation {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getTagSemestrialisation();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setTagSemestrialisation((Integer) value);
            }
        }
        ,
        TagGroupeMatiere {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getTagGroupeMatiere();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setTagGroupeMatiere((Integer) value);
            }
        }
        ,
        DateCreation {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getDateCreation();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setDateCreation((Timestamp) value);
            }
        }
        ,
        DateModification {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getDateModification();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setDateModification((Timestamp) value);
            }
        }
        ,
        UtiCree {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getUtiCree();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setUtiCree((Long) value);
            }
        }
        ,
        UtiModifie {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getUtiModifie();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setUtiModifie((Long) value);
            }
        }
        ,
        ModeDispatching {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getModeDispatching();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setModeDispatching((Integer) value);
            }
        }
        ,
        FormationsEO {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getFormationsEO();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setFormationsEO((EntityImpl) value);
            }
        }
        ,
        NiveauFormationCohortesEO {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getNiveauFormationCohortesEO();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FormationsVO {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getFormationsVO();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NiveauFormationCohortesVO {
            protected Object get(NiveauxFormationsEOImpl obj) {
                return obj.getNiveauFormationCohortesVO();
            }

            protected void put(NiveauxFormationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(NiveauxFormationsEOImpl object);

        protected abstract void put(NiveauxFormationsEOImpl object, Object value);

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


    public static final int IDNIVEAUFORMATION = AttributesEnum.IdNiveauFormation.index();
    public static final int IDNIVEAU = AttributesEnum.IdNiveau.index();
    public static final int IDFORMATION = AttributesEnum.IdFormation.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();
    public static final int LIBELLECOURT = AttributesEnum.LibelleCourt.index();
    public static final int ASELECTION = AttributesEnum.ASelection.index();
    public static final int AUTORISATIONPERMISE = AttributesEnum.AutorisationPermise.index();
    public static final int UTILISEEVALUATION = AttributesEnum.UtiliseEvaluation.index();
    public static final int PRESENTIELLE = AttributesEnum.Presentielle.index();
    public static final int NBREINSPERMISE = AttributesEnum.NbreInsPermise.index();
    public static final int DIPLOMANTE = AttributesEnum.Diplomante.index();
    public static final int OUVERT = AttributesEnum.Ouvert.index();
    public static final int IDMODEPAIEMENT = AttributesEnum.IdModePaiement.index();
    public static final int MENSUALITE = AttributesEnum.Mensualite.index();
    public static final int COUTFORMATION = AttributesEnum.CoutFormation.index();
    public static final int DROITINSPED = AttributesEnum.DroitInsPed.index();
    public static final int DROITINSADM = AttributesEnum.DroitInsAdm.index();
    public static final int COUTFORMATIONETR = AttributesEnum.CoutFormationEtr.index();
    public static final int DROITINSADMETR = AttributesEnum.DroitInsAdmEtr.index();
    public static final int DROITINSPEDETR = AttributesEnum.DroitInsPedEtr.index();
    public static final int TAGSEMESTRIALISATION = AttributesEnum.TagSemestrialisation.index();
    public static final int TAGGROUPEMATIERE = AttributesEnum.TagGroupeMatiere.index();
    public static final int DATECREATION = AttributesEnum.DateCreation.index();
    public static final int DATEMODIFICATION = AttributesEnum.DateModification.index();
    public static final int UTICREE = AttributesEnum.UtiCree.index();
    public static final int UTIMODIFIE = AttributesEnum.UtiModifie.index();
    public static final int MODEDISPATCHING = AttributesEnum.ModeDispatching.index();
    public static final int FORMATIONSEO = AttributesEnum.FormationsEO.index();
    public static final int NIVEAUFORMATIONCOHORTESEO = AttributesEnum.NiveauFormationCohortesEO.index();
    public static final int FORMATIONSVO = AttributesEnum.FormationsVO.index();
    public static final int NIVEAUFORMATIONCOHORTESVO = AttributesEnum.NiveauFormationCohortesVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public NiveauxFormationsEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("model.entities.NiveauxFormationsEO");
    }


    /**
     * Gets the attribute value for IdNiveauFormation, using the alias name IdNiveauFormation.
     * @return the value of IdNiveauFormation
     */
    public DBSequence getIdNiveauFormation() {
        return (DBSequence) getAttributeInternal(IDNIVEAUFORMATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for IdNiveauFormation.
     * @param value value to set the IdNiveauFormation
     */
    public void setIdNiveauFormation(DBSequence value) {
        setAttributeInternal(IDNIVEAUFORMATION, value);
    }

    /**
     * Gets the attribute value for IdNiveau, using the alias name IdNiveau.
     * @return the value of IdNiveau
     */
    public DBSequence getIdNiveau() {
        return (DBSequence) getAttributeInternal(IDNIVEAU);
    }

    /**
     * Sets <code>value</code> as the attribute value for IdNiveau.
     * @param value value to set the IdNiveau
     */
    public void setIdNiveau(DBSequence value) {
        setAttributeInternal(IDNIVEAU, value);
    }

    /**
     * Gets the attribute value for IdFormation, using the alias name IdFormation.
     * @return the value of IdFormation
     */
    public DBSequence getIdFormation() {
        return (DBSequence) getAttributeInternal(IDFORMATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for IdFormation.
     * @param value value to set the IdFormation
     */
    public void setIdFormation(DBSequence value) {
        setAttributeInternal(IDFORMATION, value);
    }

    /**
     * Gets the attribute value for LibelleLong, using the alias name LibelleLong.
     * @return the value of LibelleLong
     */
    public String getLibelleLong() {
        return (String) getAttributeInternal(LIBELLELONG);
    }

    /**
     * Sets <code>value</code> as the attribute value for LibelleLong.
     * @param value value to set the LibelleLong
     */
    public void setLibelleLong(String value) {
        setAttributeInternal(LIBELLELONG, value);
    }

    /**
     * Gets the attribute value for LibelleCourt, using the alias name LibelleCourt.
     * @return the value of LibelleCourt
     */
    public String getLibelleCourt() {
        return (String) getAttributeInternal(LIBELLECOURT);
    }

    /**
     * Sets <code>value</code> as the attribute value for LibelleCourt.
     * @param value value to set the LibelleCourt
     */
    public void setLibelleCourt(String value) {
        setAttributeInternal(LIBELLECOURT, value);
    }

    /**
     * Gets the attribute value for ASelection, using the alias name ASelection.
     * @return the value of ASelection
     */
    public Integer getASelection() {
        return (Integer) getAttributeInternal(ASELECTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for ASelection.
     * @param value value to set the ASelection
     */
    public void setASelection(Integer value) {
        setAttributeInternal(ASELECTION, value);
    }

    /**
     * Gets the attribute value for AutorisationPermise, using the alias name AutorisationPermise.
     * @return the value of AutorisationPermise
     */
    public Integer getAutorisationPermise() {
        return (Integer) getAttributeInternal(AUTORISATIONPERMISE);
    }

    /**
     * Sets <code>value</code> as the attribute value for AutorisationPermise.
     * @param value value to set the AutorisationPermise
     */
    public void setAutorisationPermise(Integer value) {
        setAttributeInternal(AUTORISATIONPERMISE, value);
    }

    /**
     * Gets the attribute value for UtiliseEvaluation, using the alias name UtiliseEvaluation.
     * @return the value of UtiliseEvaluation
     */
    public Integer getUtiliseEvaluation() {
        return (Integer) getAttributeInternal(UTILISEEVALUATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for UtiliseEvaluation.
     * @param value value to set the UtiliseEvaluation
     */
    public void setUtiliseEvaluation(Integer value) {
        setAttributeInternal(UTILISEEVALUATION, value);
    }

    /**
     * Gets the attribute value for Presentielle, using the alias name Presentielle.
     * @return the value of Presentielle
     */
    public Integer getPresentielle() {
        return (Integer) getAttributeInternal(PRESENTIELLE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Presentielle.
     * @param value value to set the Presentielle
     */
    public void setPresentielle(Integer value) {
        setAttributeInternal(PRESENTIELLE, value);
    }

    /**
     * Gets the attribute value for NbreInsPermise, using the alias name NbreInsPermise.
     * @return the value of NbreInsPermise
     */
    public Integer getNbreInsPermise() {
        return (Integer) getAttributeInternal(NBREINSPERMISE);
    }

    /**
     * Sets <code>value</code> as the attribute value for NbreInsPermise.
     * @param value value to set the NbreInsPermise
     */
    public void setNbreInsPermise(Integer value) {
        setAttributeInternal(NBREINSPERMISE, value);
    }

    /**
     * Gets the attribute value for Diplomante, using the alias name Diplomante.
     * @return the value of Diplomante
     */
    public Integer getDiplomante() {
        return (Integer) getAttributeInternal(DIPLOMANTE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Diplomante.
     * @param value value to set the Diplomante
     */
    public void setDiplomante(Integer value) {
        setAttributeInternal(DIPLOMANTE, value);
    }

    /**
     * Gets the attribute value for Ouvert, using the alias name Ouvert.
     * @return the value of Ouvert
     */
    public Integer getOuvert() {
        return (Integer) getAttributeInternal(OUVERT);
    }

    /**
     * Sets <code>value</code> as the attribute value for Ouvert.
     * @param value value to set the Ouvert
     */
    public void setOuvert(Integer value) {
        setAttributeInternal(OUVERT, value);
    }

    /**
     * Gets the attribute value for IdModePaiement, using the alias name IdModePaiement.
     * @return the value of IdModePaiement
     */
    public Integer getIdModePaiement() {
        return (Integer) getAttributeInternal(IDMODEPAIEMENT);
    }

    /**
     * Sets <code>value</code> as the attribute value for IdModePaiement.
     * @param value value to set the IdModePaiement
     */
    public void setIdModePaiement(Integer value) {
        setAttributeInternal(IDMODEPAIEMENT, value);
    }

    /**
     * Gets the attribute value for Mensualite, using the alias name Mensualite.
     * @return the value of Mensualite
     */
    public Integer getMensualite() {
        return (Integer) getAttributeInternal(MENSUALITE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Mensualite.
     * @param value value to set the Mensualite
     */
    public void setMensualite(Integer value) {
        setAttributeInternal(MENSUALITE, value);
    }

    /**
     * Gets the attribute value for CoutFormation, using the alias name CoutFormation.
     * @return the value of CoutFormation
     */
    public Long getCoutFormation() {
        return (Long) getAttributeInternal(COUTFORMATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoutFormation.
     * @param value value to set the CoutFormation
     */
    public void setCoutFormation(Long value) {
        setAttributeInternal(COUTFORMATION, value);
    }

    /**
     * Gets the attribute value for DroitInsPed, using the alias name DroitInsPed.
     * @return the value of DroitInsPed
     */
    public Integer getDroitInsPed() {
        return (Integer) getAttributeInternal(DROITINSPED);
    }

    /**
     * Sets <code>value</code> as the attribute value for DroitInsPed.
     * @param value value to set the DroitInsPed
     */
    public void setDroitInsPed(Integer value) {
        setAttributeInternal(DROITINSPED, value);
    }

    /**
     * Gets the attribute value for DroitInsAdm, using the alias name DroitInsAdm.
     * @return the value of DroitInsAdm
     */
    public Integer getDroitInsAdm() {
        return (Integer) getAttributeInternal(DROITINSADM);
    }

    /**
     * Sets <code>value</code> as the attribute value for DroitInsAdm.
     * @param value value to set the DroitInsAdm
     */
    public void setDroitInsAdm(Integer value) {
        setAttributeInternal(DROITINSADM, value);
    }

    /**
     * Gets the attribute value for CoutFormationEtr, using the alias name CoutFormationEtr.
     * @return the value of CoutFormationEtr
     */
    public Long getCoutFormationEtr() {
        return (Long) getAttributeInternal(COUTFORMATIONETR);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoutFormationEtr.
     * @param value value to set the CoutFormationEtr
     */
    public void setCoutFormationEtr(Long value) {
        setAttributeInternal(COUTFORMATIONETR, value);
    }

    /**
     * Gets the attribute value for DroitInsAdmEtr, using the alias name DroitInsAdmEtr.
     * @return the value of DroitInsAdmEtr
     */
    public Integer getDroitInsAdmEtr() {
        return (Integer) getAttributeInternal(DROITINSADMETR);
    }

    /**
     * Sets <code>value</code> as the attribute value for DroitInsAdmEtr.
     * @param value value to set the DroitInsAdmEtr
     */
    public void setDroitInsAdmEtr(Integer value) {
        setAttributeInternal(DROITINSADMETR, value);
    }

    /**
     * Gets the attribute value for DroitInsPedEtr, using the alias name DroitInsPedEtr.
     * @return the value of DroitInsPedEtr
     */
    public Integer getDroitInsPedEtr() {
        return (Integer) getAttributeInternal(DROITINSPEDETR);
    }

    /**
     * Sets <code>value</code> as the attribute value for DroitInsPedEtr.
     * @param value value to set the DroitInsPedEtr
     */
    public void setDroitInsPedEtr(Integer value) {
        setAttributeInternal(DROITINSPEDETR, value);
    }

    /**
     * Gets the attribute value for TagSemestrialisation, using the alias name TagSemestrialisation.
     * @return the value of TagSemestrialisation
     */
    public Integer getTagSemestrialisation() {
        return (Integer) getAttributeInternal(TAGSEMESTRIALISATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for TagSemestrialisation.
     * @param value value to set the TagSemestrialisation
     */
    public void setTagSemestrialisation(Integer value) {
        setAttributeInternal(TAGSEMESTRIALISATION, value);
    }

    /**
     * Gets the attribute value for TagGroupeMatiere, using the alias name TagGroupeMatiere.
     * @return the value of TagGroupeMatiere
     */
    public Integer getTagGroupeMatiere() {
        return (Integer) getAttributeInternal(TAGGROUPEMATIERE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TagGroupeMatiere.
     * @param value value to set the TagGroupeMatiere
     */
    public void setTagGroupeMatiere(Integer value) {
        setAttributeInternal(TAGGROUPEMATIERE, value);
    }

    /**
     * Gets the attribute value for DateCreation, using the alias name DateCreation.
     * @return the value of DateCreation
     */
    public Timestamp getDateCreation() {
        return (Timestamp) getAttributeInternal(DATECREATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for DateCreation.
     * @param value value to set the DateCreation
     */
    public void setDateCreation(Timestamp value) {
        setAttributeInternal(DATECREATION, value);
    }

    /**
     * Gets the attribute value for DateModification, using the alias name DateModification.
     * @return the value of DateModification
     */
    public Timestamp getDateModification() {
        return (Timestamp) getAttributeInternal(DATEMODIFICATION);
    }

    /**
     * Sets <code>value</code> as the attribute value for DateModification.
     * @param value value to set the DateModification
     */
    public void setDateModification(Timestamp value) {
        setAttributeInternal(DATEMODIFICATION, value);
    }

    /**
     * Gets the attribute value for UtiCree, using the alias name UtiCree.
     * @return the value of UtiCree
     */
    public Long getUtiCree() {
        return (Long) getAttributeInternal(UTICREE);
    }

    /**
     * Sets <code>value</code> as the attribute value for UtiCree.
     * @param value value to set the UtiCree
     */
    public void setUtiCree(Long value) {
        setAttributeInternal(UTICREE, value);
    }

    /**
     * Gets the attribute value for UtiModifie, using the alias name UtiModifie.
     * @return the value of UtiModifie
     */
    public Long getUtiModifie() {
        return (Long) getAttributeInternal(UTIMODIFIE);
    }

    /**
     * Sets <code>value</code> as the attribute value for UtiModifie.
     * @param value value to set the UtiModifie
     */
    public void setUtiModifie(Long value) {
        setAttributeInternal(UTIMODIFIE, value);
    }

    /**
     * Gets the attribute value for ModeDispatching, using the alias name ModeDispatching.
     * @return the value of ModeDispatching
     */
    public Integer getModeDispatching() {
        return (Integer) getAttributeInternal(MODEDISPATCHING);
    }

    /**
     * Sets <code>value</code> as the attribute value for ModeDispatching.
     * @param value value to set the ModeDispatching
     */
    public void setModeDispatching(Integer value) {
        setAttributeInternal(MODEDISPATCHING, value);
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

    /**
     * @return the associated entity oracle.jbo.server.EntityImpl.
     */
    public EntityImpl getFormationsEO() {
        return (EntityImpl) getAttributeInternal(FORMATIONSEO);
    }

    /**
     * Sets <code>value</code> as the associated entity oracle.jbo.server.EntityImpl.
     */
    public void setFormationsEO(EntityImpl value) {
        setAttributeInternal(FORMATIONSEO, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getNiveauFormationCohortesEO() {
        return (RowIterator) getAttributeInternal(NIVEAUFORMATIONCOHORTESEO);
    }


    /**
     * Uses the link FormationsToNiveauxFormationsVL to return rows of NiveauxFormationsVO
     */
    public FormationsVORowImpl getFormationsVO() {
        return (FormationsVORowImpl) getAttributeInternal(FORMATIONSVO);
    }

    /**
     * Uses the link NiveauxFormationsToNiveauFormationsCohortesVL to return rows of NiveauFormationCohortesVO
     */
    public RowIterator getNiveauFormationCohortesVO() {
        return (RowIterator) getAttributeInternal(NIVEAUFORMATIONCOHORTESVO);
    }

    /**
     * @param idNiveauFormation key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence idNiveauFormation) {
        return new Key(new Object[] { idNiveauFormation });
    }


}

