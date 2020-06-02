package HRA.controllers;

import HRA.model.HotelManager;
import HRA.model.User;
import HRA.services.HotelManagerService;
import HRA.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

//TODO
//Fields for information about: Images, Facilities List, Details list



public class HotelCustomerOverviewController {

    private List<HotelManager> HM = HotelManagerService.getHotelManagersFromHotelManagerService();

    private static Scene mainHotelCustomerOverviewScene;
    private Stage mainLoginStage = LoginController.getPrimaryStageFromLC();

    public void handleHCO(){

        List <String> facilities;

        /*
        for(HotelManager hotelManager : HM) {
            if (Objects.equals(hotelManager.getUsername(), )) {

            }
        }*/

        //HOME BUTTON
        Button homeButton = new Button("Home");
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoggedCustomerController LCC = new LoggedCustomerController();
                LCC.getMainScene();
                mainLoginStage.setScene(LCC.getMainScene());
            }
        });

        //RESERVE BUTTON
        Button reserveButton = new Button("Reserve");


        //BUTTONS IN MAIN PAGE
        HBox reservePane = new HBox();
        reservePane.setAlignment(Pos.CENTER);
        reservePane.getChildren().addAll(reserveButton,homeButton);

        //MAIN LAYOUT
        VBox layout = new VBox(10);
        layout.getChildren().addAll(reservePane);

        //MAIN PAGE SPACING
        layout.setPadding(new Insets(20,20,20,20));

        mainHotelCustomerOverviewScene = new Scene(layout, 1280, 720);
    }

    public Scene getMainScene(){
        return mainHotelCustomerOverviewScene;
    }

}

