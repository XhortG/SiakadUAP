<?php
$host = "localhost";
$user = "root";
$pass = "";
$db = "db_siakad";

$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die(json_encode(array("status" => false, "message" => "Connection failed: " . $conn->connect_error)));
}
?>
