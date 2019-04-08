package com.samuel.petshop.dataModel;

public class Species {
        //n.b class is reserved word in java
        protected String name;
        protected String className;
        protected String order;
        protected String family;
        protected String genus;
        protected String species;
        protected int numberOfLegs;


        public Species(String name, String className, String order, String family, String genus, String species, int numberOfLegs) {
            this.name = name;
            this.className = className;
            this.order = order;
            this.family = family;
            this.genus = genus;
            this.species = species;
            this.numberOfLegs = numberOfLegs;
        }

        /* return the class propertyies through get methods
           and we don't need set methods */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
            return className;
        }

        public String getOrder() {
            return order;
        }

        public String getFamily() {
            return family;
        }

        public String getGenus() {
            return genus;
        }

        public String getSpecies() {
            return species;
        }

        public int getNumberOfLegs(){
            return numberOfLegs;
        }


        @Override
        public String toString(){
        return String.format("%s , %s , %s , %s , %s , %s , %s", name,className,order,family,genus,species,numberOfLegs);
        }
}
