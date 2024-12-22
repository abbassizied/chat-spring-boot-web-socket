-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 22, 2024 at 11:22 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chat-web-socket`
--

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `message_content` varchar(255) DEFAULT NULL,
  `message_type` enum('CONTACT','PRIVATE_CHAT','PUBLIC_CHAT') DEFAULT NULL,
  `to_user_id` int(11) DEFAULT NULL,
  `from_user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `created_at`, `message_content`, `message_type`, `to_user_id`, `from_user_id`) VALUES
(51, '2024-11-14 00:13:32.000000', '444', 'PRIVATE_CHAT', 2, 1),
(52, '2024-11-14 00:13:42.000000', '444', 'PRIVATE_CHAT', 2, 1),
(53, '2024-11-14 00:16:34.000000', '444', 'PRIVATE_CHAT', 2, 1),
(54, '2024-11-14 00:16:37.000000', '444', 'PRIVATE_CHAT', 2, 1),
(55, '2024-11-14 00:20:52.000000', '555', 'PRIVATE_CHAT', 2, 1),
(56, '2024-11-14 00:21:19.000000', '444', 'PRIVATE_CHAT', 2, 1),
(57, '2024-11-14 00:22:22.000000', '5', 'PRIVATE_CHAT', 2, 1),
(58, '2024-11-14 00:22:27.000000', '', 'PRIVATE_CHAT', 2, 1),
(59, '2024-11-14 00:22:29.000000', '', 'PRIVATE_CHAT', 2, 1),
(60, '2024-11-14 00:22:29.000000', '', 'PRIVATE_CHAT', 2, 1),
(61, '2024-11-14 00:22:31.000000', '565', 'PRIVATE_CHAT', 2, 1),
(62, '2024-11-14 00:23:19.000000', '55', 'PRIVATE_CHAT', 2, 1),
(63, '2024-11-14 00:26:43.000000', '444', 'PRIVATE_CHAT', 2, 1),
(64, '2024-11-14 00:26:47.000000', 'ooooooooooooo', 'PRIVATE_CHAT', 2, 1),
(65, '2024-11-14 00:29:30.000000', 'kk', 'PUBLIC_CHAT', NULL, 1),
(66, '2024-11-14 00:29:33.000000', 'kk', 'PUBLIC_CHAT', NULL, 1),
(67, '2024-11-14 00:30:34.000000', 'yuiluylu', 'PUBLIC_CHAT', NULL, 1),
(68, '2024-11-14 00:30:36.000000', 'tyiytiyti', 'PUBLIC_CHAT', NULL, 1),
(69, '2024-11-14 00:30:46.000000', 'szurrt', 'PRIVATE_CHAT', 2, 1),
(70, '2024-11-14 00:30:50.000000', 'rztutrzzrtrt', 'PRIVATE_CHAT', 2, 1),
(71, '2024-11-14 00:34:14.000000', '', 'PRIVATE_CHAT', 2, 1),
(72, '2024-11-14 00:34:23.000000', '444', 'PRIVATE_CHAT', 2, 1),
(73, '2024-11-14 00:36:18.000000', '	console.log(\"begin - displayMessage\");', 'PRIVATE_CHAT', 2, 1),
(74, '2024-11-14 00:36:30.000000', '	console.log(\"begin - displayMessage\");', 'PRIVATE_CHAT', 2, 1),
(75, '2024-11-14 00:37:54.000000', '444', 'PRIVATE_CHAT', 2, 1),
(76, '2024-11-14 00:37:58.000000', '444', 'PRIVATE_CHAT', 2, 1),
(77, '2024-11-14 00:38:38.000000', 'iiiiiiiiiiiiii', 'PRIVATE_CHAT', 2, 1),
(78, '2024-11-14 00:39:30.000000', '444', 'PRIVATE_CHAT', 2, 1),
(79, '2024-11-14 00:39:35.000000', 'iiiiiiiiii', 'PRIVATE_CHAT', 2, 1),
(80, '2024-11-14 00:39:40.000000', 'ppppppppppppppppp', 'PRIVATE_CHAT', 2, 1),
(81, '2024-11-14 00:39:43.000000', 'hhhhh', 'PRIVATE_CHAT', 2, 1),
(82, '2024-11-14 00:39:55.000000', 'a7la zied', 'PRIVATE_CHAT', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `updated_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `created_date`, `name`, `updated_date`) VALUES
(1, NULL, 'ROLE_SUPERADMIN', NULL),
(2, '2024-11-10 07:15:55.000000', 'ROLE_USER', '2024-11-10 07:15:55.000000'),
(3, '2024-11-10 07:20:07.000000', 'ROLE_ADMIN', '2024-11-10 07:20:07.000000');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `updated_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `active`, `created_date`, `email`, `first_name`, `last_name`, `password`, `updated_date`) VALUES
(1, b'1', NULL, 'abbassizied@outlook.fr', 'Zied', 'Abbassi', '$2a$10$7ariAThTH72UZDXF4/V8POuE9rUOu68/hbOrHt2/n9KbSDBb9cFX2', '2024-11-10 06:39:26.000000'),
(2, b'1', '2024-11-10 06:39:59.000000', 'abbassi.mohamed@outlook.fr', 'Mohamed', 'Abbassi', '$2a$10$pF5loEpjUHBgNDdazwgppOJNyQ6p5sYaK9SzAooB7.u06FnvT0EgS', '2024-11-10 06:39:59.000000');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1uv23ovkid0grayvivw5vkpf0` (`to_user_id`),
  ADD KEY `FK6ym9ojpy2t5aytdw25r4hsn2s` (`from_user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK1uv23ovkid0grayvivw5vkpf0` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK6ym9ojpy2t5aytdw25r4hsn2s` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
