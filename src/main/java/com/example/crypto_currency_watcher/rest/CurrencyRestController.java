package com.example.crypto_currency_watcher.rest;

import com.example.crypto_currency_watcher.model.Currency;
import com.example.crypto_currency_watcher.service.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency/")
public class CurrencyRestController {

    private final CurrencyServiceImpl currencyService;

    @Autowired
    public CurrencyRestController(CurrencyServiceImpl currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> getCurrencyById(@PathVariable("id") Long currencyId) {
        if (currencyId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Currency currency = this.currencyService.getById(currencyId);
        if (currency == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @RequestMapping(value = "{symbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> getCurrencyBySymbol(@PathVariable("symbol") String currencySymbol) {
        if (currencySymbol.length() == 0 || currencySymbol.length() > 20) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Currency currency = this.currencyService.getBySymbol(currencySymbol.toUpperCase());
        if (currency == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> saveCurrency(@RequestBody @Validated Currency currency) {
        if (currency == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.currencyService.save(currency);
        return new ResponseEntity<>(currency, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> updateCurrency(@RequestBody @Validated Currency currency) {
        if (currency == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.currencyService.save(currency);
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @RequestMapping(value = "id", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> deleteCurrency(Long id) {
        Currency currency = this.currencyService.getById(id);
        if (currency == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.currencyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = this.currencyService.getAll();
        if (currencies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currencies,HttpStatus.OK);
    }
}
