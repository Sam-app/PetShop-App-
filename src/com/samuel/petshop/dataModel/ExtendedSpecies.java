package com.samuel.petshop.dataModel;

public class ExtendedSpecies extends Species{
    private String extraFeature;

    public ExtendedSpecies(String commonName, String className, String order, String family, String genus, String species, int numberOfLegs, String extraFeature) {
        super(commonName, className, order, family, genus, species, numberOfLegs);
        this.extraFeature = extraFeature;
    }

    public String getExtraFeature() {
        return extraFeature;
    }

    public void setExtraFeature(String extraFeature) {
        this.extraFeature = extraFeature;
    }

    @Override
    public String toString(){
        return String.format("%s , %s , %s , %s , %s , %s , %s , %s", name,className,order,family,genus,species,numberOfLegs,extraFeature);
    }
}
