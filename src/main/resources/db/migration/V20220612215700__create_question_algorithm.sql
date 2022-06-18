/*
 * question_algorithm
 */
CREATE TABLE IF NOT EXISTS `question_algorithm` (
                                      `algorithm_id` bigint(20) NOT NULL,
                                      `question_id` bigint(20) NOT NULL,
                                      KEY `FKj2gcu7p1mue7bqdifoy8697f9` (`question_id`),
                                      KEY `FK4jsk8sqsig7ibitgft6px1mis` (`algorithm_id`),
                                      CONSTRAINT `FK4jsk8sqsig7ibitgft6px1mis` FOREIGN KEY (`algorithm_id`) REFERENCES `question` (`id`),
                                      CONSTRAINT `FKj2gcu7p1mue7bqdifoy8697f9` FOREIGN KEY (`question_id`) REFERENCES `algorithm` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
