package org.example;

import jakarta.persistence.*;

import java.text.DecimalFormat;
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

    public void chargeAccount(int id, double amount, Currency currency) {
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
        if(amount <= 0) {
            em.getTransaction().rollback();
            throw new RuntimeException("Amount cannot be negative");
        }
        account1.setBalance(account1.getBalance() + amount);
        em.merge(account1);
        em.getTransaction().commit();
    }

    public void transferMoney(Account from, Account to, double amount) {
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
            account1.setBalance(account1.getBalance() - amount);
            account2.setBalance(account2.getBalance() + amount);
        }
        if(!(account1.getCurrency().equals(account2.getCurrency()))) {
            System.out.println("Currency does not match. To transfer money you need to use our exchange rate");
            double exchangedValue = (account1.getCurrency().getBuyRate() / account2.getCurrency().getSellRate()) * amount;
            System.out.println("Amount to transfer:  " + exchangedValue);
            try(Scanner scanner = new Scanner(System.in)) {
                System.out.println("If you would like to continue transfer \nPress 1 - to coninue\nPress 2 - to cancel");
                int choice = scanner.nextInt();
                if(choice == 1) {
                    account1.setBalance(account1.getBalance() - amount);
                    account2.setBalance(account2.getBalance() + exchangedValue);
                }
            }
        }
        if(amount <= 0) {
            em.getTransaction().rollback();
            throw new RuntimeException("Amount cannot be negative");
        }
        em.merge(account1);
        em.merge(account2);
        em.getTransaction().commit();
    }

    public double getBalanceInUAHFromOneClient(int id) {
        em.getTransaction().begin();
        double amount = 0;
        Client client = em.find(Client.class, id);
        if(client == null) {
            em.getTransaction().rollback();
            throw new RuntimeException("Client not found");
        }
        for (Account a : client.getAccounts()) {
            double rate = a.getCurrency().getBuyRate();
            amount += a.getBalance() * rate;
        }
        return amount;
    }
}
