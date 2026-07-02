<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $npm = isset($_POST['npm']) ? trim($_POST['npm']) : '';
    
    if (!empty($npm)) {
        // Ambil semua mata kuliah yang BELUM ada di krs
        // dan (opsional) belum ada di khs (nilai A/B/C). Untuk simplifikasi, kita filter yang belum ada di KRS saja.
        $stmt = $conn->prepare("SELECT * FROM mata_kuliah WHERE kode_mk NOT IN (SELECT kode_mk FROM krs WHERE npm = ?)");
        $stmt->bind_param("s", $npm);
        $stmt->execute();
        $result = $stmt->get_result();
        
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $data[] = $row;
        }
        
        echo json_encode(["status" => true, "data" => $data]);
        $stmt->close();
    } else {
        echo json_encode(["status" => false, "message" => "NPM kosong"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Invalid Request"]);
}
?>
