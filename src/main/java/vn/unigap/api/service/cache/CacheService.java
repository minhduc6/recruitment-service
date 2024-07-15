package vn.unigap.api.service.cache;

public interface CacheService {
  void put(String cacheName, String key, Object value);

  Object get(String cacheName, String key);

  void evict(String cacheName, String key);

  void clear(String cacheName);
}
