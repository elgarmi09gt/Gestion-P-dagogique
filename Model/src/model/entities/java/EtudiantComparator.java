package model.entities.java;

import java.util.Comparator;

public class EtudiantComparator implements Comparator<Etudiant> {
    public EtudiantComparator() {
        super();
    }

    @Override
    public int compare(Etudiant etd1, Etudiant etd2) {
        // TODO Implement this method
        //System.out.println(etd1.getNom().toUpperCase());
        if(0 == (etd1.getNom().toUpperCase().compareTo(etd2.getNom().toUpperCase()))){
            if(0 == (etd1.getPrenom().toUpperCase().compareTo(etd2.getPrenom().toUpperCase()))){
                return etd1.getNumero().toUpperCase().compareTo(etd2.getNumero().toUpperCase());
            }
            return etd1.getPrenom().toUpperCase().compareTo(etd2.getPrenom().toUpperCase());
        }
        return etd1.getNom().toUpperCase().compareTo(etd2.getNom().toUpperCase());
    }
}
