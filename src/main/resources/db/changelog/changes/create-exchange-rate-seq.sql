-liquibase formatted sql
-changeset <postgres>:<create-exchange-rate-sequence-id>
CREATE SEQUENCE IF NOT EXIST public.exchange_rate_id_seq INCREMENT 1 START 1 MINVALUE 1;

-rollback DROP SEQUENCE public.exchange_rate_id_seq;