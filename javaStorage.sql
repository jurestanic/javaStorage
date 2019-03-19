-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 19, 2019 at 07:34 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `storage`
--

-- --------------------------------------------------------

--
-- Table structure for table `grad`
--

CREATE TABLE `grad` (
  `grad_id` int(11) NOT NULL,
  `ime` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `postanski_broj` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `grad`
--

INSERT INTO `grad` (`grad_id`, `ime`, `postanski_broj`) VALUES
(1, 'Mostar', '88000');

-- --------------------------------------------------------

--
-- Table structure for table `kategorija`
--

CREATE TABLE `kategorija` (
  `kategorija_id` int(11) NOT NULL,
  `ime` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `kategorija`
--

INSERT INTO `kategorija` (`kategorija_id`, `ime`) VALUES
(1, 'Obuca'),
(2, 'Skije'),
(3, 'Naocale'),
(5, 'qwdqdqwd'),
(15, 'wfwefwef'),
(16, 'qwdqdqwdqdq'),
(17, 'qweqweqwe'),
(26, 'qweqwrtwsfwdf'),
(33, 'werwerwer');

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `id` int(11) NOT NULL,
  `korisnicko_ime` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lozinka` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`id`, `korisnicko_ime`, `lozinka`, `role_id`) VALUES
(1, 'Jure', 'jure123', 2),
(3, 'Andra', 'andra123', 1),
(4, 'Cvita', 'cvita123', 1);

-- --------------------------------------------------------

--
-- Table structure for table `proizvod`
--

CREATE TABLE `proizvod` (
  `id` int(11) NOT NULL,
  `ime` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `kategorija_id` int(11) NOT NULL,
  `kolicina` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `cijena` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `skladiste_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `proizvod`
--

INSERT INTO `proizvod` (`id`, `ime`, `kategorija_id`, `kolicina`, `cijena`, `skladiste_id`) VALUES
(1, 'Fischer', 2, '1968', '29', 1),
(80, 'Nike', 1, '243', '124', 1),
(98, 'Adidas', 1, '505', '132', 1),
(108, 'Puma', 1, '1247', '123', 1),
(111, 'New Balance', 1, '62', '189', 1),
(113, 'Fuša Skije', 2, '1', '99', 1);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `role_name`) VALUES
(1, 'admin'),
(2, 'member');

-- --------------------------------------------------------

--
-- Table structure for table `skladiste`
--

CREATE TABLE `skladiste` (
  `skladiste_id` int(11) NOT NULL,
  `grad_id` int(11) NOT NULL,
  `velicina` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `skladiste`
--

INSERT INTO `skladiste` (`skladiste_id`, `grad_id`, `velicina`) VALUES
(1, 1, 110);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `grad`
--
ALTER TABLE `grad`
  ADD PRIMARY KEY (`grad_id`);

--
-- Indexes for table `kategorija`
--
ALTER TABLE `kategorija`
  ADD PRIMARY KEY (`kategorija_id`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- Indexes for table `proizvod`
--
ALTER TABLE `proizvod`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kategorija_id` (`kategorija_id`),
  ADD KEY `skladiste_id` (`skladiste_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `skladiste`
--
ALTER TABLE `skladiste`
  ADD PRIMARY KEY (`skladiste_id`),
  ADD KEY `grad_id` (`grad_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `grad`
--
ALTER TABLE `grad`
  MODIFY `grad_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `kategorija`
--
ALTER TABLE `kategorija`
  MODIFY `kategorija_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `proizvod`
--
ALTER TABLE `proizvod`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=174;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `skladiste`
--
ALTER TABLE `skladiste`
  MODIFY `skladiste_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD CONSTRAINT `korisnik_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `proizvod`
--
ALTER TABLE `proizvod`
  ADD CONSTRAINT `proizvod_ibfk_1` FOREIGN KEY (`kategorija_id`) REFERENCES `kategorija` (`kategorija_id`),
  ADD CONSTRAINT `proizvod_ibfk_2` FOREIGN KEY (`skladiste_id`) REFERENCES `skladiste` (`skladiste_id`);

--
-- Constraints for table `skladiste`
--
ALTER TABLE `skladiste`
  ADD CONSTRAINT `skladiste_ibfk_1` FOREIGN KEY (`grad_id`) REFERENCES `grad` (`grad_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
