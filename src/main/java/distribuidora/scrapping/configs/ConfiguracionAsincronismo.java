package distribuidora.scrapping.configs;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ConfiguracionAsincronismo {


    @Bean(name = "MyExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }

}