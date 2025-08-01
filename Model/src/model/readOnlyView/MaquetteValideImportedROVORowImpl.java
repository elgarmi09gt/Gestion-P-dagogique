package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 03 15:57:18 UTC 2023
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MaquetteValideImportedROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdMaquetteValide {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormationParcours {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdAnneesUnivers {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdMaquette {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Inscriptionouverte {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ParcoursRespFrImpMaqEtdROVO {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MaquetteIntegratedNivSecROVO {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EtatDeliberationAnROVO {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EtatDeliberationSemROVO {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AnneeUniverROVO1 {
            protected Object get(MaquetteValideImportedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(MaquetteValideImportedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(MaquetteValideImportedROVORowImpl object);

        protected abstract void put(MaquetteValideImportedROVORowImpl object, Object value);

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


    public static final int IDMAQUETTEVALIDE = AttributesEnum.IdMaquetteValide.index();
    public static final int IDNIVEAUFORMATIONPARCOURS = AttributesEnum.IdNiveauFormationParcours.index();
    public static final int IDANNEESUNIVERS = AttributesEnum.IdAnneesUnivers.index();
    public static final int IDMAQUETTE = AttributesEnum.IdMaquette.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();
    public static final int INSCRIPTIONOUVERTE = AttributesEnum.Inscriptionouverte.index();
    public static final int PARCOURSRESPFRIMPMAQETDROVO = AttributesEnum.ParcoursRespFrImpMaqEtdROVO.index();
    public static final int MAQUETTEINTEGRATEDNIVSECROVO = AttributesEnum.MaquetteIntegratedNivSecROVO.index();
    public static final int ETATDELIBERATIONANROVO = AttributesEnum.EtatDeliberationAnROVO.index();
    public static final int ETATDELIBERATIONSEMROVO = AttributesEnum.EtatDeliberationSemROVO.index();
    public static final int ANNEEUNIVERROVO1 = AttributesEnum.AnneeUniverROVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MaquetteValideImportedROVORowImpl() {
    }
}

