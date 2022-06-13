/*
  Warnings:

  - You are about to drop the column `name` on the `Size` table. All the data in the column will be lost.
  - Added the required column `description` to the `Size` table without a default value. This is not possible if the table is not empty.
  - Added the required column `measurement` to the `Size` table without a default value. This is not possible if the table is not empty.
  - Added the required column `title` to the `Size` table without a default value. This is not possible if the table is not empty.
  - Made the column `productId` on table `Size` required. This step will fail if there are existing NULL values in that column.

*/
-- DropIndex
DROP INDEX `Size_name_key` ON `Size`;

-- AlterTable
ALTER TABLE `Size` DROP COLUMN `name`,
    ADD COLUMN `description` VARCHAR(191) NOT NULL,
    ADD COLUMN `measurement` VARCHAR(191) NOT NULL,
    ADD COLUMN `title` VARCHAR(191) NOT NULL,
    MODIFY `productId` VARCHAR(191) NOT NULL;
