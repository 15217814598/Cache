package com.program.cache;

/**
 * 缓存实体类
 * @author D
 *
 */
public class Cache {
	/**
	 * 保存的数据
	 */
	private volatile Object data;
	/**
	 * 缓存自创建后多久失效
	 */
	private long timeToLiveSeconds;
	/**
	 * 缓存创建时间
	 */
	private long createTime;
	
	public Cache(Object data, long timeToLiveSeconds, long createTime) {
		super();
		this.data = data;
		this.timeToLiveSeconds = timeToLiveSeconds;
		this.createTime = createTime;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}
	
	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	
}
