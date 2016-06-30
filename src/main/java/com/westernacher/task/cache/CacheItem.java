package com.westernacher.task.cache;

public class CacheItem {
	private final long createTime;
	private final long expireTime;
	private Object data;

	public CacheItem(long expireTime, Object data) {
		this.createTime = System.currentTimeMillis();
		this.expireTime = expireTime;
		this.setData(data);
	}

	public CacheItem(Object data) {
		this(CacheConstants.DEFAULT_CACHE_EXPIRATION_TIME, data);
	}

	public boolean expired() {
		return (System.currentTimeMillis() - createTime) > getExpireTime();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getExpireTime() {
		return expireTime;
	}

}
