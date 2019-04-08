package com.samuel.petshop.dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class PetShop {
    private static PetShop ourInstance = new PetShop();
    private String petListFileName = "petFile.txt";
    private String speciesListFileName = "SpeciesFile.txt";

    private ObservableList<Species> speciesList;
    private ObservableList<Pet> petList;

    public static DateTimeFormatter formater;

    public static PetShop getInstance() {
        return ourInstance;
    }

    private PetShop() {
        formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public ObservableList<Species> getSpeciesList() {
        return speciesList;
    }

    public void setSpeciesList(ObservableList<Species> speciesList) {
        this.speciesList = speciesList;
    }

    public ObservableList<Pet> getPets() {
        return petList;
    }

    public void setPets(ObservableList<Pet> pets) {
        this.petList = pets;
    }

    public void addPet(Pet newPet){
        this.petList.add(newPet);
    }


    public void loadSpeciesList() throws IOException {
        speciesList = FXCollections.observableArrayList();
        Path path = Paths.get(speciesListFileName);
        BufferedReader br = Files.newBufferedReader(path);
        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] speciesProp = input.split("(,)");

                //check all the required propties exist
                if(speciesProp.length > 6) {
                    String comonName = speciesProp[0].trim();
                    String className = speciesProp[1].trim();
                    String order = speciesProp[2].trim();
                    String family = speciesProp[3].trim();
                    String genus = speciesProp[4].trim();
                    String species = speciesProp[5].trim();
                    int numOfLefs = Integer.parseInt(speciesProp[6].trim());


                    String extraFeature; // speciesProp[6].trim();
                    //System.out.println(extraFeature);
                    Species Species;

                    if (speciesProp.length == 8) {
                        extraFeature = speciesProp[7].trim();

                        Species = new ExtendedSpecies(comonName, className, order, family, genus, species, numOfLefs, extraFeature);
                        speciesList.add(((ExtendedSpecies) Species));
                    } else {
                        Species = new Species(comonName, className, order, family, genus, species, numOfLefs);
                        speciesList.add(Species);
                    }

                }


            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeSpeciesList() throws IOException {
        Path path = Paths.get(speciesListFileName);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {
            Iterator<Species> iter = speciesList.iterator();
            while (iter.hasNext()) {
                Species species = iter.next();
                if (species instanceof ExtendedSpecies) {
                    bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s", species.getName()
                            , species.getClassName()
                            , species.getOrder()
                            , species.getFamily()
                            ,species.getGenus()
                            , species.getSpecies()
                            , species.getNumberOfLegs()
                            , ((ExtendedSpecies) species).getExtraFeature()
                    ));
                } else {
                    bw.write(String.format("%s,%s,%s,%s,%s,%s,%s", species.getName()
                            , species.getClassName()
                            , species.getOrder()
                            , species.getFamily()
                            ,species.getGenus()
                            , species.getSpecies()
                            , species.getNumberOfLegs()
                    ));

                }


                //go to the new line
                bw.newLine();

            }


        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void loadPetList() throws IOException {

        petList = FXCollections.observableArrayList();
        Path path = Paths.get(petListFileName);
        BufferedReader br = Files.newBufferedReader(path);
        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] petProp = input.split(",");

                String name = petProp[0].trim();
                String commonName = petProp[1].trim();
                double price = Double.parseDouble(petProp[2].trim());
                String gender = petProp[3].trim();

                String mainColour = petProp[4].trim();
                LocalDate arrivalDate;

                if(petProp.length == 5){
                    arrivalDate = LocalDate.parse(LocalDate.now().format(formater));

                }else  {
                    String arrivalDateStr = petProp[5].trim();
                    arrivalDate = LocalDate.parse(arrivalDateStr, formater);

                }

                Pet pet = new Pet(name,commonName , mainColour,price,gender,arrivalDate);

                if(petProp.length >= 7) {
                    String sellDateStr = petProp[6].trim();
                    LocalDate sellDate = LocalDate.parse(sellDateStr, formater);
                    pet.setSellDate(sellDate);
                }

                petList.add(pet);

                //add the pet's species  if it's availble
                FilteredList<Species> filteredSpecies = PetShop.getInstance().getSpeciesList().filtered(new Predicate<Species>() {
                    @Override
                    public boolean test(Species species) {
                        return species.getName().toLowerCase().equals(pet.getCommonName().toLowerCase());
                    }
                });
//                ((ObservableList<Species>) species).filtered(new Predicate<Species>() {
//                    @Override
//                    public boolean test(Species species) {
//                        return species.getName() == pet.getCommonName();
//                    }
//                });
                Species petSpecies;
                if(filteredSpecies.size()>0){
                    petSpecies = filteredSpecies.get(0);
                    pet.setSpecies(petSpecies);
                }



            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storePetList()throws IOException {
        Path path = Paths.get(petListFileName);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {
            ObservableList petList = PetShop.getInstance().getPets();
            Iterator<Pet> iter = petList.iterator();

            while (iter.hasNext()) {
                Pet pet = iter.next();
                    if(pet.getSellDate()== null){
                        bw.write(String.format("%s,%s,%s,%s,%s,%s",pet.getName()
                                ,pet.getCommonName()
                                ,pet.getPrice()
                                ,pet.getGender()
                                ,pet.getMainColour()
                                ,pet.getArrivalDate()

                        ));

                    }else{
                        bw.write(String.format("%s,%s,%s,%s,%s,%s,%s",pet.getName()
                                ,pet.getCommonName()
                                ,pet.getPrice()
                                ,pet.getGender()
                                ,pet.getMainColour()
                                ,pet.getArrivalDate()
                                ,pet.getSellDate()
                        ));
                    }


                //go to the new line
                bw.newLine();

            }


        } finally {
            if (bw != null) {
                bw.close();
            }
        }


    }


}

