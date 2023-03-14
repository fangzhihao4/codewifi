package codewifi.annotation.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({ ApiRedisProperties.class })
public class MyRedissonConfig {

	@Resource
	private ApiRedisProperties apiRedisProperties;

	@Bean(name = "apiRedissonClient", destroyMethod = "shutdown")
	public RedissonClient apiRedissonClient() {
		return Redisson.create(redissonConfig(apiRedisProperties));
	}

	@Bean
	public Config redissonConfig(final IRedisProperties redisProperties) {

		final Config config = new Config();
		if ("single".equalsIgnoreCase(redisProperties.getMode())) {

			final SingleServerConfig ssConfig = config.useSingleServer().setAddress(redisProperties.getNodes().get(0))
					.setConnectionPoolSize(redisProperties.getConnectionPoolSize());
			if (StringUtils.isNotBlank(apiRedisProperties.getPwd())) {
				ssConfig.setPassword(apiRedisProperties.getPwd());
			}
			if (redisProperties.getConnectTimeout() > 0) {
				ssConfig.setConnectTimeout(redisProperties.getConnectTimeout());
			}
			if (redisProperties.getResponseTimeout() > 0) {
				ssConfig.setTimeout(redisProperties.getResponseTimeout());
			}
		}
		return config;
	}

}
