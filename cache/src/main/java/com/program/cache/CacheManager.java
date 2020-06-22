package com.program.cache;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 缓存类
 * @author D
 *
 */
public class CacheManager {

	/**
	 * 缓存的最大容量，默认为10
	 */
	private int maxSize = 10;
	/**
	 * 缓存存储的map
	 */
	private static Map<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
	/**
	 * 链表，用于实现FIFO
	 */
	private LinkedList<String> cacheList = new LinkedList<String>();
	/**
	 * 设置缓存的最大容量
	 * @param size 输入缓存容量
	 */
	public void setMaxSize(int size) {
		this.maxSize = size;
	}
	/**
	 * 设置缓存，支持设置缓存过期时间，默认过期时间为10秒
	 * 当缓存容量超过设置大小，通过先进先出原则去删除缓存
	 * @param key 缓存对应的键值
	 * @param data 缓存数据
	 * @param ttl 缓存过期时间
	 */
	public void setCache(String key, Object data, long ttl) {
		if (cacheMap.size() > maxSize - 1) {
			this.removeCache(cacheList.getFirst());
		}
		ttl = ttl > 0 ? ttl : 10 * 1000L;
		cacheList.add(key);
		Cache cache = new Cache(data, ttl, System.currentTimeMillis());
		cacheMap.put(key, cache);
	}
	/**
	 * 获取缓存
	 * @param key 缓存对应的键
	 * @return 返回缓存
	 */
	public Cache getCache(String key) {
		return isExist(key) ? cacheMap.get(key) : null;
	}
	/**
	 * 获取缓存中的数据
	 * @param key 缓存对应的键
	 * @return 返回缓存中的数据
	 */
	public Object getCacheData(String key) {
		return this.isExist(key) ? cacheMap.get(key).getData() : null;
	}
	/**
	 * 删除缓存
	 * @param key 缓存对应的键
	 */
	public void removeCache(String key) {
		if (this.isExist(key)) {
			cacheList.remove(key);
			cacheMap.remove(key);
		}
	}
	/**
	 * 清空缓存
	 */
	public void clearCache() {
		cacheList.clear();
		cacheMap.clear();
	}
	/**
	 * 判断缓存是否存在
	 * @param key 缓存对应的键
	 * @return true:存在，false:不存在
	 */
	public boolean isExist(String key) {
		return cacheMap.containsKey(key);
	}
	/**
	 * 查看缓存是否失效
	 * @param key 缓存对应的键
	 * @return true:失效，false:未失效
	 */
	public boolean isTimeOut(String key) {
		if (!this.isExist(key)) {
			return true;
		}
		Cache cache = cacheMap.get(key);
		long timeToLiveSeconds = cache.getTimeToLiveSeconds();
		long createTime = cache.getCreateTime();
		if (timeToLiveSeconds ==0 || System.currentTimeMillis() - createTime > timeToLiveSeconds) {
			return true;
		}
		return false;
	}
	/**
	 * 获取所有缓存的键值
	 * @return 返回缓存的所有键
	 */
	public Set<String> getAllCacheKey() {
		return cacheMap.keySet();
	}
	/**
	 * 显示所有缓存
	 * @param cacheManager 缓存
	 */
	public static void showAllCache(CacheManager cacheManager){
		for (String key : cacheManager.getAllCacheKey()) {
			System.out.println(key + ":" + cacheManager.getCacheData(key));
		}
	}
}
