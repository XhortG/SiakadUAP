<?php
header("Content-Type: application/json");
require_once 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Fix: Gunakan prepared statement untuk cegah SQL Injection
    $email    = isset($_POST['email'])    ? trim($_POST['email'])    : '';
    $password = isset($_POST['password']) ? trim($_POST['password']) : '';

    if (!empty($email) && !empty($password)) {
        // Prepared statement: aman dari SQL Injection
        $stmt = $conn->prepare(
            "SELECT id, username, email, nama_lengkap, nim, prodi, password 
             FROM users 
             WHERE email = ? OR username = ? 
             LIMIT 1"
        );
        $stmt->bind_param("ss", $email, $email);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            $user = $result->fetch_assoc();

            // Fix: Dukung password MD5 (lama) maupun password_hash (baru)
            $storedPassword = $user['password'];
            $isValid = false;

            if (strlen($storedPassword) === 32) {
                // Password lama: MD5 (sesuai materi)
                $isValid = ($storedPassword === md5($password));
            } else {
                // Password baru: password_hash (lebih aman)
                $isValid = password_verify($password, $storedPassword);
            }

            if ($isValid) {
                // Hapus password dari respons sebelum dikirim
                unset($user['password']);
                echo json_encode([
                    "status"  => true,
                    "message" => "Login Berhasil",
                    "data"    => $user
                ]);
            } else {
                echo json_encode(["status" => false, "message" => "Email/Username atau Password salah"]);
            }
        } else {
            echo json_encode(["status" => false, "message" => "Email/Username atau Password salah"]);
        }

        $stmt->close();
    } else {
        echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Invalid Request"]);
}
?>
