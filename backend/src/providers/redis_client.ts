import { RedisClientType, createClient } from "redis"
import { REDIS_PASSWORD, REDIS_URL } from "../keys/secrets"

class RedisProvider {
  private static client: RedisClientType | null = null

  private constructor() {}

  static getInstance(): RedisClientType {
    if (RedisProvider.client == null) {
      console.log(REDIS_URL)

      RedisProvider.client = createClient({
        password: REDIS_PASSWORD,
        url: REDIS_URL,
      })
    }
    return RedisProvider.client
  }
}

export default RedisProvider
