import { PrismaClient } from "@prisma/client";

class PrismaClientProvider {
  private static prisma: PrismaClient | null = null;

  static get(): PrismaClient {
    if (PrismaClientProvider.prisma === null) {
      PrismaClientProvider.prisma = new PrismaClient();
    }
    return PrismaClientProvider.prisma;
  }
}

export default PrismaClientProvider;
