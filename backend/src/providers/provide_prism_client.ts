import { PrismaClient } from "@prisma/client";

class PrismaClientProvider {
  private static prisma: PrismaClient | null = null;

  private constructor() {}

  static get(): PrismaClient {
    if (PrismaClientProvider.prisma === null || !PrismaClientProvider.prisma) {
      PrismaClientProvider.prisma = new PrismaClient();
    }
    return PrismaClientProvider.prisma;
  }
}

export default PrismaClientProvider;
