package my.mmshulga.currencyconversionservice.controllers;

import my.mmshulga.currencyconversionservice.models.CurrencyConversion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/currency-converter/")
public class CurrencyConversionController {
    @GetMapping
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
}
