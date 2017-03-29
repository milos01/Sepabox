package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {
    public TextField duBox;
    public TextField sBox;
    public TextField vBox;
    public ToggleGroup group1;
    public ToggleGroup group2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Sepabox");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label mm = new Label("mm");
        Label mm2 = new Label("mm");
        Label mm3 = new Label("mm");

        Rectangle rec = new Rectangle(100,100);
        Image img = new Image("file:resources/image/dropbox_logo.png");
        rec.setFill(new ImagePattern(img));
        Label scenetitle = new Label("Sepabox");
        scenetitle.setStyle("-fx-padding: 0px 0px 120px 10px");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        grid.add(scenetitle, 1, 0);
        grid.add(rec, 1, 0);

        Label dBox = new Label("Dužina:");
        grid.add(dBox, 0, 1);
        grid.add(mm, 2, 1);

        duBox = new TextField();
        grid.add(duBox, 1, 1);

        Label pw = new Label("Širina:");
        grid.add(pw, 0, 2);
        grid.add(mm2, 2, 2);

        sBox = new TextField();
        grid.add(sBox, 1, 2);

        Label height = new Label("Visina:");
        grid.add(height, 0, 3);
        grid.add(mm3, 2, 3);

        vBox = new TextField();
        grid.add(vBox, 1, 3);

        group1 = new ToggleGroup();
        group2 = new ToggleGroup();


        RadioButton rb1 = new RadioButton("Spoljašnja mera");
        rb1.setToggleGroup(group1);
        rb1.setStyle("-fx-padding: 23px 0px 0px 0px");
        rb1.setUserData("sm");
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Unutrašnja mera");
        rb2.setToggleGroup(group1);
        rb2.setUserData("um");

        grid.add(rb1, 0, 4);
        grid.add(rb2, 0, 5);

        RadioButton rb11 = new RadioButton("Troslojna");
        rb11.setToggleGroup(group2);
        rb11.setStyle("-fx-padding: 13px 0px 0px 0px;");
        rb11.setUserData("tk");
        rb11.setSelected(true);

        RadioButton rb22 = new RadioButton("Petoslojna");
        rb22.setToggleGroup(group2);
        rb22.setUserData("pk");

        grid.add(rb11, 0, 6);
        grid.add(rb22, 0, 7);

        Button btn = new Button("Izračunaj");
        HBox hbBtn = new HBox(20);
        hbBtn.setStyle("-fx-padding: 20px 0px 0px 0px");
        btn.setOnAction(this::buttonClick);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 8);




        Scene scene = new Scene(grid, 400, 520);
        primaryStage.setScene(scene);





        primaryStage.show();
    }

    private void buttonClick(ActionEvent e) {
        String mesures = group1.getSelectedToggle().getUserData().toString();
        String material = group2.getSelectedToggle().getUserData().toString();

       try{
           int duBoxVal = Integer.parseInt(duBox.getText());
           int sBoxVal = Integer.parseInt(sBox.getText());
           double vBoxVal = Integer.parseInt(vBox.getText());

           ArrayList<Double> mes = lenHeightSelector(mesures, material, duBoxVal, sBoxVal, vBoxVal);

           Rectangle r = new Rectangle(300,175);

           r.setFill(Color.WHITE);
           r.setStroke(Color.BLACK);
           GridPane grid = new GridPane();
           grid.setAlignment(Pos.CENTER);
           grid.add(r,1,1);

           Label pw0 = new Label("Dimenzije ploče");
           pw0.setFont(Font.font("Tahoma", FontWeight.NORMAL, 17));
           pw0.setStyle("-fx-padding: 0px 0px 10px 00px;");
           grid.add(pw0, 1, 0);

           Label pw1 = new Label("Dužina: " + mes.get(0)+"mm");
           pw1.setStyle("-fx-padding: 10px 0px 0px 100px;");


           grid.add(pw1, 1, 2);
           Label pw2 = new Label("Širina: " + mes.get(1)+"mm");
           grid.add(pw2, 2, 1);
           pw2.setStyle("-fx-padding: 0px 0px 0px 10px;");
           Stage s = new Stage();
           Scene sc = new Scene(grid,500, 300);
           s.setScene(sc);
           s.setResizable(false);
           s.show();
       }catch (Exception error){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Greška pri unosu podataka");
           alert.setContentText("Vredosti moraju biti numeričke i sva polja moraju biti popunjena!");

           alert.showAndWait();
       }




    }

    private ArrayList<Double> lenHeightSelector(String mesures, String material, int duBoxVal, int sBoxVal, double vBoxVal){
        ArrayList<Double> list = new ArrayList<Double>();
        if (mesures == "sm" && material == "tk") {
            list.clear();
            double length = length(duBoxVal, sBoxVal, 40);
            double height = height(sBoxVal, vBoxVal,4);
            list.add(length);
            list.add(height);
        } else if (mesures == "um" && material == "tk") {
            list.clear();
            double length = length(duBoxVal, sBoxVal, 60);
            vBoxVal = innerMeasure("tk", vBoxVal);
            double height = height(sBoxVal, vBoxVal,4);
            list.add(length);
            list.add(height);

        } else if (mesures == "sm" && material == "pk") {
            double length = length(duBoxVal, sBoxVal, 40);
            double height = height(sBoxVal, vBoxVal,6);
            list.add(length);
            list.add(height);
        } else {
            double length = length(duBoxVal, sBoxVal, 60);
            vBoxVal = innerMeasure("pk", vBoxVal);
            double height = height(sBoxVal, vBoxVal,6);
            list.add(length);
            list.add(height);
        }
        return list;
    }

    private double innerMeasure(String material, double vBoxVal){
        if (material == "tk"){
            vBoxVal = vBoxVal + (1.5 * 4);
        }else{
            vBoxVal = vBoxVal + (1.5 * 6);
        }

        return vBoxVal;
    }

    private double length(int lBox, double wBox, int tail) {
        double len = (lBox + wBox) * 2 + tail;
        return len;
    }

    private double height(int wBox, double hBox, int gap) {
        double height = wBox + hBox + gap;
        return height;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
