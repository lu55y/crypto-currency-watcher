package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.dto.CurrencyResponse;
import com.example.crypto_currency_watcher.model.Currency;
import com.example.crypto_currency_watcher.model.User;
import com.example.crypto_currency_watcher.repository.CurrencyRepository;
import com.example.crypto_currency_watcher.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;
    private final WebClient webClient;

    public CurrencyResponse findCurrencyBySymbol(String symbol) {
        Optional<Currency> bySymbol = currencyRepository.findBySymbol(symbol);
        return mapToCurrencyResponse(bySymbol.orElseThrow());
    }

    public List<CurrencyResponse> findAllCurrencies(){
        return currencyRepository.findAll().stream()
                .map(this::mapToCurrencyResponse)
                .toList();
    }

    @Scheduled(fixedRate = 60000)
    public void updateCurrencies() {
        List<Currency> currencyList = getCurrencyActualInfoOnCoinloreAPI();
        System.out.println(currencyList);
        if (currencyList != null) {
            currencyRepository.saveAllAndFlush(currencyList);
        }
        checkDifferenceUsersCurrency();
    }

    private CurrencyResponse mapToCurrencyResponse(Currency currency) {
        return CurrencyResponse.builder()
                .id(currency.getId())
                .symbol(currency.getSymbol())
                .name(currency.getName())
                .price_usd(currency.getPrice_usd())
                .build();
    }

    private List<Currency> getCurrencyActualInfoOnCoinloreAPI() {
        String stringsIdForURI = getStringsIdForURI();
        List<CurrencyResponse> currencyResponse = makeRequestOnCoinloreAPI(stringsIdForURI);

        return currencyResponse != null ? currencyResponse.stream()
                .map(this::currencyResponseToCurrency)
                .toList() : null;
    }

    private String getStringsIdForURI() {
        List<Long> currenciesId = currencyRepository.findAll()
                .stream()
                .map(Currency::getId)
                .toList();
        return currenciesId.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    private List<CurrencyResponse> makeRequestOnCoinloreAPI(String stringsIdForURI) {
        ResponseEntity<List<CurrencyResponse>> responseEntity = webClient.get()
                .uri("https://api.coinlore.net/api/ticker/?id=" + stringsIdForURI)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(CurrencyResponse.class).block();
        if (responseEntity != null) {
            return responseEntity.getBody();
        }
        return null;
    }

    private Currency currencyResponseToCurrency(CurrencyResponse currencyResponse) {
        return new Currency(currencyResponse.getId(),
                currencyResponse.getSymbol(),
                currencyResponse.getName(),
                currencyResponse.getPrice_usd());
    }

    private void checkDifferenceUsersCurrency() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            users.forEach(this::compareCurrencyDifference);
        }
    }

    private void compareCurrencyDifference(User user) {
        String currencySymbol = user.getCurrencySymbol();
        Currency currency = currencyRepository.findBySymbol(currencySymbol).orElseThrow();
        BigDecimal userCurrencyPriceUsd = new BigDecimal(user.getCurrencyPrice_usd().toString());      //x
        BigDecimal currencyPriceUsd = new BigDecimal(currency.getPrice_usd().toString());              //y

        BigDecimal result = compareCurrencies(userCurrencyPriceUsd, currencyPriceUsd);
        double dResult = result.doubleValue();
        if (dResult > 1) log.warn("Currency changes -- | %s | %s | %s %% | --"
                .formatted(user.getCurrencySymbol(), user.getUsername(), dResult));
    }

    private static BigDecimal compareCurrencies(BigDecimal userCurrencyPriceUsd, BigDecimal currencyPriceUsd) {
        BigDecimal result;
        int compareValue = userCurrencyPriceUsd.compareTo(currencyPriceUsd);
        switch (compareValue) {
            // x<y   R=((y-x)/x)*100
            case -1 -> result = currencyPriceUsd.subtract(userCurrencyPriceUsd)
                    .divide(userCurrencyPriceUsd, 4, RoundingMode.CEILING)
                    .multiply(new BigDecimal("100.00"))
                    .setScale(2, RoundingMode.CEILING);
            // x>y   R=((x-y)/x)*100
            case 1 -> result = userCurrencyPriceUsd.subtract(currencyPriceUsd)
                    .divide(userCurrencyPriceUsd, 4, RoundingMode.CEILING)
                    .multiply(new BigDecimal("100.00"))
                    .setScale(2, RoundingMode.CEILING);
            //x=y
            default -> result = new BigDecimal("0.00");
        }
        return result;
    }
}
