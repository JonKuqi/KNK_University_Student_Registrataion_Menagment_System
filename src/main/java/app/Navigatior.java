package app;

/* KOMENTIM I NAVIGATORIT

Kontrolleri i ribbonit edhe i menus duhet me pas ni pane qe me e mbush masane,
Navigatior.navigate(pane qe mbushet, edhe pane qe vjen) -> shembulli tek Admini qysh e kum ba

Metodat niher spi fshij amo besoj e ndreqi ni menyr qysh me u en kahmos.

*/

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import service.Animations.Tick;
import service.Animations.UpLogoAnimate;

import java.io.IOException;



interface OverallPages{
    public static String DASHBOARD = "";
    public static String ERROR404 = "overall-error404.fxml";

    public static final String cssForActiveSection = "-fx-background-color: #A5CEF2; -fx-background-radius:10px;";
    public static final String LOGIN = "overall-login.fxml";
    public static final String CHANGEPASSWORD = "changePassword.fxml";
}

interface AdminPages{
    public final static String ADMIN_RIBBON = "admin-ribbon.fxml";


    public final static String ADMIN_STUDENTMENU = "admin-studentMenu.fxml";
    public final static String ADMIN_STUDENTMENU_ADDSTUDENT = "admin-studentMenu-addStudent.fxml";

    public final static String ADMIN_STUDENTMENU_EDITSTUDENT = "admin-studentMenu-editStudent.fxml";
    public final static String ADMIN_PROFILE = "admin-profile.fxml";

    public final static String ADMIN_REGISTRATIONMENU = "admin-registrationPeriodMenu.fxml";
    public final static String ADMIN_REGISTRATIONMENU_ADDREGISTRATION = "admin-registrationPeriodMenu-addRegistration.fxml";


}
interface StudentPages{

}
interface SupervisorPages{
    public final static String SUPERVISOR_RIBBON = "supervisor-ribbon.fxml";
    public final static String SUPERVISOR_MENU = "supervisor-menu.fxml";
    public final static String SUPERVISOR_PROFILE = "supervisor-profile.fxml";
    public final static String ADMIN_SUPERVISORMENU = "admin-supervisorMenu.fxml";
    public final static String ADMIN_SUPERVISORMENU_ADDSUPERVISOR = "admin-supervisorMenu-addSupervisor.fxml";
    public final static String ADMIN_SUPERVISORMENU_EDITSUPERVISOR = "admin-supervisorMenu-editSupervisor.fxml";

}



public class Navigatior implements AdminPages, StudentPages, SupervisorPages, OverallPages{

    public static void navigate(Event event, String form){
        Node eventNode = (Node) event.getSource();

        Stage stage = (Stage) eventNode.getScene().getWindow();
        navigate(stage, form);
    }

    public static void navigate(Stage stage, String form){
        Pane formPane = loadPane(form);
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        Scene newScene = new Scene(formPane);
        stage.setScene(newScene);
        stage.show();
    }

    //Per kur klokohet ribboni, qe me e qu pane jo stringun
    public static void navigate(Stage stage, Pane mainPane){
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        Scene newScene = new Scene(mainPane);
        stage.setScene(newScene);
        stage.show();
    }

    //Shton Pane brenda panit aktual - BLENDI
    public static void navigate(Pane pane, String addedPane){
        Pane formPane = loadPane(addedPane);
        pane.getChildren().clear();
        pane.getChildren().add(formPane);
    }

    //Kthen Pane me pane te shtuar aktual
    public static Pane addPane(Pane pane, String addedPane){
        Pane formPane = loadPane(addedPane);
        //Nese e ka veq ni element(dmth ribonin) leje, nese ka ka shum fshije t fundit(tu e lan ribbonin)
        if(formPane.getChildren().size() > 1) {
         formPane.getChildren().remove(formPane.getChildren().size() - 1);
       }
        pane.getChildren().add(formPane);
        return pane;
    }

    private static Pane loadPane(String page){
        FXMLLoader loader = new FXMLLoader(
                Navigatior.class.getResource(page)
        );
        try {
            return loader.load();
        }catch (IOException ioe){
            System.out.println("Error ne load");
            try {
                StackPane pane = (new FXMLLoader(Navigatior.class.getResource(Navigatior.ERROR404))).load();
                pane.setMaxHeight(650);
                return pane;

            }catch(Exception e){
                System.out.println("Nuk u gjet pane Error 404");
                return null;
            }

        }
    }
    public static void closeStageAfterDelay(Event event, Duration delay) {
        Node eventNode = (Node) event.getSource();
        Stage stage = (Stage) eventNode.getScene().getWindow();
        Timeline timeline = new Timeline(new KeyFrame(delay, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        }));
        timeline.play();
    }


    public static void navigateNewStage(String page){
         Scene scene = new Scene(loadPane(page));
        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.show();

    }
    public static void tick(double dimension){
        Tick tick = new Tick(dimension);
        Scene scene = new Scene(tick,dimension,dimension);
        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        Timeline timeline2= new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              stage.close();
            }
        }));
        timeline2.play();
    }

    public static void loading(String message,boolean successful, String logoText){
        UpLogoAnimate pane = new UpLogoAnimate(100, logoText, 10, 10);
        pane.start();
        pane.setTranslateY(50);
        StackPane stackPane = new StackPane();

        Text text = new Text();
        text.setText(message);
        if (successful) {
            text.setStyle("-fx-font-weight: bold; -fx-font-size: 19; -fx-fill: green;");
        } else {
            text.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-fill: red;");
        }
        text.setTranslateY(110);
        StackPane stack = new StackPane(text);
        VBox mainPane = new VBox();
        mainPane.getChildren().addAll(stackPane,stack);

        stackPane.getChildren().add(pane);
        Scene scene = new Scene(mainPane,280,200);


        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3300), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        }));
        timeline.play();


    }



    //kur navigojmeNeMenu qojme Pane
    public static Pane getRibbonWithSection(String ribbon, Pane section){
        Pane mainPane = new VBox();

        Pane ribbonPane = loadPane(ribbon);
        ribbonPane.setMaxHeight(80);
        ribbonPane.setMinHeight(80);

        mainPane.getChildren().add(ribbonPane);
        if(mainPane.getChildren().size() > 1) {
            mainPane.getChildren().remove(mainPane.getChildren().size() - 1);
        }
        mainPane.getChildren().add(section);
        return mainPane;
    }

    //Navigon veq Ribbon
    public static void navigate(Event event, String ribbon, String section){
        Node eventNode = (Node) event.getSource();
        Stage stage = (Stage) eventNode.getScene().getWindow();
        Pane sectionPane = loadPane(section);
        navigate(stage, getRibbonWithSection(ribbon, sectionPane));
    }
    public static void navigate(Stage stage, String ribbon, String section){

        Pane sectionPane = loadPane(section);
        if(sectionPane == null){
            sectionPane = loadPane(Navigatior.ERROR404);
        }
        navigate(stage, getRibbonWithSection(ribbon, sectionPane));
    }

    //Navigon Menu(Ka edhe ribbon)
    public static void navigate(Event event, String ribbon, String menu, String section){
        Node eventNode = (Node) event.getSource();
        Stage stage = (Stage) eventNode.getScene().getWindow();

        Pane menuAndSection = new HBox();
        //Menu ne Hbox
        menuAndSection.getChildren().add(loadPane(menu));
        //Menu dhe section ne ribbon
        Pane mainPane = getRibbonWithSection(ribbon,addPane(menuAndSection, section));

        navigate(stage,mainPane);

    }
}

