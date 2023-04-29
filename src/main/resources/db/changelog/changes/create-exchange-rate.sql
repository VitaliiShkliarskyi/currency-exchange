-liquibase formatted sql
-changeset <postgres>:<create-exchange-rate-table>
CREATE TABLE IF NOT EXIST public.currence_exchange
(
    id BIGINT NOT NULL,
    saleRate DOUBLE PRECISION NOT NULL,
    purchaseRate DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL,
    bank VARCHAR(255) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    CONSTRAINT currency_exchange_pk PRIMARY KEY (id)
);

-rollback DROP TABLE currency_exchange;