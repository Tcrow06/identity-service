package com.example.minio_storage.service.iml;

import com.example.minio_storage.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BaseRedisServiceImpl implements BaseRedisService {
    //Thao tác với những Object nào chỉ lưu 2 trường là key và value
    private final RedisTemplate<String, Object> redisTemplate;

    //Thao tác với những Object nào lưu 3 trường;

    private final HashOperations<String,String, Object> hashOperations;
    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void setTimeToLive(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key,timeout, timeUnit);
    }

    @Override
    public void hashSet(String key, String field, Object value) {
        hashOperations.put(key, field, value);
    }

    @Override
    public boolean hashExists(String key, String field) {

        return hashOperations.hasKey(key,field);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Map<String, Object> getField(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public Object hastGet(String key, String field) {
        return hashOperations.get(key, field);
    }

    @Override
    public List<Object> hashGetByFieldPrefix(String key, String fieldPrefix) {
        List<Object> objects = new ArrayList<>();

        Map<String, Object> hashEntries =  hashOperations.entries(key);
        for (Map.Entry<String, Object> entry : hashEntries.entrySet()){
            if(entry.getKey().startsWith(fieldPrefix))
                objects.add(entry.getValue());
        }
        return objects;
    }

    @Override
    public Set<String> getFieldPrefixes(String key) {
        return hashOperations.entries(key).keySet();
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(String key, String field) {
        hashOperations.delete(key, field);
    }

    @Override
    public void delete(String key, List<String> fields) {
        for(String field : fields)
            hashOperations.delete(key, field);
    }
}
