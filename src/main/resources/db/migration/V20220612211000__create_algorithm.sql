/*
 * algorithm
 * IF NOT EXISTS 를 통해 기존테이블을 가져올수 있다.
 * 혹은 첫번째 마이그레이션 파일에 놓는것도 방법이다.
 */
CREATE TABLE IF NOT EXISTS `algorithm` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `created_date` datetime(6) DEFAULT NULL,
                             `modified_date` datetime(6) DEFAULT NULL,
                             `name` varchar(100) NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
