/*
 * user
 * add oauth_id column
 */
ALTER TABLE `user` ADD COLUMN `username` varchar(255) DEFAULT NULL;
