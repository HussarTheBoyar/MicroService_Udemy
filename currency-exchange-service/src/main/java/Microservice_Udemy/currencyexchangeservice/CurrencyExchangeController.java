package Microservice_Udemy.currencyexchangeservice;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
  @Autowired
  private Environment environment;
  @Autowired
  private CurrencyExchangeRepository currencyExchangeRepository;

  public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
    this.environment = environment;
    this.currencyExchangeRepository = currencyExchangeRepository;
  }

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to) throws RuntimeException{
    CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

    if (currencyExchange == null){
      throw  new RuntimeException("Cannot find data for " + from + " and " + to);
    }

    String port = environment.getProperty("local.server.port");
    currencyExchange.setEnvironment(port);
    return currencyExchange;
  }

}
