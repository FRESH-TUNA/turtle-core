/*
 * user
 * add oauth_id column
 */
ALTER TABLE `user` ADD COLUMN `oauth_id` varchar(255) DEFAULT NULL;
