<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $npm = isset($_GET['npm']) ? $_GET['npm'] : '';

    if(!empty($npm)){
        $stmt = $conn->prepare("SELECT * FROM tb_riwayat WHERE npm=? ORDER BY id DESC");
        $stmt->bind_param("s", $npm);
        $stmt->execute();
        $result = $stmt->get_result();

        $data = array();
        if ($result && $result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $data[] = $row;
            }
            echo json_encode(array("status" => true, "data" => $data));
        } else {
            echo json_encode(array("status" => false, "message" => "Data Riwayat tidak ditemukan"));
        }
        $stmt->close();
    } else {
        echo json_encode(array("status" => false, "message" => "NPM tidak diberikan"));
    }
} else {
    echo json_encode(array("status" => false, "message" => "Invalid Request"));
}
?>
