package com.example.currencyexchange.repository;

import java.time.LocalDate;
import java.util.List;
import com.example.currencyexchange.model.Currency;
import com.example.currencyexchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findByDateAndCurrencyAndProvider
            (LocalDate date, Currency currency, String provider);

    List<ExchangeRate> findAllByProviderAndDate(String provider, LocalDate date);

    List<ExchangeRate> findAllByProviderAndDateBetween(
            String provider, LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT AVG(e.saleRate) FROM ExchangeRate e WHERE e.currency = ?1 AND e.date = ?2")
    Float getAverageMarketSaleRate(Currency currency, LocalDate date);

    @Query("SELECT AVG(e.purchaseRate) FROM ExchangeRate e WHERE e.currency = ?1 AND e.date = ?2")
    Float getAverageMarketPurchaseRate(Currency currency, LocalDate date);
}
