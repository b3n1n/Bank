package org.example.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_number")
    private Long accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column
    private BigDecimal balance;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<Transaction>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    public Account() {}

    public Account(Client client, BigDecimal balance, Currency currency) {
        this.client = client;
        this.balance = balance;
        this.currency = currency;
    }

    public Long getId() {
        return accountNumber;
    }

    public void setId(Long id) {
        this.accountNumber = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountNumber +
                ", client=" + client.getId() +
                ", balance=" + balance +
                ", transactions=" + transactions +
                ", currency=" + currency +
                '}' + "\n";
    }
}
