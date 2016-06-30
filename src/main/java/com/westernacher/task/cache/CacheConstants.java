package com.westernacher.task.cache;

import java.util.concurrent.TimeUnit;

public interface CacheConstants {

	public static long DEFAULT_CACHE_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(60);

}
