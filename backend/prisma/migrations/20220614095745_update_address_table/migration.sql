/*
  Warnings:

  - You are about to alter the column `pincode` on the `Address` table. The data in that column could be lost. The data in that column will be cast from `VarChar(191)` to `Int`.

*/
-- AlterTable
ALTER TABLE `Address` MODIFY `pincode` INTEGER NOT NULL;
