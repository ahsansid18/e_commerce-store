import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookingFormPage {

    private Stage stage;
    private String productName;
    private double pricePerItem;

    public BookingFormPage(Stage stage, String productName, double pricePerItem) {
        this.stage = stage;
        this.productName = productName;
        this.pricePerItem = pricePerItem;
    }

    public void show() {
        stage.setTitle("Order Form - " + productName);

        Label productLabel = new Label("Product: " + productName);
        Label priceLabel = new Label("Price per item: $" + pricePerItem);

        Label nameLabel = new Label("Customer Name:");
        TextField nameField = new TextField();

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label messageLabel = new Label();

        Button backBtn = new Button("Back");
        Button submitBtn = new Button("Submit Order");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(productLabel, 0, 0);
        grid.add(priceLabel, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(addressLabel, 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(quantityLabel, 0, 3);
        grid.add(quantityField, 1, 3);

        HBox buttonBox = new HBox(10, backBtn, submitBtn);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 4);
        grid.add(messageLabel, 1, 5);

        submitBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String quantityStr = quantityField.getText().trim();

            if (name.isEmpty() || address.isEmpty() || quantityStr.isEmpty()) {
                messageLabel.setText("Please fill all fields.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    messageLabel.setText("Enter a positive quantity.");
                    return;
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Quantity must be a valid number.");
                return;
            }

            boolean success = sendOrderToServer(name, address, productName, quantity, pricePerItem);

            if (success) {
                messageLabel.setText("Order placed successfully!");
                nameField.clear();
                addressField.clear();
                quantityField.clear();
            } else {
                messageLabel.setText("Order failed. Try again.");
            }
        });

        backBtn.setOnAction(e -> new ProductAvailabilityPage(stage).show());

        stage.setScene(new Scene(grid, 450, 300));
        stage.show();
    }

    private boolean sendOrderToServer(String name, String address, String productName, int quantity, double price) {
        try {
            URL url = new URL("http://localhost/ProductManagementBackend/add_order.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            double total = quantity * price;

            String jsonInputString = String.format("""
                {
                    "customer_name": "%s",
                    "address": "%s",
                    "product_name": "%s",
                    "quantity": %d,
                    "price_per_item": %.2f,
                    "total_amount": %.2f
                }
                """, name, address, productName, quantity, price, total);

            // ✅ Debug JSON being sent (optional)
            System.out.println("Sending JSON:");
            System.out.println(jsonInputString);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            if (code == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) response.append(line.trim());
                    System.out.println("Server Response: " + response); // Optional
                    return true;
                }
            } else {
                System.err.println("HTTP error code: " + code);
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

