/*
 * user
 */
CREATE TABLE IF NOT EXISTS `user` (
                        `id` bigint(20) NOT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `picture` varchar(255) DEFAULT NULL,
                        `provider_type` varchar(255) DEFAULT NULL,
                        `role` varchar(255) DEFAULT NULL,
                        `username` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
