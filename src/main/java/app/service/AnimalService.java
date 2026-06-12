package app.service;

import app.model.Animal;
import java.util.List;
import java.util.Optional;

public interface AnimalService {
    Animal cadastrarAnimal(Animal animal);
    Optional<Animal> buscarAnimalPorId(Long id);
    List<Animal> listarAnimais();
    void atualizarAnimal(Animal animal);
    void deletarAnimal(Long id);
}
