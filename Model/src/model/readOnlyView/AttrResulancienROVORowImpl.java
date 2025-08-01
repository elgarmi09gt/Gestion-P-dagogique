package model.readOnlyView;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.NClobDomain;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jan 13 11:15:52 GMT 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AttrResulancienROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        EtudAnc {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdAnnee {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Valide {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NumDiplome {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateDelib {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdOption {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdSession {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateDeliv {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NumIdentite {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdTypeResulat {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdTypeMention {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AnneesUniversVO1 {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OptionsVO1 {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TypeMentionVO1 {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        SessionsVO1 {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TypeResultatVO1 {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ResultAncienValiderLOV1 {
            protected Object get(AttrResulancienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AttrResulancienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(AttrResulancienROVORowImpl object);

        protected abstract void put(AttrResulancienROVORowImpl object, Object value);

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


    public static final int ETUDANC = AttributesEnum.EtudAnc.index();
    public static final int IDANNEE = AttributesEnum.IdAnnee.index();
    public static final int VALIDE = AttributesEnum.Valide.index();
    public static final int NUMDIPLOME = AttributesEnum.NumDiplome.index();
    public static final int DATEDELIB = AttributesEnum.DateDelib.index();
    public static final int IDOPTION = AttributesEnum.IdOption.index();
    public static final int IDSESSION = AttributesEnum.IdSession.index();
    public static final int DATEDELIV = AttributesEnum.DateDeliv.index();
    public static final int NUMIDENTITE = AttributesEnum.NumIdentite.index();
    public static final int IDTYPERESULAT = AttributesEnum.IdTypeResulat.index();
    public static final int IDTYPEMENTION = AttributesEnum.IdTypeMention.index();
    public static final int ANNEESUNIVERSVO1 = AttributesEnum.AnneesUniversVO1.index();
    public static final int OPTIONSVO1 = AttributesEnum.OptionsVO1.index();
    public static final int TYPEMENTIONVO1 = AttributesEnum.TypeMentionVO1.index();
    public static final int SESSIONSVO1 = AttributesEnum.SessionsVO1.index();
    public static final int TYPERESULTATVO1 = AttributesEnum.TypeResultatVO1.index();
    public static final int RESULTANCIENVALIDERLOV1 = AttributesEnum.ResultAncienValiderLOV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AttrResulancienROVORowImpl() {
    }
}

