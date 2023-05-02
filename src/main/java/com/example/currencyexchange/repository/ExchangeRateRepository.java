package com.example.currencyexchange.repository;

import java.time.LocalDate;
import com.example.currencyexchange.model.Currency;
import com.example.currencyexchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findByDateAndCurrencyAndProvider
            (LocalDate date, Currency currency, String provider);
}
