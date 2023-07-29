package Microservice_Udemy.currencyexchangeservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController

public class CircuitBreakerController {

  private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
  @GetMapping("/sample-api")
  /*@Retry(name = "sample-api", fallbackMethod = "hardcodeResponse")*/
  @CircuitBreaker(name = "sample-api", fallbackMethod = "hardcodeResponse")
  @RateLimiter(name = "sample-api")
  //in default mode: RateLimiter allow 10000 calls to sample api per 10s
  public  String sampleApi(){
    logger.info("Sample Api call recceived");

    /*ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);

    return forEntity.getBody();*/

    return "sample api";
  }

  public String hardcodeResponse(Exception ex){
    return "fallback response";
  }

}
