package com.example.crypto_currency_watcher.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Currency{
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;
    @Column(name = "price_usd")
    private BigDecimal price_usd;
}
