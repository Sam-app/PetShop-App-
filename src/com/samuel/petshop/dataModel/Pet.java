package com.samuel.petshop.dataModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public class Pet implements Gender {
    private String name;
    private String mainColour;
    private double price;
    private LocalDate arrivalDate;
    private LocalDate sellDate;
    private String gender;
    private String commonName;
    private Species species;


    public Pet(String name ,String commonName ,String mainColour, double price,String gender, LocalDate arrivalDate
               /* ,LocalDate sellDate */) {
        this.name = name;
        this.mainColour = mainColour;
        this.price = price;
        this.gender = gender;
        this.arrivalDate = arrivalDate;
        this.sellDate = sellDate;
        this.commonName = commonName;
    }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCommonName() {
                return commonName;
            }

            public void setCommonName(String commonName) {
                this.commonName = commonName;
            }

            public String getMainColour() {
                        return mainColour;
            }

            public void setMainColour(String mainColour) {
                this.mainColour = mainColour;
            }


            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public LocalDate getArrivalDate() {
                return arrivalDate;
            }

            private String getArrivalDateStr() { return arrivalDate.toString();}

            public void setArrivalDate(LocalDate arrivalDate) {
                this.arrivalDate = arrivalDate;
            }

            public LocalDate getSellDate() {
                return sellDate;
            }
            // get sell date string
            private String getSellDateStr(){
                if(getSellDate() == null){
                    return "";
                }else{
                    return sellDate.toString();
                }

            }

            public void setSellDate(LocalDate sellDate) {
                this.sellDate = sellDate;
            }

            public void setSpecies(Species species) {
                this.species = species;
            }

            public Species getSpecies() {
                return species;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public boolean isAvailable(){
                return this.sellDate != null;
            }



    public double getSellPrice(){

             // check if the animal was more than 4 or 2 months
             boolean is2Months =  this.getSellDate().isAfter(this.getArrivalDate().plusDays(60));
             boolean is4Months = this.getSellDate().isAfter(this.getArrivalDate().plusDays(120));
             if(is4Months){
                 return (this.price - (this.price * 0.8));
             }else if(is2Months){
                 return (this.price - (this.price * 0.1));
             }else {
                 return this.price;
             }
    }



            @Override
            public String toString(){

                return String.format("%s , %s , %.2f, %s , %s",name,commonName,price, getArrivalDateStr(),getSellDateStr());
            }
}
