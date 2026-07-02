<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $npm = isset($_GET['npm']) ? $_GET['npm'] : '';

    if(!empty($npm)){
        $stmt = $conn->prepare("SELECT * FROM khs WHERE npm=?");
        $stmt->bind_param("s", $npm);
        $stmt->execute();
        $result = $stmt->get_result();

        $khs = array();
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $khs[] = $row;
            }
            echo json_encode(array("status" => true, "data" => $khs));
        } else {
            echo json_encode(array("status" => false, "message" => "Data KHS tidak ditemukan"));
        }
    } else {
        echo json_encode(array("status" => false, "message" => "NPM tidak diberikan"));
    }
} else {
    echo json_encode(array("status" => false, "message" => "Invalid Request"));
}
?>
