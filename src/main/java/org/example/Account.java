package org.example;

import jakarta.persistence.*;

import javax.xml.namespace.QName;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_number")
    private int accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column
    private double balance;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<Transaction>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", nullable = false)
    private Currency currency;

    public Account() {}

    public Account(Client client, double balance, Currency currency) {
        this.client = client;
        this.balance = balance;
        this.currency = currency;
    }

    public int getId() {
        return accountNumber;
    }

    public void setId(int id) {
        this.accountNumber = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
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
