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
('mahasiswa1', 'mahasiswa1@uap.ac.id', 'e10adc3949ba59abbe56e057f20f883e', 'Budi Santoso', '19010001', 'Teknik Informatika');
-- password 'admin' is 'admin' md5. password '123456' is '123456' md5.

-- Table structure for table `berita`
CREATE TABLE IF NOT EXISTS `berita` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judul` varchar(255) NOT NULL,
  `isi` text NOT NULL,
  `tanggal` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `berita` (`judul`, `isi`, `tanggal`) VALUES
('Pengumuman UTS Semester Ganjil', 'Ujian Tengah Semester Ganjil akan dilaksanakan mulai tanggal 15 Oktober.', '2026-10-01'),
('Beasiswa Berprestasi UAP', 'Pendaftaran beasiswa berprestasi telah dibuka, silakan hubungi bagian kemahasiswaan.', '2026-09-20');

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
('19010001', 'MK001', 'Pemrograman Web', 3, 'Dr. Budi'),
('19010001', 'MK002', 'Pemrograman Mobile', 3, 'Ir. Joko');

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
('19010001', 'MK002', 'Pemrograman Mobile', 3, 'A-');
