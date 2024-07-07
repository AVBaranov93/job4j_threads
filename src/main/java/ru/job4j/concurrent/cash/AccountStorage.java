package ru.job4j.concurrent.cash;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.computeIfPresent(account.id(), (k, v) -> account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return accounts.entrySet().stream()
                .filter(e -> e.getKey() == id)
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean transferSuccess = accounts.containsKey(fromId) && accounts.containsKey(toId)
                && accounts.get(fromId).amount() >= amount;
        if (transferSuccess) {
            accounts.computeIfPresent(fromId, (id, fromAccount) -> new Account(id, fromAccount.amount() - amount));
            accounts.computeIfPresent(toId, (id, toAccount) -> new Account(id, toAccount.amount() + amount));
        }
        return transferSuccess;
    }
}
