<?php
header("Content-Type: application/json");
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $nim = isset($_GET['nim']) ? $_GET['nim'] : '';

    if(!empty($nim)){
        $sql = "SELECT * FROM khs WHERE nim='$nim'";
        $result = $conn->query($sql);

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
        echo json_encode(array("status" => false, "message" => "NIM tidak diberikan"));
    }
} else {
    echo json_encode(array("status" => false, "message" => "Invalid Request"));
}
?>
