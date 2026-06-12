package app.service;

import app.model.Animal;
import app.persistence.AnimalRepositoryJdbc;
import app.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;

public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository repository;

    public AnimalServiceImpl() {
        this.repository = new AnimalRepositoryJdbc();
    }

    public AnimalServiceImpl(AnimalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Animal cadastrarAnimal(Animal animal) {
        if (animal.getNome() == null || animal.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do animal é obrigatório.");
        }
        if (animal.getRaca() == null || animal.getRaca().trim().isEmpty()) {
            throw new IllegalArgumentException("Raça do animal é obrigatória.");
        }
        if (animal.getDono() == null || animal.getDono().trim().isEmpty()) {
            throw new IllegalArgumentException("Dono do animal é obrigatório.");
        }
        return repository.save(animal);
    }

    @Override
    public Optional<Animal> buscarAnimalPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Animal> listarAnimais() {
        return repository.findAll();
    }

    @Override
    public void atualizarAnimal(Animal animal) {
        repository.update(animal);
    }

    @Override
    public void deletarAnimal(Long id) {
        repository.delete(id);
    }
}
