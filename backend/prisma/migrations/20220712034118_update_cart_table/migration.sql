/*
  Warnings:

  - You are about to drop the `WishList` table. If the table is not empty, all the data it contains will be lost.

*/
-- AlterTable
ALTER TABLE `Cart` ADD COLUMN `isWishList` BOOLEAN NOT NULL DEFAULT false;

-- DropTable
DROP TABLE `WishList`;
