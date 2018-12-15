package my.mmshulga.currencyconversionservice.proxies;

import my.mmshulga.currencyconversionservice.models.CurrencyConversion;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-exchange-service")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange/do")
    CurrencyConversion getExchangeValue(@RequestParam(name = "from") String from,
                                        @RequestParam(name = "to") String to);
}
