package com.samuel.petshop;

import com.samuel.petshop.dataModel.Pet;
import com.samuel.petshop.dataModel.PetShop;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.time.LocalDate;


public class AddPetsController {
    @FXML
    private TextArea addPetsTextArea;


   public ObservableList<Pet> processAddAnimalsResult(){
       String[] pets = addPetsTextArea.getText().split("\n");


       String petName;
       String petCommonName;
       double petPrice;
       String petGender;
       String petMainColour;
       LocalDate arrivalDate;




       for(String pet: pets){
           String[] petArray = pet.split(",");
           if (petArray.length >= 4){
               petName = petArray[0];
               petCommonName = petArray[1];
               petPrice = Double.parseDouble(petArray[2]);
               petGender = petArray[3];
               petMainColour = petArray[4];



               if(petArray.length == 5){
                    arrivalDate = LocalDate.parse(petArray[4],PetShop.formater);
               }else{
                   arrivalDate =LocalDate.parse(LocalDate.now().toString(),PetShop.formater);
               }

               Pet newPet = new Pet(petName,petCommonName,petMainColour,petPrice,petGender,arrivalDate);
               PetShop.getInstance().addPet(newPet);
           }

       }

    return PetShop.getInstance().getPets();
   }

}
