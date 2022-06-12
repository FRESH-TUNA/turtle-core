/*
 * platform
 */
CREATE TABLE IF NOT EXISTS `platform` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `created_date` datetime(6) DEFAULT NULL,
                            `modified_date` datetime(6) DEFAULT NULL,
                            `link` varchar(255) NOT NULL,
                            `name` varchar(100) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
