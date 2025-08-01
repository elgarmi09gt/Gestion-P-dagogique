package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jan 15 10:44:14 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ScolNivFormationROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        CodeNivSec {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleCourt {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ASelection {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AutorisationPermise {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Presentielle {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NbreInsPermises {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Diplomante {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CodeFormation {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Ouvert {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CodeEdoc {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiliseEvaluation {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TagSemestrialisation {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TagGroupematiere {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NiveauCode {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Code {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NombreEtudiant {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Actif {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ScolFormationROVO {
            protected Object get(ScolNivFormationROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolNivFormationROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(ScolNivFormationROVORowImpl object);

        protected abstract void put(ScolNivFormationROVORowImpl object, Object value);

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


    public static final int CODENIVSEC = AttributesEnum.CodeNivSec.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();
    public static final int LIBELLECOURT = AttributesEnum.LibelleCourt.index();
    public static final int ASELECTION = AttributesEnum.ASelection.index();
    public static final int AUTORISATIONPERMISE = AttributesEnum.AutorisationPermise.index();
    public static final int PRESENTIELLE = AttributesEnum.Presentielle.index();
    public static final int NBREINSPERMISES = AttributesEnum.NbreInsPermises.index();
    public static final int DIPLOMANTE = AttributesEnum.Diplomante.index();
    public static final int CODEFORMATION = AttributesEnum.CodeFormation.index();
    public static final int OUVERT = AttributesEnum.Ouvert.index();
    public static final int CODEEDOC = AttributesEnum.CodeEdoc.index();
    public static final int UTILISEEVALUATION = AttributesEnum.UtiliseEvaluation.index();
    public static final int TAGSEMESTRIALISATION = AttributesEnum.TagSemestrialisation.index();
    public static final int TAGGROUPEMATIERE = AttributesEnum.TagGroupematiere.index();
    public static final int NIVEAUCODE = AttributesEnum.NiveauCode.index();
    public static final int CODE = AttributesEnum.Code.index();
    public static final int NOMBREETUDIANT = AttributesEnum.NombreEtudiant.index();
    public static final int ACTIF = AttributesEnum.Actif.index();
    public static final int SCOLFORMATIONROVO = AttributesEnum.ScolFormationROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ScolNivFormationROVORowImpl() {
    }
}

