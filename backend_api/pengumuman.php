<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $sql = "SELECT * FROM tb_pengumuman ORDER BY id DESC";
    $result = $conn->query($sql);

    $data = array();
    if ($result && $result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $data[] = $row;
        }
        echo json_encode(array("status" => true, "data" => $data));
    } else {
        echo json_encode(array("status" => false, "message" => "Data Pengumuman tidak ditemukan"));
    }
} else {
    echo json_encode(array("status" => false, "message" => "Invalid Request"));
}
?>
