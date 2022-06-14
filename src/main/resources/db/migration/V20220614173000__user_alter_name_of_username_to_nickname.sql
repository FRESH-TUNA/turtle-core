/*
 * user
 * add oauth_id column
 */
ALTER TABLE `User` RENAME COLUMN `username` TO `nickname`;
