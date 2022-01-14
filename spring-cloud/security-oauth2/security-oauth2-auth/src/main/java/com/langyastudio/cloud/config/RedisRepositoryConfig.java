package com.langyastudio.cloud.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

/**
 * Redis相关配置
 * @author langyastudio
 */
@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig
{
    /**
     * redis template相关配置
     * 使redis支持插入对象
     *
     * @return 方法缓存 Methods the cache
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory)
    {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        // 值采用json序列化
        template.setValueSerializer(this.fastJsonRedisSerializer(false));

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(this.fastJsonRedisSerializer(false));

        template.afterPropertiesSet();
        return template;
    }

    private FastJsonRedisSerializer<Object> fastJsonRedisSerializer(boolean WriteClassName)
    {
        //使用FastJsonRedisSerializer来序列化和反序列化redis的value值
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        FastJsonConfig                  fastJsonConfig          = fastJsonRedisSerializer.getFastJsonConfig();

        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty);
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);

        fastJsonConfig.setFeatures(Feature.SupportAutoType);

        if(WriteClassName)
        {
            fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteClassName);
        }

        //如果时间类型值为null，则返回空串
        //否则null类型的字段不返回!!!
/*        ValueFilter dateFilter = (Object var1, String var2, Object var3) -> {
            try {
                if (var3 == null && var1 != null &&
                        LocalDateTime.class.isAssignableFrom(var1.getClass().getDeclaredField(var2).getType())) {
                    return "";
                }
            } catch (Exception e) {
            }
            return var3;
        };
        fastJsonConfig.setSerializeFilters(dateFilter);*/

        return fastJsonRedisSerializer;
    }
}
