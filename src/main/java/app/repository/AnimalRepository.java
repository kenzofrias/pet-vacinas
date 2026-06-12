package app.repository;

import app.model.Animal;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository {
    Animal save(Animal animal);
    Optional<Animal> findById(Long id);
    List<Animal> findAll();
    void update(Animal animal);
    void delete(Long id);
}
