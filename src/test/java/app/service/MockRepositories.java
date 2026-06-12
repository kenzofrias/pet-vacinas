package app.service;

import app.model.Animal;
import app.model.Vacina;
import app.model.Vacinacao;
import app.repository.AnimalRepository;
import app.repository.VacinaRepository;
import app.repository.VacinacaoRepository;

import java.util.*;

public class MockRepositories {

    public static class MockAnimalRepository implements AnimalRepository {
        private final Map<Long, Animal> database = new HashMap<>();
        private long idSequence = 1;

        @Override
        public Animal save(Animal animal) {
            if (animal.getId() == null) {
                animal.setId(idSequence++);
            }
            database.put(animal.getId(), animal);
            return animal;
        }

        @Override
        public Optional<Animal> findById(Long id) {
            return Optional.ofNullable(database.get(id));
        }

        @Override
        public List<Animal> findAll() {
            return new ArrayList<>(database.values());
        }

        @Override
        public void update(Animal animal) {
            database.put(animal.getId(), animal);
        }

        @Override
        public void delete(Long id) {
            database.remove(id);
        }
    }

    public static class MockVacinaRepository implements VacinaRepository {
        private final Map<Long, Vacina> database = new HashMap<>();
        private long idSequence = 1;

        @Override
        public Vacina save(Vacina vacina) {
            if (vacina.getId() == null) {
                vacina.setId(idSequence++);
            }
            database.put(vacina.getId(), vacina);
            return vacina;
        }

        @Override
        public Optional<Vacina> findById(Long id) {
            return Optional.ofNullable(database.get(id));
        }

        @Override
        public List<Vacina> findAll() {
            return new ArrayList<>(database.values());
        }

        @Override
        public void update(Vacina vacina) {
            database.put(vacina.getId(), vacina);
        }

        @Override
        public void delete(Long id) {
            database.remove(id);
        }
    }

    public static class MockVacinacaoRepository implements VacinacaoRepository {
        private final Map<Long, Vacinacao> database = new HashMap<>();
        private long idSequence = 1;

        @Override
        public Vacinacao save(Vacinacao vacinacao) {
            if (vacinacao.getId() == null) {
                vacinacao.setId(idSequence++);
            }
            database.put(vacinacao.getId(), vacinacao);
            return vacinacao;
        }

        @Override
        public Optional<Vacinacao> findById(Long id) {
            return Optional.ofNullable(database.get(id));
        }

        @Override
        public List<Vacinacao> findAll() {
            return new ArrayList<>(database.values());
        }

        @Override
        public List<Vacinacao> findByAnimalId(Long animalId) {
            List<Vacinacao> result = new ArrayList<>();
            for (Vacinacao v : database.values()) {
                if (v.getAnimal() != null && animalId.equals(v.getAnimal().getId())) {
                    result.add(v);
                }
            }
            return result;
        }

        @Override
        public void update(Vacinacao vacinacao) {
            database.put(vacinacao.getId(), vacinacao);
        }

        @Override
        public void delete(Long id) {
            database.remove(id);
        }
    }
}
