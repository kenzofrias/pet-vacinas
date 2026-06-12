package app.service;

import app.model.Animal;
import app.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalServiceTest {

    private AnimalRepository repository;
    private AnimalService service;

    @BeforeEach
    public void setUp() {
        this.repository = new MockRepositories.MockAnimalRepository();
        this.service = new AnimalServiceImpl(repository);
    }

    @Test
    public void testCadastrarAnimalSucesso() {
        Animal animal = new Animal("Bidu", "Poodle", "Murilo");
        Animal salvo = service.cadastrarAnimal(animal);

        assertNotNull(salvo.getId());
        assertEquals("Bidu", salvo.getNome());
        assertEquals("Poodle", salvo.getRaca());
        assertEquals("Murilo", salvo.getDono());
    }

    @Test
    public void testCadastrarAnimalSemNome() {
        Animal animal = new Animal("", "Poodle", "Murilo");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarAnimal(animal);
        });
        assertEquals("Nome do animal é obrigatório.", exception.getMessage());
    }

    @Test
    public void testCadastrarAnimalSemRaca() {
        Animal animal = new Animal("Bidu", "  ", "Murilo");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarAnimal(animal);
        });
        assertEquals("Raça do animal é obrigatória.", exception.getMessage());
    }

    @Test
    public void testCadastrarAnimalSemDono() {
        Animal animal = new Animal("Bidu", "Poodle", null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarAnimal(animal);
        });
        assertEquals("Dono do animal é obrigatório.", exception.getMessage());
    }

    @Test
    public void testBuscarAnimalPorIdExistente() {
        Animal animal = TestDataFactory.createAnimal(10L, "Bidu", "Poodle", "Murilo");
        repository.save(animal);

        Optional<Animal> opt = service.buscarAnimalPorId(10L);
        assertTrue(opt.isPresent());
        assertEquals("Bidu", opt.get().getNome());
    }

    @Test
    public void testBuscarAnimalPorIdInexistente() {
        Optional<Animal> opt = service.buscarAnimalPorId(999L);
        assertFalse(opt.isPresent());
    }

    @Test
    public void testListarAnimais() {
        repository.save(new Animal("Bidu", "Poodle", "Murilo"));
        repository.save(new Animal("Rex", "SRD", "Kenzo"));

        List<Animal> list = service.listarAnimais();
        assertEquals(2, list.size());
    }

    @Test
    public void testAtualizarAnimal() {
        Animal animal = TestDataFactory.createAnimal(1L, "Bidu", "Poodle", "Murilo");
        repository.save(animal);

        animal.setNome("Bidu Novo");
        service.atualizarAnimal(animal);

        Optional<Animal> opt = repository.findById(1L);
        assertTrue(opt.isPresent());
        assertEquals("Bidu Novo", opt.get().getNome());
    }

    @Test
    public void testDeletarAnimal() {
        Animal animal = TestDataFactory.createAnimal(1L, "Bidu", "Poodle", "Murilo");
        repository.save(animal);

        service.deletarAnimal(1L);
        Optional<Animal> opt = repository.findById(1L);
        assertFalse(opt.isPresent());
    }
}
