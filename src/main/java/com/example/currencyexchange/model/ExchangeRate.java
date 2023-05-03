package com.example.currencyexchange.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
    private Float saleRate;
    private Float purchaseRate;
    private LocalDate date;
    private String provider;
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
