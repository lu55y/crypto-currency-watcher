package com.example.crypto_currency_watcher.configuration;

import com.example.crypto_currency_watcher.CryptoCurrencyWatcherApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CryptoCurrencyWatcherApplication.class);
    }

}
