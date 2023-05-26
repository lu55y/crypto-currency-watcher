create database if not exists currency_watcher_db;
use currency_watcher_db;
create table if not exists currencies(
    id bigint primary key,
    symbol varchar(20) not null,
    username varchar(20) not null,
    price_usd decimal(12,2) not null
    );
create table if not exists users(
    id bigint auto_increment primary key ,
    userName varchar(50) not null ,
    currency_symbol varchar(20) not null ,
    currency_price_usd decimal(12,2) not null
);
