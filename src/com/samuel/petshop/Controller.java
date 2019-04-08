package com.samuel.petshop;

import com.samuel.petshop.dataModel.Pet;
import com.samuel.petshop.dataModel.PetShop;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {

    @FXML
    private BorderPane mainWindow;
    @FXML
    private ListView petListView;
    @FXML
    private ToggleButton unSoldToggleBtn;
    @FXML
    private ToggleButton soldToggleBtn;
    @FXML
    private Label petDetaileLabel;
    @FXML
    private Label speciesDetailLabel;
    @FXML
    private Label petSellPriceLabel;
    @FXML
    private DatePicker searchDatePicker;
    @FXML
    private Label dayRevenueLabel;
    @FXML
    private  Label monthRevenueLabel;

    private FilteredList<Pet> filteredList;
    private FilteredList<Pet> soldFilteredList;
    private SortedList<Pet> unSoldSortedList;
    private SortedList<Pet> SoldSortedList;
    private Predicate<Pet>   allPets;
    private Predicate<Pet> unSoldPetsPredicate;
    private Predicate<Pet> soldPetsPredicate;


    //Dialog<ButtonType> dialog;

    public void initialize() {

        //intialise the filteredlist,sortedlist and predicate
        allPets = new Predicate<Pet>() {
            @Override
            public boolean test(Pet pet) {
                return true;
            }
        };
        unSoldPetsPredicate = new Predicate<Pet>() {
            @Override
            public boolean test(Pet pet) {

                return (!pet.isAvailable());
            }
        };
        soldPetsPredicate = new Predicate<Pet>() {
            @Override
            public boolean test(Pet pet) {

                return (pet.isAvailable());
            }
        };
        filteredList = new FilteredList<Pet>(PetShop.getInstance().getPets(),allPets);
        soldFilteredList = new FilteredList<Pet>(PetShop.getInstance().getPets(),soldPetsPredicate);


        SoldSortedList = new SortedList<Pet>(soldFilteredList,
                new Comparator<Pet>() {
                    @Override
                    public int compare(Pet o1, Pet o2) {
                        return o1.getSellDate().compareTo(o2.getSellDate());
                    }
                }
        );


       // petListView.setItems(filteredList);
        petListView.setItems(PetShop.getInstance().getPets());
        petListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        petListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showAddPetDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Add New Pet");
        dialog.setHeaderText("Use this dialog to create new pet");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddPetDialog.fxml"));

        try {
            //Parent root = FXMLLoader.load(getClass().getResource("todoItemDialog.fxml"));
            //dialog.getDialogPane().setContent(root);

            dialog.getDialogPane().setContent(fxmlLoader.load());

        }catch(IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        dialog.getDialogPane().lookupButton(ButtonType.OK).setId("AddPetOkButton");
       // dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);


        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){

            System.out.println(" Ok pressed");
            AddPetDialogController controller = fxmlLoader.getController();
            Pet newPet = controller.processResult();

            petListView.getSelectionModel().select(newPet);

        }else{
            System.out.println("Cancel Presseed");
        }

    }

    @FXML
    public void showAddPetsDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Add Pets");
        dialog.setHeaderText("Add pets separated by ; propties separate by comma and ");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddPetsDialog.fxml"));

        try {
            //Parent root = FXMLLoader.load(getClass().getResource("todoItemDialog.fxml"));
            //dialog.getDialogPane().setContent(root);

            dialog.getDialogPane().setContent(fxmlLoader.load());

        }catch(IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //dialog.getDialogPane().lookupButton(ButtonType.OK).setId("AddPetOkButton");
        // dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);


        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){

            System.out.println(" Ok pressed");
            AddPetsController controller = fxmlLoader.getController();
            ObservableList<Pet> newAnimals = controller.processAddAnimalsResult();
         //   petListView.getSelectionModel().select(newAnimals);

        }else{
            System.out.println("Cancel Presseed");
        }

    }


    // Sell button clicked
    @FXML
    public void handleSellPetClicked(){
        Pet pet = (Pet)petListView.getSelectionModel().getSelectedItem();

        //check if pet is selected  is null or not
            if(pet == null){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Pet Selected ");
                alert.setContentText("Please make sure you selected a pet");
                alert.showAndWait();
                return;
            }else if(pet.getSellDate() != null){
                Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(pet.getName()+" already sold on " + pet.getSellDate());
                alert.setContentText("Are you sure you want to sell already sold pet ?");
                Optional<ButtonType>  result =alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK){
                    sellPet(pet);
                }


            }else{
                sellPet(pet);
            }

    }

    @FXML
    public void handleUnSoldToggleBtn(){
        //get the current selected pet
        Pet selectedPet = (Pet) petListView.getSelectionModel().getSelectedItem();
        if(unSoldToggleBtn.isSelected()){

            filteredList.setPredicate(unSoldPetsPredicate);
            unSoldSortedList = new SortedList<Pet>(filteredList,
                    new Comparator<Pet>() {
                        @Override
                        public int compare(Pet o1, Pet o2) {

                            return o1.getArrivalDate().compareTo(o2.getArrivalDate());
                        }
                    });
            petListView.setItems(unSoldSortedList);
            if(filteredList.contains(selectedPet)){
                petListView.getSelectionModel().select(selectedPet);
            }
        }else{
            filteredList.setPredicate(allPets);
            petListView.setItems(filteredList);
            petListView.getSelectionModel().select(selectedPet);
        }

    }

   @FXML
    public void handleSoldToggleBtn(){

        Pet selectedPet = (Pet) petListView.getSelectionModel().getSelectedItem();

        if(soldToggleBtn.isSelected()){

            filteredList.setPredicate(soldPetsPredicate);

             petListView.setItems(SoldSortedList);
            if(filteredList.contains(selectedPet)){
                petListView.getSelectionModel().select(selectedPet);
            }
        }else{
            filteredList.setPredicate(allPets);
            petListView.setItems(PetShop.getInstance().getPets());
            petListView.getSelectionModel().select(selectedPet);
        }

    }

    @FXML
    public  void sellPet(Pet pet){
        //update the list by removing the old pet and add the new one
        PetShop.getInstance().getPets().remove(pet);
        pet.setSellDate(LocalDate.parse(LocalDate.now().toString(),PetShop.formater));
        PetShop.getInstance().getPets().add(0,pet);
        petListView.setItems(PetShop.getInstance().getPets());

        // select the update pet that is first in the list
        petListView.getSelectionModel().selectFirst();

    }

    //handle listclicked
    @FXML
    public void handleListClicked(){

        Pet selectedPet =(Pet) petListView.getSelectionModel().getSelectedItem();
        petDetaileLabel.setText(selectedPet.toString());
        // Display the pet species if it available
        if(selectedPet.getSpecies()!=null){
            speciesDetailLabel.setText(selectedPet.getSpecies().toString());
        }else {
            speciesDetailLabel.setText(selectedPet.getCommonName()+ " species not specified");
        }

        if(selectedPet.getSellDate()!= null) {
            petSellPriceLabel.setText(String.format("Sold on " + selectedPet.getSellDate().toString()+ " for %.2f ", selectedPet.getSellPrice()));
        }else{
            petSellPriceLabel.setText(String.format(selectedPet.getName()+ " is still available for sell"));
           // petDetaileLabel.setText("");
        }

    }

    @FXML
    public void calculateRevenue(){
        double dayRevenue = 0;
        double  monthRevenue = 0;

        //calculate if date is given
        if(searchDatePicker.getValue()!= null){
            //get seach date picker
            //and format it
            LocalDate searchDate = LocalDate.parse(searchDatePicker.getValue().toString(),PetShop.formater);

            ObservableList petList = PetShop.getInstance().getPets();
            Iterator<Pet> iter = petList.iterator();

            while (iter.hasNext()){
                Pet pet = iter.next();
                if(pet.getSellDate()!= null){

                    if (pet.getSellDate().getDayOfYear() ==searchDate.getDayOfYear()&&
                            pet.getSellDate().getYear() ==searchDate.getYear()){
                        dayRevenue += pet.getSellPrice();

                    }

                    if(pet.getSellDate().getYear() == searchDate.getYear() &&
                            pet.getSellDate().getMonthValue()== searchDate.getMonthValue()){
                            monthRevenue += pet.getSellPrice();
                    }

                }
            }
            dayRevenueLabel.setText(String.format("Day revenue : %.4f",dayRevenue));
            monthRevenueLabel.setText(String.format("Month revenue : %.4f",monthRevenue));

        }


    }





}
