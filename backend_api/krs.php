<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $npm = isset($_GET['npm']) ? $_GET['npm'] : '';

    if(!empty($npm)){
        $stmt = $conn->prepare("SELECT * FROM krs WHERE npm=?");
        $stmt->bind_param("s", $npm);
        $stmt->execute();
        $result = $stmt->get_result();

        $krs = array();
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $krs[] = $row;
            }
            echo json_encode(array("status" => true, "data" => $krs));
        } else {
            echo json_encode(array("status" => false, "message" => "Data KRS tidak ditemukan"));
        }
    } else {
        echo json_encode(array("status" => false, "message" => "NPM tidak diberikan"));
    }
} else {
    echo json_encode(array("status" => false, "message" => "Invalid Request"));
}
?>
