package clima.climaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.common.util.concurrent.RateLimiter;

@Configuration 
@EnableScheduling
public class RateLimitConfiguration {
    
    private static final int REQUESTS_PER_HOUR = 1000;

    private final RateLimiter rateLimiter = RateLimiter.create(REQUESTS_PER_HOUR / 3600.0);
    @Scheduled(fixedRate = 3600000) 
    public void resetRateLimiter() {
        rateLimiter.setRate(REQUESTS_PER_HOUR / 3600.0);
    }
    @Bean
    public RateLimiter rateLimiter() {
        return rateLimiter;
    }
}
