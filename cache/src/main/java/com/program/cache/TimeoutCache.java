package com.program.cache;

/**
 * 监视缓存类，若缓存失效则清除
 * @author D
 *
 */
public class TimeoutCache implements Runnable {

	private CacheManager cacheManager;
	
	public TimeoutCache(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public synchronized void run() {
		// TODO Auto-generated method stub
		while (true) {
			for (String key : cacheManager.getAllCacheKey()) {
				if (cacheManager.isTimeOut(key)) {
					cacheManager.removeCache(key);
					System.out.println(key + ":缓存被清除");
				}
			}
		}
	}

	
	
}
