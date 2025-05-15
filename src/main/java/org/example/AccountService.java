package org.example;

import jakarta.persistence.*;
import org.example.Entities.Account;
import org.example.Entities.Client;
import org.example.Entities.Currency;

import java.math.BigDecimal;
import java.util.Scanner;

public class AccountService {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private final EntityManager em = emf.createEntityManager();

    public void deleteAccount(int id) {
        em.getTransaction().begin();
        Account account1 = em.find(Account.class, id);
        account1.setClient(null);
        account1.setCurrency(null);
        em.remove(account1);
        em.getTransaction().commit();
    }

    public void chargeAccount(int id, BigDecimal amount, Currency currency) {
        em.getTransaction().begin();
        Account account1 = em.find(Account.class, id);
        if(account1 == null) {
            em.getTransaction().rollback();
            throw new RuntimeException("Account not found");
        }
        if(!(account1.getCurrency().getName().equals(currency.getName()))) {
            em.getTransaction().rollback();
            throw new RuntimeException("Currency does not match");
        }
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            em.getTransaction().rollback();
            throw new RuntimeException("Amount cannot be negative");
        }
        account1.setBalance(account1.getBalance().add(amount));
        em.merge(account1);
        em.getTransaction().commit();
    }

    public void transferMoney(Account from, Account to, BigDecimal amount) {
        em.getTransaction().begin();
        Account account1 = em.find(Account.class, from.getId());
        Account account2 = em.find(Account.class, to.getId());
        if(account1 == null) {
            em.getTransaction().rollback();
            throw new RuntimeException("Account not found");
        }
        if(account2 == null) {
            em.getTransaction().rollback();
            throw new RuntimeException("Account not found");
        }
        if(account1.getCurrency().getName().equals(account2.getCurrency().getName())) {
            account1.setBalance(account1.getBalance().subtract(amount));
            account2.setBalance(account2.getBalance().add(amount));
        }
        if(!(account1.getCurrency().equals(account2.getCurrency()))) {
            System.out.println("Currency does not match. To transfer money you need to use our exchange rate");
            BigDecimal exchangedValue = (account1.getCurrency().getBuyRate().divide(account2.getCurrency().getSellRate())).multiply(amount);
            System.out.println("Amount to transfer:  " + exchangedValue);
            try(Scanner scanner = new Scanner(System.in)) {
                System.out.println("If you would like to continue transfer \nPress 1 - to coninue\nPress 2 - to cancel");
                int choice = scanner.nextInt();
                if(choice == 1) {
                    account1.setBalance(account1.getBalance().subtract(amount));
                    account2.setBalance(account2.getBalance().add(exchangedValue));
                }
            }
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            em.getTransaction().rollback();
            throw new RuntimeException("Amount cannot be negative");
        }
        em.merge(account1);
        em.merge(account2);
        em.getTransaction().commit();
    }

    public BigDecimal getBalanceInUAHFromOneClient(int id) {
        em.getTransaction().begin();
        BigDecimal amount = BigDecimal.ZERO;
        Client client = em.find(Client.class, id);
        if(client == null) {
            em.getTransaction().rollback();
            throw new RuntimeException("Client not found");
        }
        for (Account a : client.getAccounts()) {
            BigDecimal rate = a.getCurrency().getBuyRate();
            amount = amount.add(a.getBalance().multiply(rate));
        }
        return amount;
    }
}
