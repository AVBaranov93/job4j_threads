package ru.job4j.concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
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
        Optional<Account> fromAccount = getById(fromId);
        Optional<Account> toAccount = getById(toId);
        boolean transferSuccess = fromAccount.isPresent() && toAccount.isPresent() && fromAccount.get().amount() >= amount;
        if (transferSuccess) {
            accounts.put(fromId, new Account(fromId, fromAccount.get().amount() - amount));
            accounts.put(toId, new Account(toId, toAccount.get().amount() + amount));
        }
        return transferSuccess;
    }
}
