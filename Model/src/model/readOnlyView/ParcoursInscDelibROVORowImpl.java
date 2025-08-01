package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jan 19 16:40:35 UTC 2023
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ParcoursInscDelibROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdFormation {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormationParcours {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormationCohorte {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormation {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdHistoriquesStructure {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Diplomante {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibParcours {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Niveauformation {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Cohorte {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Specialite {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Opt {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        StudentInsDelibROVO {
            protected Object get(ParcoursInscDelibROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursInscDelibROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(ParcoursInscDelibROVORowImpl object);

        protected abstract void put(ParcoursInscDelibROVORowImpl object, Object value);

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
    public static final int IDNIVEAUFORMATIONPARCOURS = AttributesEnum.IdNiveauFormationParcours.index();
    public static final int IDNIVEAUFORMATIONCOHORTE = AttributesEnum.IdNiveauFormationCohorte.index();
    public static final int IDNIVEAUFORMATION = AttributesEnum.IdNiveauFormation.index();
    public static final int IDHISTORIQUESSTRUCTURE = AttributesEnum.IdHistoriquesStructure.index();
    public static final int DIPLOMANTE = AttributesEnum.Diplomante.index();
    public static final int LIBPARCOURS = AttributesEnum.LibParcours.index();
    public static final int NIVEAUFORMATION = AttributesEnum.Niveauformation.index();
    public static final int COHORTE = AttributesEnum.Cohorte.index();
    public static final int SPECIALITE = AttributesEnum.Specialite.index();
    public static final int OPT = AttributesEnum.Opt.index();
    public static final int STUDENTINSDELIBROVO = AttributesEnum.StudentInsDelibROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ParcoursInscDelibROVORowImpl() {
    }
}

