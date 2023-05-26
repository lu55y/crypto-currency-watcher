use currency_watcher_db;
insert into currencies
values (90, 'BTC', 'Bitcoin', 0.01),
       (80, 'ETH', 'Ethereum', 0.01),
       (48543, 'SOL', 'Solana', 0.01);
insert into users (userName, currency_symbol, currency_price_usd)
    VALUES ('vasya','BTC',0.01);