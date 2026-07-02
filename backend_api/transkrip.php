<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $npm = isset($_POST['npm']) ? trim($_POST['npm']) : '';

    if (!empty($npm)) {
        // Mengambil semua data KHS, diurutkan berdasarkan semester
        $stmt = $conn->prepare("SELECT * FROM khs WHERE npm = ? ORDER BY semester ASC");
        $stmt->bind_param("s", $npm);
        $stmt->execute();
        $result = $stmt->get_result();

        $data = [];
        $totalSks = 0;
        $totalBobot = 0;

        while ($row = $result->fetch_assoc()) {
            $data[] = $row;
            $sks = (int) $row['sks'];
            $nilai_huruf = $row['nilai'];
            
            // Konversi nilai huruf ke angka
            $angka = 0;
            if ($nilai_huruf == 'A') $angka = 4.0;
            else if ($nilai_huruf == 'A-') $angka = 3.7;
            else if ($nilai_huruf == 'B+') $angka = 3.3;
            else if ($nilai_huruf == 'B') $angka = 3.0;
            else if ($nilai_huruf == 'B-') $angka = 2.7;
            else if ($nilai_huruf == 'C+') $angka = 2.3;
            else if ($nilai_huruf == 'C') $angka = 2.0;
            else if ($nilai_huruf == 'D') $angka = 1.0;

            $totalSks += $sks;
            $totalBobot += ($sks * $angka);
        }

        $ipk = 0;
        if ($totalSks > 0) {
            $ipk = $totalBobot / $totalSks;
        }

        echo json_encode([
            "status" => true, 
            "data" => $data, 
            "total_sks" => $totalSks,
            "ipk" => round($ipk, 2)
        ]);
        $stmt->close();
    } else {
        echo json_encode(["status" => false, "message" => "NPM kosong"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Invalid Request"]);
}
?>
