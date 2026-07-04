<?php
$host = "localhost";
$user = "root";
$pass = "";
$db = "db_siakad";

$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    http_response_code(500);
    die(json_encode(array("status" => false, "message" => "Server Maintenance")));
}
?>
