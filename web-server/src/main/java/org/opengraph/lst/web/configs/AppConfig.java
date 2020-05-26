package org.opengraph.lst.web.configs;

import java.util.UUID;
import java.util.concurrent.Executor;

import org.opengraph.lst.anlysis.ApplicationEventListener;
import org.opengraph.lst.anlysis.StatAnalyzer;
import org.opengraph.lst.anlysis.SummaryAnalyzer;
import org.opengraph.lst.core.repos.StatRepository;
import org.opengraph.lst.core.service.AnalysisService;
import org.opengraph.lst.service.AnalysisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableAsync
@PropertySource(value = {"classpath:application.properties"})
@ComponentScan(basePackages = {
		"org.opengraph.lst.core.repos", "org.opengraph.lst.core.util", "org.opengraph.lst.anlysis", 
		"org.opengraph.lst.service"
		})
@Import(value = {SecurityConfig.class})
public class AppConfig extends AsyncConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    public Executor getAsyncExecutor() {
        LOGGER.info("UUID - {}", UUID.nameUUIDFromBytes("Test".getBytes()).toString());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(1000);
        executor.initialize();
        return executor;
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            LOGGER.error("Error executing method {} with args {}", method, objects, throwable);
        };
    }
    
    @Bean
    public AnalysisService analysisService(@Autowired SummaryAnalyzer summaryAnalyzer, @Autowired StatRepository statRepository) {
    	return new AnalysisServiceImpl(summaryAnalyzer, statRepository);
    }

    // Add all Event listeners
    @Bean
    public ApplicationEventListener applicationEventListener(@Autowired StatAnalyzer statAnalyzer, @Autowired SummaryAnalyzer summaryAnalyzer) {
        return new ApplicationEventListener(statAnalyzer, summaryAnalyzer);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
