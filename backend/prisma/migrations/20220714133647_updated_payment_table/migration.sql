-- AlterTable
ALTER TABLE `Payment` ADD COLUMN `razorPayOrderId` VARCHAR(191) NULL,
    ADD COLUMN `razorPayPaymentId` VARCHAR(191) NULL,
    ADD COLUMN `razorPaySignature` VARCHAR(191) NULL;
