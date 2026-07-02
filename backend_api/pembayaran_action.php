<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $npm = isset($_POST['npm']) ? trim($_POST['npm']) : '';
    $semester = isset($_POST['semester']) ? trim($_POST['semester']) : '';

    if (!empty($npm) && !empty($semester)) {
        // Simulasi Pembayaran: Ubah status jadi Lunas dan set tanggal
        $tanggal = date("d F Y"); // e.g., 02 July 2026
        
        $stmt = $conn->prepare("UPDATE tb_pembayaran SET status = 'Lunas', tanggal_bayar = ? WHERE npm = ? AND semester = ?");
        $stmt->bind_param("sss", $tanggal, $npm, $semester);
        
        if ($stmt->execute()) {
            if ($stmt->affected_rows > 0) {
                echo json_encode(["status" => true, "message" => "Pembayaran berhasil diverifikasi"]);
            } else {
                echo json_encode(["status" => false, "message" => "Gagal bayar, tagihan tidak ditemukan"]);
            }
        } else {
            echo json_encode(["status" => false, "message" => "Terjadi kesalahan server"]);
        }
        $stmt->close();
    } else {
        echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Invalid Request"]);
}
?>
