<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $npm = isset($_POST['npm']) ? trim($_POST['npm']) : '';
    $action = isset($_POST['action']) ? trim($_POST['action']) : ''; // 'tambah' atau 'batal'
    $kode_mk = isset($_POST['kode_mk']) ? trim($_POST['kode_mk']) : '';

    if (!empty($npm) && !empty($action) && !empty($kode_mk)) {
        if ($action == 'tambah') {
            // Cek apakah mata kuliah ada di katalog
            $stmt = $conn->prepare("SELECT * FROM mata_kuliah WHERE kode_mk = ?");
            $stmt->bind_param("s", $kode_mk);
            $stmt->execute();
            $res = $stmt->get_result();
            if ($res->num_rows > 0) {
                $mk = $res->fetch_assoc();
                
                // Tambah ke KRS
                $stmt1 = $conn->prepare("INSERT INTO krs (npm, kode_mk, nama_mk, sks, dosen) VALUES (?, ?, ?, ?, ?)");
                $stmt1->bind_param("sssis", $npm, $kode_mk, $mk['nama_mk'], $mk['sks'], $mk['dosen']);
                $stmt1->execute();
                
                // Tambah ke Jadwal (Dummy schedule)
                $hari = "Senin";
                $jam = "08:00 - 10:00";
                $ruang = "Ruang A.101";
                $stmt2 = $conn->prepare("INSERT INTO tb_jadwal (npm, hari, jam, kode_mk, nama_mk, dosen, ruang) VALUES (?, ?, ?, ?, ?, ?, ?)");
                $stmt2->bind_param("sssssss", $npm, $hari, $jam, $kode_mk, $mk['nama_mk'], $mk['dosen'], $ruang);
                $stmt2->execute();
                
                // Tambah ke Absensi
                $stmt3 = $conn->prepare("INSERT INTO tb_absensi (npm, kode_mk, nama_mk, hadir, izin, sakit, alfa, total_pertemuan) VALUES (?, ?, ?, 0, 0, 0, 0, 14)");
                $stmt3->bind_param("sss", $npm, $kode_mk, $mk['nama_mk']);
                $stmt3->execute();

                echo json_encode(["status" => true, "message" => "Mata kuliah berhasil ditambahkan"]);
            } else {
                echo json_encode(["status" => false, "message" => "Mata kuliah tidak ditemukan"]);
            }
        } else if ($action == 'batal') {
            // Hapus dari KRS, Jadwal, Absensi
            $conn->query("DELETE FROM krs WHERE npm='$npm' AND kode_mk='$kode_mk'");
            $conn->query("DELETE FROM tb_jadwal WHERE npm='$npm' AND kode_mk='$kode_mk'");
            $conn->query("DELETE FROM tb_absensi WHERE npm='$npm' AND kode_mk='$kode_mk'");
            echo json_encode(["status" => true, "message" => "Mata kuliah berhasil dibatalkan"]);
        } else {
            echo json_encode(["status" => false, "message" => "Action tidak valid"]);
        }
    } else {
        echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Invalid Request"]);
}
?>
