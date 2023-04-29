package com.example.currencyexchange.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(generator = "exchange_rate_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "exchange_rate_id_seq",
            sequenceName = "exchange_rate_id_seq",
            allocationSize = 1)
    private Long id;
    private Double saleRate;
    private Double purchaseRate;
    private LocalDate date;
    private String bank;
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
