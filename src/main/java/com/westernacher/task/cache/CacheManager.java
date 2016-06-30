package com.westernacher.task.cache;

import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {

	private static ConcurrentHashMap<Integer, CacheItem> cachedObjects = new ConcurrentHashMap<Integer, CacheItem>();

	@SuppressWarnings("unchecked")
	public static <T> T getCache(Integer cacheId) {
		final CacheItem item = cachedObjects.get(cacheId);
		if ((item == null) || item.expired()) {
			return null;
		} else {
			return (T) item.getData();
		}
	}

	public static <T> T setCache(Integer cacheId, T object) {
		cachedObjects.put(cacheId, new CacheItem(object));
		return object;
	}

	public static <T> T setCache(Integer cacheId, T object, long expireIntervalMs) {
		cachedObjects.put(cacheId, new CacheItem(expireIntervalMs, object));
		return object;
	}

	public static void invalidateCache(Integer cacheId) {
		cachedObjects.remove(cacheId);
	}

}
