package my.mmshulga.currencyconversionservice.controllers;

import my.mmshulga.currencyconversionservice.models.CurrencyConversion;
import my.mmshulga.currencyconversionservice.proxies.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeServiceProxy proxy;

    @Autowired
    public CurrencyConversionController(CurrencyExchangeServiceProxy proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/currency-converter/do")
    public CurrencyConversion convert(@RequestParam(name = "from") String from,
                                      @RequestParam(name = "to") String to,
                                      @RequestParam(name = "quantity") BigDecimal quantity) {

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("from", from);
        uriParams.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/do?from={from}&to={to}",
                CurrencyConversion.class,
                uriParams);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        CurrencyConversion returned = new CurrencyConversion();
        returned.setFrom(from);
        returned.setTo(to);
        returned.setId(currencyConversion.getId());
        returned.setQuantity(quantity);
        returned.setMultiplier(currencyConversion.getMultiplier());
        returned.setConvertedAmount(quantity.multiply(currencyConversion.getMultiplier()));
        returned.setPort(currencyConversion.getPort());

        return returned;
    }

    @GetMapping("/currency-converter-feign/do")
    public CurrencyConversion convertFeign(@RequestParam(name = "from") String from,
                                      @RequestParam(name = "to") String to,
                                      @RequestParam(name = "quantity") BigDecimal quantity) {


        CurrencyConversion currencyConversion = proxy.getExchangeValue(from, to);

        CurrencyConversion returned = new CurrencyConversion();
        returned.setFrom(from);
        returned.setTo(to);
        returned.setId(currencyConversion.getId());
        returned.setQuantity(quantity);
        returned.setMultiplier(currencyConversion.getMultiplier());
        returned.setConvertedAmount(quantity.multiply(currencyConversion.getMultiplier()));
        returned.setPort(currencyConversion.getPort());

        return returned;
    }
}
