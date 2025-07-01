<?php
$servername = "localhost";
$username = "root";
$password = "";  // or your actual password
$dbname = "new_ecommerce";
$port = 3307;   // specify your changed port here

$conn = new mysqli($servername, $username, $password, $dbname, $port);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>
