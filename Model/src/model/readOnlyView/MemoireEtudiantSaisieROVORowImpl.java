package model.readOnlyView;

import java.math.BigDecimal;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 10 12:38:57 UTC 2023
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MemoireEtudiantSaisieROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdEtudiant {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdParcoursMaquetAnnee {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Numero {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Prenoms {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Nom {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EmailInstitutionnel {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Memoiresaisie {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibMemoire {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DoubleAssimileTireeLOV1 {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MemoireSaisieLov1 {
            protected Object get(MemoireEtudiantSaisieROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MemoireEtudiantSaisieROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(MemoireEtudiantSaisieROVORowImpl object);

        protected abstract void put(MemoireEtudiantSaisieROVORowImpl object, Object value);

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


    public static final int IDETUDIANT = AttributesEnum.IdEtudiant.index();
    public static final int IDPARCOURSMAQUETANNEE = AttributesEnum.IdParcoursMaquetAnnee.index();
    public static final int NUMERO = AttributesEnum.Numero.index();
    public static final int PRENOMS = AttributesEnum.Prenoms.index();
    public static final int NOM = AttributesEnum.Nom.index();
    public static final int EMAILINSTITUTIONNEL = AttributesEnum.EmailInstitutionnel.index();
    public static final int MEMOIRESAISIE = AttributesEnum.Memoiresaisie.index();
    public static final int LIBMEMOIRE = AttributesEnum.LibMemoire.index();
    public static final int DOUBLEASSIMILETIREELOV1 = AttributesEnum.DoubleAssimileTireeLOV1.index();
    public static final int MEMOIRESAISIELOV1 = AttributesEnum.MemoireSaisieLov1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MemoireEtudiantSaisieROVORowImpl() {
    }
}

