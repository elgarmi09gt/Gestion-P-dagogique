package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 11 15:00:15 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ParcoursUserROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdFormation {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CodeNivSec {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormationParcours {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormationCohorte {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormation {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdHistoriquesStructure {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FormAcc {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveau {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibParcours {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Niveauformation {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Cohorte {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Specialite {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Opt {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DepartementUserROVO {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        GradeSemestreROVO {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FilUeInscDelibROVO {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MaquetteParcoursAnneeROVO {
            protected Object get(ParcoursUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(ParcoursUserROVORowImpl object);

        protected abstract void put(ParcoursUserROVORowImpl object, Object value);

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


    public static final int IDFORMATION = AttributesEnum.IdFormation.index();
    public static final int CODENIVSEC = AttributesEnum.CodeNivSec.index();
    public static final int IDNIVEAUFORMATIONPARCOURS = AttributesEnum.IdNiveauFormationParcours.index();
    public static final int IDNIVEAUFORMATIONCOHORTE = AttributesEnum.IdNiveauFormationCohorte.index();
    public static final int IDNIVEAUFORMATION = AttributesEnum.IdNiveauFormation.index();
    public static final int IDHISTORIQUESSTRUCTURE = AttributesEnum.IdHistoriquesStructure.index();
    public static final int FORMACC = AttributesEnum.FormAcc.index();
    public static final int IDNIVEAU = AttributesEnum.IdNiveau.index();
    public static final int LIBPARCOURS = AttributesEnum.LibParcours.index();
    public static final int NIVEAUFORMATION = AttributesEnum.Niveauformation.index();
    public static final int COHORTE = AttributesEnum.Cohorte.index();
    public static final int SPECIALITE = AttributesEnum.Specialite.index();
    public static final int OPT = AttributesEnum.Opt.index();
    public static final int DEPARTEMENTUSERROVO = AttributesEnum.DepartementUserROVO.index();
    public static final int GRADESEMESTREROVO = AttributesEnum.GradeSemestreROVO.index();
    public static final int FILUEINSCDELIBROVO = AttributesEnum.FilUeInscDelibROVO.index();
    public static final int MAQUETTEPARCOURSANNEEROVO = AttributesEnum.MaquetteParcoursAnneeROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ParcoursUserROVORowImpl() {
    }
}

