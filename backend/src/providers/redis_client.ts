import { RedisClientType, createClient } from "redis";

class RedisProvider {
  private static client: RedisClientType | null = null;

  private constructor() {}

  static getInstance(): RedisClientType {
    if (RedisProvider.client == null) {
      RedisProvider.client = createClient({
        url: process.env.REDIS_URL || "redis://localhost:6379",
      });
    }
    return RedisProvider.client;
  }
}

export default RedisProvider;
