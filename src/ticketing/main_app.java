
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package ticketing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.Timer;

public class main_app extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        validate();
        Parent root = FXMLLoader.load(getClass().getResource("/ticketing/fxml/login.fxml"));
        root.setOnMousePressed(
                (MouseEvent event) -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });
        root.setOnMouseDragged(
                (MouseEvent event) -> {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                });
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setFullScreen(false);
        stage.centerOnScreen();
        stage.show();
    }

    void validate() {
        try (Statement statement = ConnectionManager.getInstance().getConnection().createStatement()) {
//            statement.addBatch("delete from call_table where substr(fpdate,1,10)!=substr(now(),1,10)");
            statement.addBatch("insert into tblog (select * from ttable where substr(fpdate,1,10)!=substr(now(),1,10))");
            statement.addBatch("delete from ttable where substr(fpdate,1,10)!=substr(now(),1,10)");
            int[] executeBatch = statement.executeBatch();
            for (int count = 0; count < executeBatch.length; count++) {
                System.out.println("[" + count + "] :---: Execute Query Batch [" + executeBatch[count] + "]");
            }
            statement.close();
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
    }

    void setTime() {
        ActionListener taskPerformer = (ActionEvent e) -> {
            Date dateTime = new Date();
//            jLabel14.setText(DateFormat.getTimeInstance(DateFormat.DEFAULT).format(dateTime));
        };
        new Timer(1000, taskPerformer).start();
    }

}
