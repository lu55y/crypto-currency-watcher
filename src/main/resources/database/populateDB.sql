use currency_watcher_db;
insert into currency
values (90, 'BTC', 'Bitcoin', 0.0),
       (80, 'ETH', 'Ethereum', 0.0),
       (48543, 'SOL', 'Solana', 0.0);
insert into users (userName, currency_symbol, currency_price_usd)
    VALUES ('vasya','BTC',0.0);