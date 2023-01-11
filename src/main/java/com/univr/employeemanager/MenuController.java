package com.univr.employeemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class MenuController implements Initializable {


    //oggetti usati per la ricerca
    //---------------------------------------------------
    @FXML
    private RadioButton ORenable,ANDenable;
    @FXML
    private CheckBox hasLicenseEnable,hasCarEnable,birthIntervalEnable,periodIntervalEnable;
    @FXML
    private DatePicker birthFromDate,birthToDate,periodFromDate,periodToDate;
    @FXML
    private Button searchButton,restoreButton;

    //#####################################################
    @FXML
    private ChoiceBox<String> language1,language2;
    private static String[] languages= { "English","Italian","French","Spanish","Arabic","Chinese","Portoguese","Japanese","German","none"};
    //#####################################################
    //----------------------------------------------------

    @FXML
    private Button newButton, editButton, deleteButton, detailsButton;
    @FXML
    private TableColumn <Employee, String> addressField, nameField, lastNameField, cellNumberField;
    @FXML
    private TableColumn<Employee,LocalDate>birthDateField;
    @FXML
    private TableView<Employee> mainTable;
    @FXML
    private Label display;
    private ObservableList<Employee> people;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private final JSONReadWrite data = new JSONReadWrite("src/main/java/com/univr/employeemanager/data.json");

    public MenuController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        detailsButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        ORenable.setSelected(true);

        birthFromDate.setDisable(false);
        birthIntervalEnable.setDisable(true);
        periodIntervalEnable.setDisable(true);


        mainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
            {
                detailsButton.setDisable(false);
                deleteButton.setDisable(false);
                editButton.setDisable(false);
            }

        });

        nameField.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthDateField.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        cellNumberField.setCellValueFactory(new PropertyValueFactory<>("cellNumber"));
        addressField.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            updateTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //#####################################################
        language1.getItems().addAll(languages);
        language2.getItems().addAll(languages);

        language1.setOnAction(this::language1Press);
        language2.setOnAction(this::language2Press);


        //#####################################################
    }

    @FXML
    public void textAreaClicked(MouseEvent mouseEvent) {
        mainTable.getSelectionModel().clearSelection();
        detailsButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }

    @FXML
    protected void newButtonPress(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void editButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        if(selected != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            root = loader.load();

            AddEmployeeController employeeController = loader.getController();
            employeeController.updateField(selected, true);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    protected void deleteButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Employee delete");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            if (selected != null){

                data.remove(selected);
                updateTable();
            }
        }
    }
    @FXML
    protected void detailsButtonPress(ActionEvent e) throws IOException {

        Employee selected = mainTable.getSelectionModel().getSelectedItem();

        if (selected != null){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            root = loader.load();

            AddEmployeeController employeeController = loader.getController();
            employeeController.updateField(selected,false);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private void updateTable() throws IOException {

        people = FXCollections.observableArrayList(data.readJSON());
        mainTable.setItems(people);
    }

    //metodi usati per la ricerca
    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    TreeSet<Employee> result=new TreeSet<>();
    TreeSet<Employee> hasCarResult =new TreeSet<>();
    TreeSet<Employee> hasLicenseResult=new TreeSet<>();
    TreeSet<Employee> birthDateResult=new TreeSet<>();
    TreeSet<Employee> periodDateResult=new TreeSet<>();
    TreeSet<Employee> language1Result=new TreeSet<>();
    TreeSet<Employee> language2Result=new TreeSet<>();

    public void ORbuttonPress(ActionEvent actionEvent) {

        ANDenable.setSelected(false);
        searchButton.setDisable(false);
    }

    public void ANDbuttonPress(ActionEvent actionEvent) {

        ORenable.setSelected(false);
        searchButton.setDisable(false);
    }

    public void searchButtonPress(ActionEvent actionEvent) {

        //questi 2 simpatici signori li faccio partire anche se premo il tasto search, così
        //possono fare la ricerca con l'ultimo valore assegnato, senza dover ripremere sul box
        language1.fireEvent(new ActionEvent());
        language2.fireEvent(new ActionEvent());


        if(this.result!=null)
            result.clear();

        //ricerca in or: aggiungo al treeset risultato, tutti i treeset delle ricerche singole
        //il treeset si occuperà di non avere ripetizioni (vedi compareTo di Person)
        if(ORenable.isSelected())
        {
            result.addAll(hasCarResult);
            result.addAll(hasLicenseResult);
            result.addAll(birthDateResult);
            result.addAll(periodDateResult);
            result.addAll(language1Result);
            result.addAll(language2Result);

        }

        //ricerca in AND: aggiungo al treeset risultato, tutti i treeset delle ricerche singole
        //infine elimino dal risultato tutti gli elementi che non sono in comune con ogni treeset di ogni singola ricerca
        //difatto eseguendo un intersezione, quindi un AND
        if(ANDenable.isSelected())
        {
            result.addAll(hasCarResult);
            result.addAll(hasLicenseResult);
            result.addAll(birthDateResult);
            result.addAll(periodDateResult);
            result.addAll(language1Result);
            result.addAll(language2Result);

            if(hasCarEnable.isSelected())
                result.retainAll(hasCarResult);
            if(hasLicenseEnable.isSelected())
                result.retainAll(hasLicenseResult);
            if(birthIntervalEnable.isSelected())
                result.retainAll(birthDateResult);
            if(periodIntervalEnable.isSelected())
                result.retainAll(periodDateResult);
            if(!language1.getValue().equalsIgnoreCase("None"))
                result.retainAll(language1Result);
            if(!language2.getValue().equalsIgnoreCase("None"))
                result.retainAll(language2Result);
        }

        people= FXCollections.observableArrayList(result);
        mainTable.setItems(people);
        clearFields();

    }

    public void restoreButtonPress(ActionEvent actionEvent) throws IOException {

        updateTable();
        result.clear();
        clearFields();
    }
    private void clearFields()
    {
        hasCarResult.clear();
        hasLicenseResult.clear();
        birthDateResult.clear();
        periodDateResult.clear();
        hasCarEnable.setSelected(false);
        hasLicenseEnable.setSelected(false);
        birthIntervalEnable.setSelected(false);
        periodIntervalEnable.setSelected(false);
    }

    //stream di employee, filtro quelli che non hanno il set licenze vuoto
    //quindi li aggiungo al set risultato di questa ricerca
    public void hasLicenseEnablePress(ActionEvent actionEvent) {

        if(hasLicenseResult!=null)
            hasLicenseResult.clear();

        people.stream()
                .filter(p -> !p.getLicenses().isEmpty())
                    .forEach(p->hasLicenseResult.add(p));
    }

    //stream di employee, filtro quelli che hanno una macchina
    //quindi li aggiungo al set risultato di questa ricerca
    public void hasCarEnablePress(ActionEvent actionEvent) {

        if(hasCarResult!=null)
            hasCarResult.clear();

        people.stream()
                .filter(p -> p.hasCar())
                    .forEach(p-> hasCarResult.add(p));

    }


    //ricerca per data di nascita
    public void birthIntervalEnablePress(ActionEvent actionEvent) {

        if(birthDateResult!=null)
            birthDateResult.clear();

        //se la data di fine e di inizio contengono data valida
        if(birthFromDate!=null&&birthToDate!=null)
        {
            System.out.print("\nsearching from: "+birthFromDate.getValue());
            System.out.print("\nto              "+birthToDate.getValue());


                people.stream()
                        .filter(p->p.getBirthDate().isAfter(birthFromDate.getValue())&&p.getBirthDate().isBefore(birthToDate.getValue()))
                        .forEach(p->birthDateResult.add(p));
        }

        System.out.print("\ndate stream result:");
        for (Employee d:birthDateResult
        ) {
            System.out.print("\n"+d.getBirthDate()+" "+d.getFirstName());
        }

        System.out.print("\n");

    }

    //permetto di abilitare il checkbox date solo se hanno entrambe un valore dentro
    public void birthFromDatePress(ActionEvent actionEvent) {
        birthIntervalEnable.setDisable(birthFromDate.getValue() == null || birthToDate.getValue() == null);

    }
    public void birthToDatePress(ActionEvent actionEvent) {
        birthIntervalEnable.setDisable(birthToDate.getValue() == null || birthFromDate.getValue() == null);
    }


    //ricerca per periodo di disponibilità
    public void periodIntervalEnablePress(ActionEvent actionEvent) {

        if(periodDateResult!=null)
            periodDateResult.clear();

        //se la data di fine e di inizio contengono data valida
        if(periodFromDate!=null&&periodToDate!=null)
        {
            System.out.print("\nsearching period from: "+periodFromDate.getValue());
            System.out.print("\nto                     "+periodToDate.getValue());

            people.stream()
                    .filter(p->p.getAvailablePeriod()[0]!=null)
                    .filter(p->p.getAvailablePeriod()[0].isAfter(periodFromDate.getValue())&&p.getAvailablePeriod()[1].isBefore(periodToDate.getValue()))
                    .forEach(p->periodDateResult.add(p));
        }

        System.out.print("\nperiod date stream result:");
        for (Employee d:periodDateResult) {
            System.out.print("\n"+d.getAvailablePeriod()[0]+" | "+d.getAvailablePeriod()[1]+" "+d.getFirstName());
        }
        System.out.print("\n");

    }

    //permetto di abilitare il checkbox date solo se hanno entrambe un valore dentro
    public void periodFromDatePress(ActionEvent actionEvent) {
        periodIntervalEnable.setDisable(periodFromDate.getValue() == null || periodToDate.getValue() == null);
    }

    public void periodToDatePress(ActionEvent actionEvent) {
        periodIntervalEnable.setDisable(periodFromDate.getValue() == null || periodToDate.getValue() == null);
    }


    //ricerca per lingue parlate
    public void language1Press(ActionEvent event){
        if(language1Result!=null)
            language1Result.clear();

       System.out.println("language1: "+language1.getValue());
       people.stream()
               .filter(p->p.getSpokenLanguage().toString().contains(language1.getValue().toUpperCase()))
               .forEach(p->language1Result.add(p));

       System.out.println("language1 stream result:");
       for (Employee e:language1Result){
           System.out.println(e.getFirstName()+"  languages: "+e.getSpokenLanguage());
       }
        System.out.print("\n");

    }

    public void language2Press(ActionEvent event){

        if(language2Result!=null)
            language2Result.clear();

        System.out.println("language2: "+language2.getValue());
        people.stream()
                .filter(p->p.getSpokenLanguage().toString().contains(language2.getValue().toUpperCase()))
                .forEach(p->language2Result.add(p));

        System.out.println("language2 stream result:");
        for (Employee e:language2Result){
            System.out.println(e.getFirstName()+"  languages: "+e.getSpokenLanguage());
        }
        System.out.print("\n");

    }
    @FXML
    protected void logoutButtonPress(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
