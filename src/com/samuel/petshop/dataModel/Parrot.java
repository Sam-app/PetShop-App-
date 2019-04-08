package com.samuel.petshop.dataModel;

import java.time.LocalDate;

public class Parrot extends Species {
    public String talk;

    public Parrot(String commonName, String className, String order, String family, String genus, String species, int numberOfLegs, String talk) {
        super(commonName, className, order, family, genus, species, numberOfLegs);
        this.talk = talk;
    }

}
