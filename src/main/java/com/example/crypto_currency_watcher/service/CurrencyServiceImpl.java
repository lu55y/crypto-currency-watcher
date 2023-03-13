package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.dto.CoinLoreCurrencyDto;
import com.example.crypto_currency_watcher.model.Currency;
import com.example.crypto_currency_watcher.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository, RestTemplateBuilder templateBuilder) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = templateBuilder.build();
    }

    @Override
    public Currency getById(Long id) {
        log.info("IN CurrencyServiceImpl.getById ()", id);
        return currencyRepository.getReferenceById(id);
    }

    @Override
    public Currency getBySymbol(String name) {
        log.info("IN CurrencyServiceImpl.getByName ()", name);
        return currencyRepository.findBySymbol(name);
    }

    @Override
    public void save(Currency currency) {
        log.info("IN CurrencyServiceImpl.save ()", currency);
        currencyRepository.save(currency);
    }

    @Override
    public void delete(Long id) {
        log.info("IN CurrencyServiceImpl.delete ()", id);
        currencyRepository.deleteById(id);
    }

    @Override
    public List<Currency> getAll() {
        log.info("IN CurrencyServiceImpl.getAll ()");
        return currencyRepository.findAll();
    }

    //TODO
    private void addNewCurrencyFromCoinLore(Long id) {
        ResponseEntity<CoinLoreCurrencyDto> responseEntity = getCurrencyFromCoinLoreApi(id);
        CoinLoreCurrencyDto currencyDto = responseEntity.getBody();
        if (currencyDto != null) {
            Currency currency = currencyDto.toCurrency();
            log.info("IN CurrencyServiceImpl.addNewCurrencyFromCoinLore ()", currency);
            currencyRepository.save(currency);
        } else log.info("ERROR IN CurrencyServiceImpl.addNewCurrencyFromCoinLore ()  currencyDto - NULL");
    }

    public void updateCurrencyInformation() {
        List<Currency> currencyList = getAll();
        Iterator<Currency> iterator = currencyList.iterator();
        Long id;
        ResponseEntity<CoinLoreCurrencyDto> responseEntity;
        while (iterator.hasNext()) {
            id = iterator.next().getId();
            addNewCurrencyFromCoinLore(id);
        }
    }

    private ResponseEntity<CoinLoreCurrencyDto> getCurrencyFromCoinLoreApi(Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return restTemplate.getForEntity("https://api.coinlore.net/api/ticker/?id=" + id,
                CoinLoreCurrencyDto.class, HttpStatus.OK);
    }

}