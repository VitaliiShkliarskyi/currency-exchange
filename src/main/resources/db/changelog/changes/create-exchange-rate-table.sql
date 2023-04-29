--liquibase formatted sql
--changeset <postgres>:<create-exchange-rate-table>
CREATE TABLE IF NOT EXISTS public.exchange_rate
(
    id BIGINT NOT NULL,
    sale_rate DOUBLE PRECISION NOT NULL,
    purchase_rate DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL,
    bank VARCHAR(255) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    CONSTRAINT currency_exchange_pk PRIMARY KEY (id)
);

--rollback DROP TABLE currency_exchange;