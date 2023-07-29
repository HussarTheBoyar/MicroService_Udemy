package MicroServiceUdemyCourse.limitsservice.Controller;

import MicroServiceUdemyCourse.limitsservice.Beans.Limits;
import MicroServiceUdemyCourse.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {
  @Autowired
  private Configuration configuration;

  /*@Value("${limits-service.minimum}")
  int minimum;
  @Value("${limits-service.maximum}")
  int maximum;*/
  @GetMapping("/limits")
  public Limits getLimnits(){

    //return new Limits(minimum, maximum);
    return  new Limits(configuration.getMinimum(), configuration.getMaximum());
    //return new Limits(1,1000);
  }

}
