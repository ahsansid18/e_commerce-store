import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductAvailabilityPage {
    private Stage stage;
    private BorderPane root;
    private FlowPane productsContainer;
    private boolean darkMode = false;

    private static class Product {
        String name;
        double price;
        String imagePath;
        List<String> features;

        Product(String name, double price, String imagePath, List<String> features) {
            this.name = name;
            this.price = price;
            this.imagePath = imagePath;
            this.features = features;
        }
    }

    public ProductAvailabilityPage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Branded Shoes", 1200, "images/shoes.jpg", Arrays.asList("Water Proof", "Non-slip", "Best Quality")));
        products.add(new Product("iPhone 16 Pro", 800, "images/phone.jpg", Arrays.asList("OLED Display", "128GB Storage", "5G Support")));
        products.add(new Product("T-Shirt", 50, "images/tshirt.jpg", Arrays.asList("Half Sleeves", "Pure Cotton", "All Sizes")));
        products.add(new Product("Bags", 100, "images/bags.jpg", Arrays.asList("Durable", "Spacious", "Best for Athletes")));
        products.add(new Product("Chair", 60, "images/ft.jpg", Arrays.asList("Ergonomic", "Strong", "Comfortable")));
        products.add(new Product("Caps", 10, "images/cap.jpg", Arrays.asList("Adjustable", "Cool Design", "Unisex")));
        products.add(new Product("Kids Toys", 100, "images/toys.jpg", Arrays.asList("Safe", "Colorful", "Entertaining")));
        products.add(new Product("Wallet", 200, "images/wallet.jpg", Arrays.asList("Leather", "Compact", "Multi-pocket")));
        products.add(new Product("Winter Jacket", 100, "images/jacket.jpg", Arrays.asList("Warm", "Durable", "Stylish")));
        products.add(new Product("dishes", 250, "images/d.jpg", Arrays.asList("Complete Kit", "Safe", "Long-lasting")));
        

        productsContainer = new FlowPane(20, 20);
        productsContainer.setPadding(new Insets(20));
        productsContainer.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            VBox productCard = createProductCard(product);
            TranslateTransition tt = new TranslateTransition(Duration.millis(400 + i * 100), productCard);
            tt.setFromY(200);
            tt.setToY(0);
            tt.play();
            productsContainer.getChildren().add(productCard);
        }

        ScrollPane scrollPane = new ScrollPane(productsContainer);
        scrollPane.setFitToWidth(true);

        root = new BorderPane(scrollPane);
        applyBackgroundStyle();

        Button darkModeToggle = new Button("Dark Mode");
        darkModeToggle.setStyle("-fx-background-color: #2A7AE2; -fx-text-fill: white;");
        darkModeToggle.setOnAction(e -> {
            darkMode = !darkMode;
            show();  // Rebuild UI with new mode
        });

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: #2A7AE2; -fx-text-fill: white;");
        backButton.setOnAction(e -> new WelcomePage(stage).show());

        HBox topButtons = new HBox(10, backButton, darkModeToggle);
        topButtons.setPadding(new Insets(10));
        topButtons.setAlignment(Pos.CENTER_RIGHT);
        root.setTop(topButtons);

        Scene scene = new Scene(root, 1100, 600);
        stage.setTitle("Product Availability");
        stage.setScene(scene);
        stage.show();
    }

    private void applyBackgroundStyle() {
        if (darkMode) {
            root.setStyle("-fx-background-color: #1a1a1a;");
            productsContainer.setStyle("-fx-background-color: #1a1a1a;");
        } else {
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffffff, #f0f8ff);");
            productsContainer.setStyle("-fx-background-color: transparent;");
        }
    }

    private VBox createProductCard(Product product) {
        Image img;
        try {
            img = new Image(new File(product.imagePath).toURI().toString(), 220, 160, true, true);
        } catch (Exception e) {
            img = new Image("https://via.placeholder.com/220x160.png?text=No+Image");
        }

        ImageView imageView = new ImageView(img);

        Label nameLabel = new Label(product.name);
        Label priceLabel = new Label("$" + product.price + " / item");

        VBox featureBox = new VBox(5);
        for (String feature : product.features) {
            Label fLabel = new Label("• " + feature);
            featureBox.getChildren().add(fLabel);
        }

        Button orderBtn = new Button("Order Now");
        orderBtn.setStyle("""
            -fx-background-color: #2A7AE2;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 14px;
            -fx-cursor: hand;
        """);
        orderBtn.setOnAction(e -> new BookingFormPage(stage, product.name, product.price).show());

        VBox vbox = new VBox(10, imageView, nameLabel, priceLabel, featureBox, orderBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(12));

        if (darkMode) {
            vbox.setStyle("""
                -fx-border-color: #555;
                -fx-border-radius: 12;
                -fx-background-radius: 12;
                -fx-background-color: #2e2e2e;
                -fx-effect: dropshadow(gaussian, rgba(255,255,255,0.1), 6, 0.2, 2, 2);
            """);
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white;");
            priceLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
            for (javafx.scene.Node node : featureBox.getChildren()) {
                ((Label) node).setStyle("-fx-font-size: 13px; -fx-text-fill: #bbbbbb;");
            }
        } else {
            vbox.setStyle("""
                -fx-border-color: #ccc;
                -fx-border-radius: 12;
                -fx-background-radius: 12;
                -fx-background-color: white;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.2, 2, 2);
            """);
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
            priceLabel.setStyle("-fx-text-fill: #444; -fx-font-size: 14px;");
            for (javafx.scene.Node node : featureBox.getChildren()) {
                ((Label) node).setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
            }
        }

        return vbox;
    }
}








