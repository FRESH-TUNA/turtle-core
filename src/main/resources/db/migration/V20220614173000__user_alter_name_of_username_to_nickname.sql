/*
 * user
 * add oauth_id column
 */
ALTER TABLE `user` RENAME COLUMN `username` TO `nickname`;
