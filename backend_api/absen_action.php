<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $npm = isset($_POST['npm']) ? trim($_POST['npm']) : '';
    $kode_mk = isset($_POST['kode_mk']) ? trim($_POST['kode_mk']) : '';
    $status = isset($_POST['status']) ? trim($_POST['status']) : '';

    $allowed_status = ['hadir', 'izin', 'sakit', 'alfa'];

    if (!empty($npm) && !empty($kode_mk) && in_array($status, $allowed_status)) {
        
        $stmt = $conn->prepare("UPDATE tb_absensi SET {$status} = {$status} + 1 WHERE npm = ? AND kode_mk = ?");
        $stmt->bind_param("ss", $npm, $kode_mk);
        
        if ($stmt->execute()) {
            if ($stmt->affected_rows > 0) {
                echo json_encode(["status" => true, "message" => "Berhasil mencatat $status"]);
            } else {
                echo json_encode(["status" => false, "message" => "Gagal absen, kelas tidak ditemukan"]);
            }
        } else {
            echo json_encode(["status" => false, "message" => "Terjadi kesalahan server"]);
        }
        $stmt->close();
    } else {
        echo json_encode(["status" => false, "message" => "Data tidak lengkap atau status tidak valid"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Invalid Request"]);
}
?>
