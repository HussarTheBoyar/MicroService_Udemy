package Microservice_Udemy.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

  @Autowired
  private CurrencyExchangeProxy currencyExchangeProxy;

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
      @PathVariable String to, @PathVariable BigDecimal quantity) {

    //Call API to CurrencyExchangeService to get data

    String currencyExchangeServiceURI = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
    HashMap<String, String> uriVariable = new HashMap<>();
    uriVariable.put("from", from);
    uriVariable.put("to", to);
    ResponseEntity<CurrencyExchange> responseEntity = new RestTemplate().getForEntity(
        currencyExchangeServiceURI, CurrencyExchange.class, uriVariable);

    CurrencyExchange currencyExchange = responseEntity.getBody();

    return new CurrencyConversion(1L, from, to,
        currencyExchange.getConversionMultiple(), quantity,
        quantity.multiply(currencyExchange.getConversionMultiple()),
        currencyExchange.getEnvironment() + " Resttemplate");
  }

  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
      @PathVariable String to, @PathVariable BigDecimal quantity) {

    //Use Feign to call the other service in our Sys

    CurrencyConversion currencyConversion = currencyExchangeProxy.getExchangeValue(from, to);

    return new CurrencyConversion(1L, from, to,
        currencyConversion.getConversionMultiple(), quantity,
        quantity.multiply(currencyConversion.getConversionMultiple()),
        currencyConversion.getEnvironment() + " Feign");
  }

}
