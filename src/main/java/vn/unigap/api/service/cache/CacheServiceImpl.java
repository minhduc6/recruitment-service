package vn.unigap.api.service.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

  private final CacheManager cacheManager;

  public CacheServiceImpl(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @Override
  public void put(String cacheName, String key, Object value) {
    Cache cache = cacheManager.getCache(cacheName);
    if (cache != null) {
      cache.put(key, value);
    }
  }

  @Override
  public Object get(String cacheName, String key) {
    Cache cache = cacheManager.getCache(cacheName);
    if (cache != null) {
      Cache.ValueWrapper wrapper = cache.get(key);
      if (wrapper != null) {
        return wrapper.get();
      }
    }
    return null;
  }

  @Override
  public void evict(String cacheName, String key) {
    Cache cache = cacheManager.getCache(cacheName);
    if (cache != null) {
      cache.evict(key);
    }
  }

  @Override
  public void clear(String cacheName) {
    Cache cache = cacheManager.getCache(cacheName);
    if (cache != null) {
      cache.clear();
    }
  }
}
