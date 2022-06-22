/*
 * practice_log
 */
CREATE TABLE IF NOT EXISTS `practice_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6) DEFAULT NULL,
    `modified_date` datetime(6) DEFAULT NULL,
    `practice_status` varchar(255) DEFAULT NULL,
    `question_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKhkcrv7pc7wtcy1x7864h42wy` (`question_id`),
    CONSTRAINT `FKhkcrv7pc7wtcy1x7864h42wy` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;