package com.example.minio_storage.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface BaseRedisService {
    void set(String key, String value);
    //Set dữ liệu trên bộ mở cache nó tồn tại nếu nó hết thơi gian đấy thì nó sẽ xóa đối tượng đấy
    void setTimeToLive(String key, long timeout, TimeUnit timeUnit);
    //Trong redis nó sẽ lưu 3 trường
    void hashSet(String key, String field, Object value);
    //Kiểm tra trong redis có tồn tại key và field này không
    boolean hashExists(String key, String field);
    Object get(String key);
    Map<String, Object> getField(String key);
    Object hastGet(String key, String field);
    List<Object> hashGetByFieldPrefix(String key, String fieldPrefix);
    Set<String> getFieldPrefixes(String key);
    void delete(String key);
    void delete(String key, String field);
    void delete(String key, List<String> fields);
}
