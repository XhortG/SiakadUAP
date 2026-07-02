<?php
header("Content-Type: application/json");
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $sql = "SELECT * FROM berita ORDER BY tanggal DESC";
    $result = $conn->query($sql);

    $berita = array();
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $berita[] = $row;
        }
        echo json_encode(array("status" => true, "data" => $berita));
    } else {
        echo json_encode(array("status" => false, "message" => "Data tidak ditemukan"));
    }
} else {
    echo json_encode(array("status" => false, "message" => "Invalid Request"));
}
?>
