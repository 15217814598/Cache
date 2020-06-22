package com.program.cache;

import org.junit.Test;

/**
 * 测试类
 * @author D
 *
 */
public class TestCache {
	/**
	 * 测试缓存的添加、失效自动清除、手动清除
	 */
	@Test
	public void test1() {
		CacheManager cacheManager = new CacheManager();
		cacheManager.setMaxSize(3);
		cacheManager.setCache("test0", "test0", 5 * 1000L);
		cacheManager.setCache("test1", "test1", 10 * 1000L);
		cacheManager.setCache("test2", "test2", 15 * 1000L);
		cacheManager.setCache("test3", "test3", 20 * 1000L);
		Thread thread = new Thread(new TimeoutCache(cacheManager));
		thread.start();
		for (String key : cacheManager.getAllCacheKey()) {
			System.out.println(key + ":" + cacheManager.getCacheData(key));
		}
		try {
			Thread.sleep(13 * 1000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("剩下的缓存：");
		CacheManager.showAllCache(cacheManager);
		cacheManager.removeCache("test2");
		System.out.println("手动清除后，剩下的缓存：" );
		CacheManager.showAllCache(cacheManager);
		
	}
	/**
	 * 测试缓存的读写操作是否保证线程安全
	 */
	@Test
	public void test2() {
		final String key = "safe";
		final CacheManager cacheManager = new CacheManager();
		cacheManager.setCache(key, 1, 1L);
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {
				
				public void run() {
					if (!cacheManager.isExist(key)) {
						cacheManager.setCache(key, 1, 1L);
					} else {
						int value = (Integer) cacheManager.getCacheData(key) + 1;
						cacheManager.setCache(key, value, 1L);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}
		System.out.println(cacheManager.getCacheData(key).toString());
	}

}
