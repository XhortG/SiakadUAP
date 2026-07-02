<?php
require_once 'koneksi.php';

$npm = '230201053';
$username = 'ilham_arifin';
$nama = 'Ilham Arifin';
$email = 'ilham@mhs.uap.ac.id';
$prodi = 'S1 Informatika';
$password = 'mahasiswauap13';

$semester = '6';
$fakultas = 'Fakultas Teknologi dan Informatika';
$tahun_masuk = '2023';
$status_mahasiswa = 'Aktif';

// 1. Insert/Update User
$check = $conn->query("SELECT * FROM users WHERE npm='$npm'");
if ($check->num_rows == 0) {
    $conn->query("INSERT INTO users (username, npm, password, nama_lengkap, email, prodi, semester, fakultas, tahun_masuk, status_mahasiswa) VALUES ('$username', '$npm', '$password', '$nama', '$email', '$prodi', '$semester', '$fakultas', '$tahun_masuk', '$status_mahasiswa')");
} else {
    $conn->query("UPDATE users SET password='$password', nama_lengkap='$nama', email='$email', prodi='$prodi', username='$username', semester='$semester', fakultas='$fakultas', tahun_masuk='$tahun_masuk', status_mahasiswa='$status_mahasiswa' WHERE npm='$npm'");
}

// Clear old data for this user
$conn->query("DELETE FROM tb_jadwal WHERE npm='$npm'");
$conn->query("DELETE FROM tb_absensi WHERE npm='$npm'");
$conn->query("DELETE FROM krs WHERE npm='$npm'");
$conn->query("DELETE FROM khs WHERE npm='$npm'");
$conn->query("DELETE FROM tb_pembayaran WHERE npm='$npm'");
$conn->query("DELETE FROM tb_riwayat WHERE npm='$npm'");

// We will use the provided real data from the image to populate the student's Academic Data.

// 2. Insert Jadwal (Semester Aktif)
// Format: Hari, Jam, Kode MK, Nama MK, Dosen, Ruang
$jadwal = [
    ['Senin', '08:00 - 10:30', 'TIF.502', 'Web Development', 'Dr. Ir. Zulkifli, S.T., M.Kom', 'Lab Komputer 1'],
    ['Senin', '13:00 - 15:30', 'FTI1704', 'Mobile Computing', 'Ratnasari,S.Kom.,M.Kom', 'Lab Komputer 2'],
    ['Selasa', '09:00 - 11:30', 'TIF.503', 'Augmented Reality', 'Agustinus Eko Setiawan, S.Kom., M.Kom', 'Ruang B.201'],
    ['Rabu', '10:00 - 12:30', 'TIF.505', 'Cloud Computing', 'Yuri Fitrian, M. Si', 'Ruang B.203']
];
foreach($jadwal as $j) {
    $conn->query("INSERT INTO tb_jadwal (npm, hari, jam, kode_mk, nama_mk, dosen, ruang) VALUES ('$npm', '{$j[0]}', '{$j[1]}', '{$j[2]}', '{$j[3]}', '{$j[4]}', '{$j[5]}')");
}

// 3. Insert Absensi (Sesuai Jadwal)
// Format: Kode MK, Nama MK, hadir, izin, sakit, alfa, total_pertemuan
$absensi = [
    ['TIF.502', 'Web Development', 12, 1, 0, 1, 14],
    ['FTI1704', 'Mobile Computing', 14, 0, 0, 0, 14],
    ['TIF.503', 'Augmented Reality', 10, 2, 2, 0, 14],
    ['TIF.505', 'Cloud Computing', 13, 1, 0, 0, 14]
];
foreach($absensi as $a) {
    $conn->query("INSERT INTO tb_absensi (npm, kode_mk, nama_mk, hadir, izin, sakit, alfa, total_pertemuan) VALUES ('$npm', '{$a[0]}', '{$a[1]}', {$a[2]}, {$a[3]}, {$a[4]}, {$a[5]}, {$a[6]})");
}

// 4. Insert KRS (Mata Kuliah Semester Ini)
// Format: Kode MK, Nama MK, SKS, Dosen
$krs = [
    ['TIF.502', 'Web Development', 4, 'Dr. Ir. Zulkifli, S.T., M.Kom'],
    ['FTI1704', 'Mobile Computing', 4, 'Ratnasari,S.Kom.,M.Kom'],
    ['TIF.503', 'Augmented Reality', 3, 'Agustinus Eko Setiawan, S.Kom., M.Kom'],
    ['TIF.505', 'Cloud Computing', 3, 'Yuri Fitrian, M. Si'],
    ['TIF.501', 'Metode Penelitian', 2, 'Yuri Fitrian, M. Si'],
    ['UAP.005', 'Technopreneurship', 2, 'Agustinus Eko Setiawan, S.Kom., M.Kom']
];
foreach($krs as $k) {
    $conn->query("INSERT INTO krs (npm, kode_mk, nama_mk, sks, dosen) VALUES ('$npm', '{$k[0]}', '{$k[1]}', {$k[2]}, '{$k[3]}')");
}

// 5. Insert Mata Kuliah (Katalog)
// Format: Kode MK, Nama MK, SKS, Dosen, Semester Tawar
$conn->query("TRUNCATE TABLE mata_kuliah");
$mata_kuliah = [
    ['TIF.502', 'Web Development', 4, 'Dr. Ir. Zulkifli, S.T., M.Kom', 6],
    ['FTI1704', 'Mobile Computing', 4, 'Ratnasari,S.Kom.,M.Kom', 6],
    ['TIF.503', 'Augmented Reality', 3, 'Agustinus Eko Setiawan, S.Kom., M.Kom', 6],
    ['TIF.505', 'Cloud Computing', 3, 'Yuri Fitrian, M. Si', 6],
    ['TIF.501', 'Metode Penelitian', 2, 'Yuri Fitrian, M. Si', 6],
    ['UAP.005', 'Technopreneurship', 2, 'Agustinus Eko Setiawan, S.Kom., M.Kom', 6],
    ['TIF.601', 'Data Mining', 3, 'Dr. Ir. Zulkifli, S.T., M.Kom', 6],
    ['TIF.602', 'Kecerdasan Buatan', 3, 'Ratnasari,S.Kom.,M.Kom', 6],
    ['TIF.603', 'Keamanan Jaringan', 3, 'Agustinus Eko Setiawan, S.Kom., M.Kom', 6],
    ['TIF.604', 'Pengolahan Citra Digital', 3, 'Yuri Fitrian, M. Si', 6]
];
foreach($mata_kuliah as $m) {
    $conn->query("INSERT INTO mata_kuliah (kode_mk, nama_mk, sks, dosen, semester_tawar) VALUES ('{$m[0]}', '{$m[1]}', {$m[2]}, '{$m[3]}', {$m[4]})");
}

// 6. Insert KHS (Mata Kuliah Semester Lalu dengan Field Semester)
// Format: Kode MK, Nama MK, SKS, Nilai, Semester
$khs = [
    ['TIF.101', 'Kalkulus I', 2, 'B+', 1],
    ['TIF.104', 'Dasar Pemrograman', 4, 'A', 1],
    ['MKD.902', 'Pancasila', 2, 'A', 1],
    ['TIF.202', 'Basis Data', 4, 'A-', 2],
    ['TIF.204', 'Desain dan Analisis Algoritma', 4, 'B', 2],
    ['TIF 301', 'Pemrograman Berorientasi Objek', 4, 'A', 3],
    ['TIF 303', 'Sistem Operasi', 3, 'B+', 3],
    ['TIF 305', 'Jaringan Komputer', 4, 'A', 4],
    ['TIF.401', 'Rekayasa Perangkat Lunak', 4, 'A', 4],
    ['TIF.509', 'Sistem Informasi Geografis', 3, 'B+', 5]
];
foreach($khs as $k) {
    $conn->query("INSERT INTO khs (npm, kode_mk, nama_mk, sks, nilai, semester) VALUES ('$npm', '{$k[0]}', '{$k[1]}', {$k[2]}, '{$k[3]}', {$k[4]})");
}

// 6. Insert Pembayaran
$pembayaran = [
    ['Ganjil 2025/2026', 4500000, 'Lunas', '15 Agustus 2025'],
    ['Genap 2024/2025', 4500000, 'Lunas', '10 Februari 2025'],
    ['Ganjil 2026/2027', 4800000, 'Belum Lunas', '-']
];
foreach($pembayaran as $p) {
    $conn->query("INSERT INTO tb_pembayaran (npm, semester, tagihan, status, tanggal_bayar) VALUES ('$npm', '{$p[0]}', {$p[1]}, '{$p[2]}', '{$p[3]}')");
}

// 7. Insert Riwayat
$riwayat = [
    ['KRS Disetujui', 'KRS Semester Ganjil 2025/2026 telah disetujui oleh Dosen Wali.', '16 Agustus 2025 09:00', 'Akademik'],
    ['Pembayaran UKT Diterima', 'Pembayaran UKT Semester Ganjil 2025/2026 diverifikasi.', '15 Agustus 2025 14:30', 'Keuangan']
];
foreach($riwayat as $r) {
    $conn->query("INSERT INTO tb_riwayat (npm, aktivitas, deskripsi, waktu, jenis) VALUES ('$npm', '{$r[0]}', '{$r[1]}', '{$r[2]}', '{$r[3]}')");
}

// 8. Insert Pengumuman (Global)
$conn->query("TRUNCATE TABLE tb_pengumuman");
$pengumuman = [
    ['Jadwal Ujian Akhir Semester', 'UAS akan dilaksanakan mulai tanggal 15 Desember 2026. Persiapkan diri Anda.', '2026-11-20'],
    ['Batas Pengisian KRS', 'Diberitahukan kepada seluruh mahasiswa bahwa batas akhir pengisian KRS adalah tanggal 20 Agustus.', '2026-08-01']
];
foreach($pengumuman as $p) {
    $conn->query("INSERT INTO tb_pengumuman (judul, isi, tanggal) VALUES ('{$p[0]}', '{$p[1]}', '{$p[2]}')");
}

// 9. Insert Berita (Global)
$conn->query("TRUNCATE TABLE berita");
$berita = [
    ['Tim Robotik UAP Juara 1', 'Tim Robotik Universitas Aisyah Pringsewu berhasil meraih juara 1 pada kontes robot tingkat nasional.', '2026-06-15'],
    ['Seminar Nasional Teknologi', 'Fakultas Teknologi dan Informatika akan mengadakan seminar nasional bertema AI untuk Kesehatan.', '2026-06-20']
];
foreach($berita as $b) {
    $conn->query("INSERT INTO berita (judul, isi, tanggal) VALUES ('{$b[0]}', '{$b[1]}', '{$b[2]}')");
}

echo "Database seeded successfully with real data from image for NPM: $npm";
?>
