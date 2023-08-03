import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Currency_Converter extends Application {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
    private Map<String, Double> exchangeRates = new HashMap<>();

    //main method
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        //background 
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label amountLabel = new Label("Amount:");
        TextField amountTextField = new TextField();
        Label fromCurrencyLabel = new Label("From:");
        ComboBox<String> fromCurrencyComboBox = new ComboBox<>();
        fromCurrencyComboBox.getItems().addAll("INR", "USD", "JPY", "GBP", "EUR");
        Label toCurrencyLabel = new Label("To:");
        ComboBox<String> toCurrencyComboBox = new ComboBox<>();
        toCurrencyComboBox.getItems().addAll("INR", "USD", "JPY", "GBP", "EUR");
        Label resultLabel = new Label("Result:");
        TextField resultTextField = new TextField();
        resultTextField.setEditable(false);

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> convertCurrency(amountTextField, fromCurrencyComboBox,
                toCurrencyComboBox, resultTextField));

        grid.add(amountLabel, 0, 0);
        grid.add(amountTextField, 1, 0);
        grid.add(fromCurrencyLabel, 0, 1);
        grid.add(fromCurrencyComboBox, 1, 1);
        grid.add(toCurrencyLabel, 0, 2);
        grid.add(toCurrencyComboBox, 1, 2);
        grid.add(resultLabel, 0, 3);
        grid.add(resultTextField, 1, 3);
        grid.add(convertButton, 1, 4);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void convertCurrency(TextField amountTextField, ComboBox<String> fromCurrencyComboBox, ComboBox<String> toCurrencyComboBox, TextField resultTextField) {
        String fromCurrency = fromCurrencyComboBox.getValue();
        String toCurrency = toCurrencyComboBox.getValue();
        Double amount = Double.parseDouble(amountTextField.getText());

        if (fromCurrency != null && toCurrency != null) {
            double rateFrom = exchangeRates.get(fromCurrency);
            double rateTo = exchangeRates.get(toCurrency);
            double result = (amount / rateFrom) * rateTo;
            resultTextField.setText(DECIMAL_FORMAT.format(result));
        } else {
            resultTextField.setText("");
        }
    }

    @Override
    public void init() {
        // Exchange rates between currencies
        exchangeRates.put("INR", 1.0);
        exchangeRates.put("USD", 0.014); // 1 USD = 71.43 INR (as of the example)
        exchangeRates.put("JPY", 1.52); // 1 JPY = 1.52 INR (as of the example)
        exchangeRates.put("GBP", 0.012); // 1 GBP = 83.33 INR (as of the example)
        exchangeRates.put("EUR", 0.012); // 1 EUR = 83.33 INR (as of the example)
    }
}
