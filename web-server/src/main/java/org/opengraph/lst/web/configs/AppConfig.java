package org.opengraph.lst.web.configs;

import org.opengraph.lst.anlysis.StatAnalyzer;
import org.opengraph.lst.anlysis.SummaryAnalyzer;
import org.opengraph.lst.core.repos.StatRepository;
import org.opengraph.lst.anlysis.ApplicationEventListener;
import org.opengraph.lst.repository.fs.InMemoryStatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.UUID;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AppConfig extends AsyncConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private ApplicationEventPublisher publisher;

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

    // Add all analyzer
    @Bean
    public SummaryAnalyzer summaryAnalyzer() {
        return new SummaryAnalyzer();
    }

    @Bean
    public StatAnalyzer statAnalyzer() {
        return new StatAnalyzer(statRepository(), publisher);
    }

    @Bean
    public StatRepository statRepository() {
        return new InMemoryStatRepository(publisher);
    }

    // Add all Event listeners
    @Bean
    public ApplicationEventListener applicationEventListener() {
        return new ApplicationEventListener(statAnalyzer(), summaryAnalyzer());
    }
}
