package org.example;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    private String code;

    @Column(name = "buy_rate")
    private double buyRate;

    @Column(name = "sell_rate")
    private double sellRate;

    @Column
    private String name;

    public Currency() {}

    public Currency(String code, double buyRate, double sellRate, String name) {
        this.code = code;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Double.compare(buyRate, currency.buyRate) == 0 && Double.compare(sellRate, currency.sellRate) == 0 && Objects.equals(code, currency.code) && Objects.equals(name, currency.name);
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

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
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
