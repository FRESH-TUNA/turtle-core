/*
 * question
 */
CREATE TABLE IF NOT EXISTS `question` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6) DEFAULT NULL,
    `modified_date` datetime(6) DEFAULT NULL,
    `link` varchar(255) NOT NULL,
    `practice_status` varchar(255) DEFAULT NULL,
    `title` varchar(100) NOT NULL,
    `user` bigint(20) NOT NULL,
    `platform_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK1nirxj8yet2l2w6wjx3qwlj8m` (`platform_id`),
    CONSTRAINT `FK1nirxj8yet2l2w6wjx3qwlj8m` FOREIGN KEY (`platform_id`) REFERENCES `platform` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
