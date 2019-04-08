package com.samuel.petshop.dataModel;

import java.time.LocalDate;

public interface Animal {
    public String name = null;
    double price = 0;
    boolean available = true;
    String mainColor = null;
    LocalDate arrivalDate = null;
    LocalDate sellingDate = null;

    String getName();




}
