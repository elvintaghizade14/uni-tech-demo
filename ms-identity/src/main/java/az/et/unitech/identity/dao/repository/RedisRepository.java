package az.et.unitech.identity.dao.repository;

import az.et.unitech.identity.config.properties.ApplicationProperties;
import az.et.unitech.identity.model.TokenPair;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class RedisRepository {

    private final RedissonClient redissonClient;
    private final ApplicationProperties.Redis props;

    public RedisRepository(RedissonClient redissonClient, ApplicationProperties properties) {
        this.redissonClient = redissonClient;
        this.props = properties.getRedis();
    }

    public void save(String name, TokenPair tokenPair) {
        saveOrUpdate(name, tokenPair);
    }

    public void update(String name, TokenPair tokenPair) {
        saveOrUpdate(name, tokenPair);
    }

    public TokenPair read(String name) {
        RBucket<TokenPair> bucket = redissonClient.getBucket(bucketName(name));
        return bucket.get();
    }

    public void delete(String name) {
        RBucket<TokenPair> bucket = redissonClient.getBucket(bucketName(name));

        if (Objects.isNull(bucket)) {
            log.warn("{} bucket not found", bucketName(name));
            return;
        }

        bucket.delete();
    }

    private void saveOrUpdate(String name, TokenPair tokenPair) {
        RBucket<TokenPair> bucket = redissonClient.getBucket(bucketName(name));
        bucket.set(tokenPair, props.getTokenTimeToLive(), TimeUnit.SECONDS);
    }

    private String bucketName(String name) {
        return String.join(":", props.getTokenPrefix(), name);
    }

}
