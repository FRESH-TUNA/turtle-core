/*
 * question
 */
CREATE TABLE IF NOT EXISTS `refresh_token` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL,
    `token` varchar(255) DEFAULT NULL,
    `title` varchar(100) NOT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
