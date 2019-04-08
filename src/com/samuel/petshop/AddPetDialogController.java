package com.samuel.petshop;

import com.samuel.petshop.dataModel.Pet;
import com.samuel.petshop.dataModel.PetShop;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddPetDialogController {
    @FXML
    private TextField petNameTextField;
    @FXML
    private TextField petCommonNameTextField;
    @FXML
    private TextField petPriceTextField;
    @FXML
    private TextField petMainColourTextField;
    @FXML
    private  ToggleGroup petGenderToggleGroup;
    @FXML
    private DatePicker arrivalDatePicker;
    @FXML
    private DatePicker SellDatePicker;
    @FXML
    private DialogPane addPetDialog;

     private DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize() {
        petPriceTextField.setTextFormatter(formatter);

//        petCommonNameTextField.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
//            @Override
//            public void handle(javafx.scene.input.KeyEvent event) {
//                if (!(petCommonNameTextField.getText().trim().isEmpty())){
//                    addPetDialog.lookupButton(ButtonType.OK).setDisable(false);
//            }
//        }
//         });

    }

    public Pet processResult() {
        String petName = petNameTextField.getText().trim();
        String petCommonName = petCommonNameTextField.getText().trim();
        String petMainColour = petMainColourTextField.getText().trim();
        String petPriceString = petPriceTextField.getText().trim();
        String petGender = petGenderToggleGroup.getSelectedToggle()!= null ?
                petGenderToggleGroup.getSelectedToggle().getUserData().toString():"";

        LocalDate arrivalDate;
        Double price;
        Pet newPet;


        if(petName.isEmpty()||petCommonName.isEmpty()||petMainColour.isEmpty()||petPriceString.isEmpty()||
                petGender.isEmpty()){

            System.out.println("Invalid pet input");

            newPet = null;

        }else {
            // parse the price string into double type
            price = Double.parseDouble(petPriceString);
                if (arrivalDatePicker.getValue() != null) {
                    // assign current date to arrival
                    arrivalDate = LocalDate.parse(arrivalDatePicker.getValue().toString(), formater);

                } else {
                    //System.out.println("arrive date is null");
                    arrivalDate = LocalDate.parse(LocalDate.now().format(formater));

                }


            newPet = new Pet(petName,petCommonName,petMainColour,price,petGender,arrivalDate);

                //if selldate picker not null
            if(SellDatePicker.getValue()!= null){
                    newPet.setSellDate(LocalDate.parse(SellDatePicker.getValue().toString(),formater));
            }

            PetShop.getInstance().addPet(newPet);


        }



        //Pet newPet = new Pet("Test name", "Teest common name", "Test Colour", 12.0, "Test gender", LocalDate.now());


        return newPet;
    }


    Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
    TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
        return pattern.matcher(change.getControlNewText()).matches() ? change : null;
    });

//    @FXML
//    public void handleAddPetKeyReleased(){
//        boolean isNameEmpty = petNameTextField.getText().trim().isEmpty();
//        if(!isNameEmpty){
//            addPetDialog.lookupButton(ButtonType.OK).setDisable(false);
//        }
//
//    }


}