<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: *");
header("Content-Type: application/json");

include("db.php");

// Get and decode the incoming JSON
$input = file_get_contents("php://input");
$data = json_decode($input);

// Check if JSON is valid
if (!$data) {
    echo json_encode(["status" => "error", "message" => "Invalid or no JSON input received"]);
    exit;
}

// Extract values safely
$name = $data->customer_name ?? '';
$address = $data->address ?? '';
$product_name = $data->product_name ?? '';
$quantity = $data->quantity ?? 0;
$price_per_item = $data->price_per_item ?? 0;
$total_amount = $data->total_amount ?? 0;

// Validate required fields
if (empty($name) || empty($address) || empty($product_name)) {
    echo json_encode(["status" => "error", "message" => "Missing required fields"]);
    exit;
}

// Insert into the database
$sql = "INSERT INTO orders (customer_name, address, product_name, quantity, price_per_item, total_amount)
        VALUES ('$name', '$address', '$product_name', $quantity, $price_per_item, $total_amount)";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error", "message" => $conn->error]);
}
?>

