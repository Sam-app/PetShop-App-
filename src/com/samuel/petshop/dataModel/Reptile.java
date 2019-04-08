package com.samuel.petshop.dataModel;

public class Reptile extends Species {
        public String venmous;

    public Reptile(String commonName, String className, String order, String family, String genus, String species, int numberOfLegs, String venmous) {
        super(commonName, className, order, family, genus, species, numberOfLegs);
        this.venmous = venmous;
    }

    public String getVenmous() {
        return venmous;
    }

    public void setVenmous(String venmous) {
        this.venmous = venmous;
    }
}
