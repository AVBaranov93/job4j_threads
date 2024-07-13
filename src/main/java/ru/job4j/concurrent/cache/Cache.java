package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        return memory.computeIfPresent(model.id(), (k, v) -> {
            var stored = findById(model.id()).orElse(null);
            if (stored != null && stored.version() != model.version()) {
                throw new OptimisticException("version is not valid");
            }
            return new Base(k, model.name(), model.version() + 1);
        }) != null;
    }

    public void delete(int id) {
        memory.entrySet().removeIf(e -> e.getValue().version() == id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}