package org.example.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    private String code;

    @Column(name = "buy_rate")
    private BigDecimal buyRate;

    @Column(name = "sell_rate")
    private BigDecimal sellRate;

    @Column
    private String name;

    public Currency() {}

    public Currency(String code, BigDecimal buyRate, BigDecimal sellRate, String name) {
        this.code = code;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code) && Objects.equals(buyRate, currency.buyRate) && Objects.equals(sellRate, currency.sellRate) && Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, buyRate, sellRate, name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", buyRate=" + buyRate +
                ", sellRate=" + sellRate +
                ", name='" + name + '\'' +
                '}' + "\n";
    }
}
