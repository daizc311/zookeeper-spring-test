package club.dreamccc.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <h2>ManagerApplication</h2>
 * <p>请添加描述</p>
 *
 * @author Daizc
 * @date 2019/9/20
 */

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
public class ManagerApplication {

//    @Autowired
//    RedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

//    @Bean
//    public RedisTemplate redisTemplateInit() {
//        //设置序列化Key的实例化对象
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        //设置序列化Value的实例化对象
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return redisTemplate;
//    }
//
//    public static final String CACHE_NAME = "test";
//
//    @Bean
//    public CachePool cachePool() {
//
//        CachePool cachePool = new CachePool();
//        try {
//            ConcurrentHashMap<String, Boolean> test = cachePool.createCacheMap(CACHE_NAME);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return cachePool;
//    }
//
//    @Bean
//    public AsyncTaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(100);
//        executor.setMaxPoolSize(1000);
//
////        ThreadPoolTaskExecutor executor2 = new ThreadPoolTaskExecutor();
////        executor2.set
//
//
//        return executor;
//    }
}
