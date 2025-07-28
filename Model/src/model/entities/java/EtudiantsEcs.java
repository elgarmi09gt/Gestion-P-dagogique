package model.entities.java;

import java.io.Serializable;

import java.util.ArrayList;

public class EtudiantsEcs implements Serializable {
    private ArrayList<EtudiantUe> etudiants;
    private ArrayList<Ec> listeEcs;
    
    public EtudiantsEcs() {
        super();
    }

    public EtudiantsEcs(ArrayList<EtudiantUe> etudiants, ArrayList<Ec> listeEcs) {
        this.etudiants = etudiants;
        this.listeEcs = listeEcs;
    }

    public void setEtudiants(ArrayList<EtudiantUe> etudiants) {
        this.etudiants = etudiants;
    }

    public ArrayList<EtudiantUe> getEtudiants() {
        return etudiants;
    }

    public void setListeEcs(ArrayList<Ec> listeEcs) {
        this.listeEcs = listeEcs;
    }

    public ArrayList<Ec> getListeEcs() {
        return listeEcs;
    }
}
