package model.readOnlyView;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jan 14 10:01:58 GMT 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AttrSearchNumDiplomeROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdAnnee {
            protected Object get(AttrSearchNumDiplomeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrSearchNumDiplomeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Numero {
            protected Object get(AttrSearchNumDiplomeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrSearchNumDiplomeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdStructure {
            protected Object get(AttrSearchNumDiplomeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrSearchNumDiplomeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        num {
            protected Object get(AttrSearchNumDiplomeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrSearchNumDiplomeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AnneesUniversVO1 {
            protected Object get(AttrSearchNumDiplomeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrSearchNumDiplomeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        HistResultAncienROVO1 {
            protected Object get(AttrSearchNumDiplomeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrSearchNumDiplomeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AnneeUnivAnscInscROVO1 {
            protected Object get(AttrSearchNumDiplomeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrSearchNumDiplomeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(AttrSearchNumDiplomeROVORowImpl object);

        protected abstract void put(AttrSearchNumDiplomeROVORowImpl object, Object value);

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


    public static final int IDANNEE = AttributesEnum.IdAnnee.index();
    public static final int NUMERO = AttributesEnum.Numero.index();
    public static final int IDSTRUCTURE = AttributesEnum.IdStructure.index();
    public static final int NUM = AttributesEnum.num.index();
    public static final int ANNEESUNIVERSVO1 = AttributesEnum.AnneesUniversVO1.index();
    public static final int HISTRESULTANCIENROVO1 = AttributesEnum.HistResultAncienROVO1.index();
    public static final int ANNEEUNIVANSCINSCROVO1 = AttributesEnum.AnneeUnivAnscInscROVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AttrSearchNumDiplomeROVORowImpl() {
    }
}

