/*
  Warnings:

  - Made the column `createdAt` on table `User` required. This step will fail if there are existing NULL values in that column.
  - Made the column `isAdmin` on table `User` required. This step will fail if there are existing NULL values in that column.
  - Made the column `isVerified` on table `User` required. This step will fail if there are existing NULL values in that column.
  - Made the column `isActive` on table `User` required. This step will fail if there are existing NULL values in that column.

*/
-- AlterTable
ALTER TABLE `User` ALTER COLUMN `password` DROP DEFAULT,
    ALTER COLUMN `resetToken` DROP DEFAULT,
    ALTER COLUMN `resetTokenExpiry` DROP DEFAULT,
    MODIFY `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    ALTER COLUMN `phoneNumber` DROP DEFAULT,
    MODIFY `isAdmin` BOOLEAN NOT NULL DEFAULT false,
    MODIFY `isVerified` BOOLEAN NOT NULL DEFAULT false,
    MODIFY `isActive` BOOLEAN NOT NULL DEFAULT false;
