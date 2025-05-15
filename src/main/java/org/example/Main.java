package org.example;

import org.example.CRUD.GeneralCRUD;

public class Main {
    public static void main(String[] args) {
        GeneralCRUD crud = new GeneralCRUD();
        AccountService accountService = new AccountService();

        System.out.println(accountService.getBalanceInUAHFromOneClient(1));
    }
}