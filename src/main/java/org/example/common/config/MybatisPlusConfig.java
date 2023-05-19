package org.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pyh
 */
@Configuration
public class MybatisPlusConfig {

	@Bean
	public CustomizedSqlInjector customizedSqlInjector() {
		return new CustomizedSqlInjector();
	}

}
