-- DropIndex
DROP INDEX `User_email_key` ON `User`;

-- AlterTable
ALTER TABLE `User` MODIFY `name` VARCHAR(255) NULL DEFAULT '',
    MODIFY `email` VARCHAR(255) NULL DEFAULT '',
    MODIFY `password` VARCHAR(191) NULL DEFAULT '',
    MODIFY `resetToken` VARCHAR(191) NULL DEFAULT '',
    MODIFY `resetTokenExpiry` DATETIME(3) NULL DEFAULT CURRENT_TIMESTAMP(3),
    MODIFY `createdAt` DATETIME(3) NULL DEFAULT CURRENT_TIMESTAMP(3),
    MODIFY `isAdmin` BOOLEAN NULL DEFAULT false,
    MODIFY `isVerified` BOOLEAN NULL DEFAULT false,
    MODIFY `isActive` BOOLEAN NULL DEFAULT false;
