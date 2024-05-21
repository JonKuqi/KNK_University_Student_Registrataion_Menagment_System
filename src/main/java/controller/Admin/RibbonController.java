package controller.Admin;

import app.Navigatior;
import controller.Animations.UpLogoAnimate;
import controller.BackAndForth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import controller.SESSION;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RibbonController {

    @FXML
    private AnchorPane addPane;
    @FXML
    private ImageView imgProfileIcon;
    @FXML
    private ImageView imgInboxIcon;
    @FXML
    private ImageView imgStudentIcon;
    @FXML
    private ImageView imgSupervisorIcon;
    //@FXML
   // private ImageView imgUpLogo;
    @FXML
    private AnchorPane anchorLogo;
    @FXML
    private ImageView imgRegistrationIcon;
    @FXML
    private ImageView imgInfoIcon;

    @FXML
    private VBox vboxMainPane;



    private UpLogoAnimate logo = new UpLogoAnimate(50, "FIEK Management", 5, 1);

    @FXML
    private ImageView imgGoBack;
    @FXML
    private ImageView imgGoForward;

    private Image imageGoBackWhite;
    private Image imageGoBackGray;
    private Image imageGoForwardWhite;
    private Image imageGoForwardGray;

    @FXML
    private void initialize(){
        if(BackAndForth.getIndex() == -1){
            SESSION.setAdminMenu("Student");
            Navigatior.navigate(this.addPane, Navigatior.ADMIN_MENU);
            BackAndForth.addOnePage(Navigatior.ADMIN_MENU+"S");
        }else{
            this.navigateAndSaveState(Navigatior.ADMIN_MENU+"S");
        }

        try {
            this.imageGoBackWhite = new Image(new FileInputStream("src/main/resources/Images/backArrow.png"));
            this.imageGoBackGray = new Image(new FileInputStream("src/main/resources/Images/grayBackArrow.png"));
            this.imageGoForwardWhite = new Image(new FileInputStream("src/main/resources/Images/forwardArrow.png"));
            this.imageGoForwardGray = new Image(new FileInputStream("src/main/resources/Images/grayForwardArrow.png"));
        } catch (FileNotFoundException e) {
         //
        }


        try {
            this.imgProfileIcon.setImage(new Image(new FileInputStream("Images/profileIcon.png")));
            this.imgStudentIcon.setImage(new Image(new FileInputStream("Images/studentIcon.png")));
            this.imgSupervisorIcon.setImage(new Image(new FileInputStream("Images/supervisorIcon.png")));
            this.imgInboxIcon.setImage(new Image(new FileInputStream("Images/inboxIcon.png")));
            this.imgRegistrationIcon.setImage(new Image(new FileInputStream("Images/registrationIcon.png")));
            this.imgInfoIcon.setImage(new Image(new FileInputStream("Images/info-icon.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Image not found");
        }
        this.anchorLogo.getChildren().add(logo);
        this.anchorLogo.setTranslateY(25);

        // E bon display ni text kur tbohet hover logo e infos
        Tooltip tooltip = new Tooltip("Info");

        Tooltip.install(imgInfoIcon, tooltip);

        this.setButtons();



        //GO back and Forth me buttona
        this.vboxMainPane.setOnKeyPressed(e->{
            if(e.isControlDown()){
                if(e.getCode() == KeyCode.Z){
                    this.goBack();
                }else if(e.getCode() == KeyCode.Y){
                    this.goForth();
                }
            }
        });
        this.vboxMainPane.requestFocus();
    }

    @FXML
    private void handleUpLogoClick(MouseEvent ae){
        //Navigu ne Dashboard
        Navigatior.navigate(this.addPane, Navigatior.DASHBOARD);
    }

    @FXML
    private void handleSupervisorMenagmentClick(MouseEvent me){
        SESSION.setAdminMenu("Supervisor");
        this.navigateAndSaveState(Navigatior.ADMIN_MENU+"M");

    }
    @FXML
    private void handleStudentMenagmentClick(MouseEvent me){
        SESSION.setAdminMenu("Student");
        this.navigateAndSaveState(Navigatior.ADMIN_MENU+"S");
    }
    @FXML
    private void handleRegistrationPeriodClick(MouseEvent me){
        SESSION.setAdminMenu("Afat");
        this.navigateAndSaveState(Navigatior.ADMIN_MENU+"A");
    }
    @FXML
    private void handleInboxClick(MouseEvent me){
        this.navigateAndSaveState(Navigatior.ADMIN_INBOX);
    }
    @FXML
    private void handleProfileClick(MouseEvent me){
        this.navigateAndSaveState(Navigatior.ADMIN_PROFILE);
    }

    @FXML
    private void handleGoToProfile(ActionEvent ae){
        this.navigateAndSaveState(Navigatior.ADMIN_PROFILE);
    }
    @FXML
    private void handleChangeLanguage(ActionEvent ae){
       // Navigatior.navigate(this.addPane, "");
        //
    }
    @FXML
    private void handleSignOut(ActionEvent ae){
        Navigatior.navigateNewStage(Navigatior.LOGIN);
        SESSION.setLoggedAdmin(null);
       // Navigatior.closeStageAfterDelay(ae, Duration.millis(100));
    }

    @FXML
    private void handleLogoClicked(MouseEvent me){
        this.navigateAndSaveState(Navigatior.DASHBOARD);
    }
    @FXML
    private void handleStartAnimation(MouseEvent me){
        this.logo.startWithMouse();
    }


    @FXML
    private void handleInfoIconClicked(MouseEvent me){
        Navigatior.navigateNewStage(Navigatior.HELP_ADMIN);
    }


    @FXML
    void handleBackClick(MouseEvent me) {
        this.goBack();

    }


    private void goBack(){
        String currentPage =BackAndForth.gotoPreviousPage();
        if(currentPage !=null) {
            if(currentPage.contains("admin-Menu")) {
                char menuChar = currentPage.charAt(currentPage.length()-1);
                if(menuChar == 'S'){
                    SESSION.setAdminMenu("Student");
                } else if (menuChar == 'M') {
                    SESSION.setAdminMenu("Supervisor");
                }else if (menuChar == 'A') {
                    SESSION.setAdminMenu("Afat");
                }
                Navigatior.navigate(this.addPane, currentPage.substring(0, currentPage.length()-1));
            }else {
                Navigatior.navigate(this.addPane, currentPage);
            }
            System.out.println("Index:" + BackAndForth.getIndex());
        }

//        System.out.println("Anetaret E linked List");
//        System.out.println("__________________________________________________________________");
//        for(String s:BackAndForth.getRibbonState()){
//            System.out.println(s);
//        }
//        System.out.println("Indexi: "+BackAndForth.getIndex());
//        System.out.println("__________________________________________________________________");

        setButtons();

    }

    private void goForth(){

        String currentPage =BackAndForth.gotoForthPage();
        System.out.println("U Ekzekutu Forth Click");
        System.out.println("Faqja current: "+currentPage);
        if(currentPage !=null) {
            if(currentPage.contains("admin-Menu")) {
                char menuChar = currentPage.charAt(currentPage.length()-1);
                if(menuChar == 'S'){
                    SESSION.setAdminMenu("Student");
                } else if (menuChar == 'M') {
                    SESSION.setAdminMenu("Supervisor");
                }else if (menuChar == 'A') {
                    SESSION.setAdminMenu("Afat");
                }
                Navigatior.navigate(this.addPane, currentPage.substring(0, currentPage.length()-1));
            }else {
                Navigatior.navigate(this.addPane, currentPage);
            }
            System.out.println("Index:" + BackAndForth.getIndex());
        }

//        System.out.println("Anetaret E linked List");
//        System.out.println("__________________________________________________________________");
//        for(String s:BackAndForth.getRibbonState()){
//            System.out.println(s);
//        }
//        System.out.println("Indexi: "+BackAndForth.getIndex());
//        System.out.println("__________________________________________________________________");

        setButtons();
    }

    @FXML
    void handleForthClick(MouseEvent me) {
      this.goForth();

    }



    private void navigateAndSaveState(String page){
        if(page.contains("admin-Menu")) {
            Navigatior.navigate(this.addPane, page.substring(0, page.length() - 1));
        }else{
            Navigatior.navigate(this.addPane, page);
        }
        BackAndForth.addOnePage(page);

//        System.out.println("Anetaret E linked List");
//        System.out.println("__________________________________________________________________");
//        for(String s:BackAndForth.getRibbonState()){
//            System.out.println(s);
//            System.out.println(BackAndForth.getIndex());
//        }
//        System.out.println("__________________________________________________________________");
//        System.out.println("Indexi: "+BackAndForth.getIndex());

        setButtons();

    }

    private void setButtons() {
           if (BackAndForth.getIndex() <= 0) {
               this.imgGoBack.setImage(this.imageGoBackGray);
               this.imgGoBack.setDisable(true);
           } else {
               this.imgGoBack.setImage(this.imageGoBackWhite);
               this.imgGoBack.setDisable(false);
           }

           if (BackAndForth.getIndex() == BackAndForth.getMaxIndex()) {
               this.imgGoForward.setImage(this.imageGoForwardGray);
               this.imgGoForward.setDisable(true);
           } else {
               this.imgGoForward.setImage(this.imageGoForwardWhite);
               this.imgGoForward.setDisable(false);
           }
    }
}


