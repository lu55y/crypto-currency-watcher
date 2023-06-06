create table if not exists currencies
(
    id        bigint primary key,
    symbol    varchar(20)    not null,
    name      varchar(20)    not null,
    price_usd decimal(12, 2) not null default (0.01)
);
create table if not exists users
(
    id                 bigint auto_increment primary key,
    username           varchar(50)    not null,
    currency_symbol    varchar(20)    not null,
    currency_price_usd decimal(12, 2) not null
);
