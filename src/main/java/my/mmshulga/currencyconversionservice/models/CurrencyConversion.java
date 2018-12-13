package my.mmshulga.currencyconversionservice.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyConversion {
    private Long id;
    private String from;
    private String to;
    private BigDecimal multiplier;
    private BigDecimal quantity;
    private BigDecimal convertedAmount;
    private int port;
}
