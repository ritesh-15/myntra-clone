/*
  Warnings:

  - A unique constraint covering the columns `[id]` on the table `Image` will be added. If there are existing duplicate values, this will fail.

*/
-- DropIndex
DROP INDEX `Image_publicId_key` ON `Image`;

-- DropIndex
DROP INDEX `Image_url_key` ON `Image`;

-- CreateIndex
CREATE UNIQUE INDEX `Image_id_key` ON `Image`(`id`);
