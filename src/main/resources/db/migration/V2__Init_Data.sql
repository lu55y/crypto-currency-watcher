use currency_watcher_db;
insert into currencies
values (90, 'BTC', 'Bitcoin',default),
       (80, 'ETH', 'Ethereum',default),
       (48543, 'SOL', 'Solana',default);
insert into users (userName, currency_symbol, currency_price_usd)
VALUES ('Alex','BTC',26717.75);