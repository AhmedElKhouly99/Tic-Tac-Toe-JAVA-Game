-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 08, 2022 at 11:03 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `xogame`
--

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE `game` (
  `username1_x` varchar(50) NOT NULL,
  `username2_o` varchar(50) NOT NULL,
  `one` varchar(1) DEFAULT NULL,
  `two` varchar(1) DEFAULT NULL,
  `three` varchar(1) DEFAULT NULL,
  `four` varchar(1) DEFAULT NULL,
  `five` varchar(1) DEFAULT NULL,
  `six` varchar(1) DEFAULT NULL,
  `seven` varchar(1) DEFAULT NULL,
  `eight` varchar(1) DEFAULT NULL,
  `nine` varchar(1) DEFAULT NULL,
  `turn` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `username` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`username`, `password`, `score`) VALUES
('AbdelRahman_9$', 'Abcd1234_$', 0),
('Ahmed_0$', 'Ahmed_0$', 10),
('Ahmed_04$', 'Ahmed_04$', 0),
('Eman', '123', 110),
('khouly', '12345', 195),
('Soly', 'soly', 205);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`username1_x`,`username2_o`),
  ADD KEY `usernamw2_o` (`username2_o`);

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `game`
--
ALTER TABLE `game`
  ADD CONSTRAINT `game_ibfk_1` FOREIGN KEY (`username1_x`) REFERENCES `player` (`username`),
  ADD CONSTRAINT `game_ibfk_2` FOREIGN KEY (`username2_o`) REFERENCES `player` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
