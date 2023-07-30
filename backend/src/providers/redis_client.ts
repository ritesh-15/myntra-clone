import { RedisClientType, createClient } from "redis"

class RedisProvider {
  private static client: RedisClientType | null = null

  private constructor() {}

  static getInstance(): RedisClientType {
    if (RedisProvider.client == null) {
      RedisProvider.client = createClient({
        socket: {
          host: process.env.REDIS_HOST,
          port: +process.env.REDIS_PORT!!,
        },
        password: process.env.REDIS_PASSWORD,
      })
    }
    return RedisProvider.client
  }
}

export default RedisProvider
