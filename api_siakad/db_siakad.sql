-- Database: `db_siakad`
CREATE DATABASE IF NOT EXISTS `db_siakad`;
USE `db_siakad`;

-- Table structure for table `users`
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `email` varchar(100) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `nim` varchar(20) NOT NULL,
  `prodi` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table `users`
INSERT INTO `users` (`username`, `email`, `password`, `nama_lengkap`, `nim`, `prodi`) VALUES
('admin', 'admin@uap.ac.id', '21232f297a57a5a743894a0e4a801fc3', 'Administrator Siakad', '000000', 'Sistem Informasi'),
('mahasiswa1', 'mahasiswa1@uap.ac.id', 'e10adc3949ba59abbe56e057f20f883e', 'Budi Santoso', '19010001', 'Teknik Informatika'),
('mahasiswa2', 'mahasiswa2@uap.ac.id', 'e10adc3949ba59abbe56e057f20f883e', 'Siti Aminah', '19010002', 'Keperawatan'),
('mahasiswa3', 'mahasiswa3@uap.ac.id', 'e10adc3949ba59abbe56e057f20f883e', 'Andi Dharma', '19010003', 'Sistem Informasi'),
('mahasiswa4', 'mahasiswa4@uap.ac.id', 'e10adc3949ba59abbe56e057f20f883e', 'Rina Kartika', '19010004', 'Farmasi');

-- Table structure for table `berita`
CREATE TABLE IF NOT EXISTS `berita` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judul` varchar(255) NOT NULL,
  `isi` text NOT NULL,
  `tanggal` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `berita` (`judul`, `isi`, `tanggal`) VALUES
('Pengumuman UTS Semester Ganjil', 'Ujian Tengah Semester Ganjil akan dilaksanakan mulai tanggal 15 Oktober 2026. Seluruh mahasiswa diwajibkan mencetak kartu ujian.', '2026-10-01'),
('Beasiswa Berprestasi UAP', 'Pendaftaran beasiswa berprestasi telah dibuka, silakan hubungi bagian kemahasiswaan atau unduh formulir di web resmi.', '2026-09-20'),
('Libur Nasional dan Cuti Bersama', 'Sehubungan dengan hari raya, kegiatan belajar mengajar diliburkan selama 3 hari dari tanggal 12 hingga 14 November.', '2026-11-05'),
('Seminar Nasional Teknologi Kesehatan', 'Fakultas Kesehatan akan mengadakan seminar nasional pada 25 November. Mahasiswa diharap mendaftar segera karena kuota terbatas.', '2026-11-10'),
('Pemeliharaan Server Siakad', 'Server Siakad akan mengalami downtime sementara pada hari Sabtu, 28 November pukul 00:00 - 04:00 WIB untuk pemeliharaan.', '2026-11-20'),
('Pendaftaran KKN Gelombang II', 'Pendaftaran Kuliah Kerja Nyata (KKN) Gelombang II telah dibuka. Syarat minimal 110 SKS.', '2026-12-01');

-- Table structure for table `krs`
CREATE TABLE IF NOT EXISTS `krs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nim` varchar(20) NOT NULL,
  `kode_mk` varchar(20) NOT NULL,
  `nama_mk` varchar(100) NOT NULL,
  `sks` int(11) NOT NULL,
  `dosen` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `krs` (`nim`, `kode_mk`, `nama_mk`, `sks`, `dosen`) VALUES
('19010001', 'MK001', 'Pemrograman Web', 3, 'Dr. Budi Darmawan, S.Kom., M.Kom.'),
('19010001', 'MK002', 'Pemrograman Mobile', 3, 'Ir. Joko Susilo, M.T.'),
('19010001', 'MK003', 'Kecerdasan Buatan', 3, 'Prof. Rini Astuti, Ph.D.'),
('19010001', 'MK004', 'Rekayasa Perangkat Lunak', 3, 'Agus Prasetyo, S.Kom., M.Eng.'),
('19010001', 'MK005', 'Metode Penelitian', 2, 'Dr. Haryanto'),
('19010001', 'MK006', 'Sistem Basis Data', 3, 'Linda Marlina, S.T., M.Kom.'),
('000000', 'MK001', 'Pemrograman Web', 3, 'Dr. Budi Darmawan, S.Kom., M.Kom.');

-- Table structure for table `khs`
CREATE TABLE IF NOT EXISTS `khs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nim` varchar(20) NOT NULL,
  `kode_mk` varchar(20) NOT NULL,
  `nama_mk` varchar(100) NOT NULL,
  `sks` int(11) NOT NULL,
  `nilai` varchar(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `khs` (`nim`, `kode_mk`, `nama_mk`, `sks`, `nilai`) VALUES
('19010001', 'MK001', 'Pemrograman Web', 3, 'A'),
('19010001', 'MK002', 'Pemrograman Mobile', 3, 'A-'),
('19010001', 'MK006', 'Sistem Basis Data', 3, 'B+'),
('19010001', 'MK007', 'Logika Informatika', 2, 'A'),
('19010001', 'MK008', 'Sistem Operasi', 3, 'B'),
('19010001', 'MK009', 'Struktur Data', 3, 'A-'),
('000000', 'MK001', 'Pemrograman Web', 3, 'A');
