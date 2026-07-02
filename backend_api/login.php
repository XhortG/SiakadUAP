<?php
header('Content-Type: application/json');
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Fix: Gunakan prepared statement untuk cegah SQL Injection
    $npm = isset($_POST['npm']) ? trim($_POST['npm']) : '';
    $password = isset($_POST['password']) ? trim($_POST['password']) : '';

    if (!empty($npm) && !empty($password)) {
        // Prepared statement: aman dari SQL Injection
        $stmt = $conn->prepare(
            "SELECT id, username, email, nama_lengkap, npm, prodi, password, semester, fakultas, tahun_masuk, status_mahasiswa 
             FROM users 
             WHERE npm = ? 
             LIMIT 1"
        );
        $stmt->bind_param("s", $npm);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            $user = $result->fetch_assoc();

            $storedPassword = $user['password'];
            $isValid = ($storedPassword === $password);

            if ($isValid) {
                // Hapus password dari respons sebelum dikirim
                unset($user['password']);
                echo json_encode([
                    "status"  => true,
                    "message" => "Login Berhasil",
                    "data"    => $user
                ]);
            } else {
                echo json_encode(["status" => false, "message" => "NPM atau Password salah"]);
            }
        } else {
            echo json_encode(["status" => false, "message" => "NPM atau Password salah"]);
        }

        $stmt->close();
    } else {
        echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Invalid Request"]);
}
?>
