import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.concurrent.Exchanger;

import Thread.ComplexThread;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 200, 230);
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100 / 4.0);
            gridpane.getColumnConstraints().add(column);
        }

        TextField xTextField = new TextField();
        xTextField.setMinWidth(35);
        xTextField.setMaxWidth(35);
        gridpane.add(xTextField, 2, 0);

        TextField yTextField = new TextField();
        yTextField.setMinWidth(35);
        yTextField.setMaxWidth(35);
        gridpane.add(yTextField, 2, 1);

        TextField nTextField = new TextField();
        nTextField.setMinWidth(35);
        nTextField.setMaxWidth(35);
        gridpane.add(nTextField, 2, 2);

        Label xLabel = new Label("   x = ");
        gridpane.add(xLabel, 1, 0);
        Label yLabel = new Label("   y = ");
        gridpane.add(yLabel, 1, 1);
        Label nLabel = new Label("   n = ");
        gridpane.add(nLabel, 1, 2);

        Button calculateButton = new Button("Calculate");
        gridpane.add(calculateButton, 1, 3, 3, 1);

        TextArea answer = new TextArea(" ");
        gridpane.add(answer, 0, 4, 4, 3);

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Double x = Double.parseDouble(xTextField.getText());
                Double y = Double.parseDouble(yTextField.getText());
                Integer n = Integer.parseInt(nTextField.getText());
                for (int k = 0; k < Double.parseDouble(nTextField.getText()); k++) {
                    String result = calculate(x, y, n, k);
                    answer.appendText(result);
                    answer.appendText("\n");
                }
            }
        });
        root.setCenter(gridpane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String calculate(Double x, Double y, Integer n, Integer k){
        Exchanger<String> exchanger = new Exchanger<>();
        ComplexThread calculator = new ComplexThread(x, y, n, k, exchanger);
        calculator.start();
        String result;
        try {
            result = exchanger.exchange("");
        } catch (Exception ex) {
            return "e r r o r";
        }
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
