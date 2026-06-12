package app.service;

import app.model.Animal;
import app.model.Vacina;
import app.model.Vacinacao;
import app.repository.VacinacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VacinacaoServiceTest {

    private VacinacaoRepository repository;
    private VacinacaoService service;

    private Animal animal;
    private Vacina vacina;

    @BeforeEach
    public void setUp() {
        this.repository = new MockRepositories.MockVacinacaoRepository();
        this.service = new VacinacaoServiceImpl(repository);

        this.animal = TestDataFactory.createAnimal(1L, "Bidu ", "Poodle", "Murilo");
        this.vacina = TestDataFactory.createVacina(1L, "Antirrábica", "Zoetis");
    }
 
    @Test
    public void testRegistrarVacinacaoSucesso() {
        Vacinacao vacinacao = new Vacinacao(animal, vacina, LocalDate.now(), "Dr. Vet");
        Vacinacao salva = service.registrarVacinacao(vacinacao);

        assertNotNull(salva.getId());
        assertEquals(animal, salva.getAnimal());
        assertEquals(vacina, salva.getVacina());
        assertEquals("Dr. Vet", salva.getResponsavel());
    }

    @Test
    public void testRegistrarVacinacaoSemAnimal() {
        Vacinacao vacinacao = new Vacinacao(null, vacina, LocalDate.now(), "Dr. Vet");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registrarVacinacao(vacinacao);
        });
        assertEquals("Animal válido é obrigatório para registrar vacinação.", exception.getMessage());
    }

    @Test
    public void testRegistrarVacinacaoSemAnimalId() {
        Animal semId = new Animal("Rex", "SRD", "Kenzo");
        Vacinacao vacinacao = new Vacinacao(semId, vacina, LocalDate.now(), "Dr. Vet");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registrarVacinacao(vacinacao);
        });
        assertEquals("Animal válido é obrigatório para registrar vacinação.", exception.getMessage());
    }

    @Test
    public void testRegistrarVacinacaoSemVacina() {
        Vacinacao vacinacao = new Vacinacao(animal, null, LocalDate.now(), "Dr. Vet");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registrarVacinacao(vacinacao);
        });
        assertEquals("Vacina válida é obrigatória para registrar vacinação.", exception.getMessage());
    }

    @Test
    public void testRegistrarVacinacaoSemData() {
        Vacinacao vacinacao = new Vacinacao(animal, vacina, null, "Dr. Vet");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registrarVacinacao(vacinacao);
        });
        assertEquals("Data da vacinação é obrigatória.", exception.getMessage());
    }

    @Test
    public void testRegistrarVacinacaoSemResponsavel() {
        Vacinacao vacinacao = new Vacinacao(animal, vacina, LocalDate.now(), "");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.registrarVacinacao(vacinacao);
        });
        assertEquals("Responsável pela vacinação é obrigatório.", exception.getMessage());
    }

    @Test
    public void testBuscarVacinacaoPorIdExistente() {
        Vacinacao vacinacao = TestDataFactory.createVacinacao(10L, animal, vacina, LocalDate.now(), "Dr. Vet");
        repository.save(vacinacao);

        Optional<Vacinacao> opt = service.buscarVacinacaoPorId(10L);
        assertTrue(opt.isPresent());
        assertEquals("Dr. Vet", opt.get().getResponsavel());
    }

    @Test
    public void testBuscarVacinacaoPorIdInexistente() {
        Optional<Vacinacao> opt = service.buscarVacinacaoPorId(999L);
        assertFalse(opt.isPresent());
    }

    @Test
    public void testListarVacinacoes() {
        Animal animal2 = TestDataFactory.createAnimal(2L, "Rex", "SRD", "Kenzo");
        repository.save(new Vacinacao(animal, vacina, LocalDate.now(), "Dr. Vet"));
        repository.save(new Vacinacao(animal2, vacina, LocalDate.now(), "Dr. Vet"));

        List<Vacinacao> list = service.listarVacinacoes();
        assertEquals(2, list.size());
    }

    @Test
    public void testObterHistoricoAnimal() {
        Animal animal2 = TestDataFactory.createAnimal(2L, "Rex", "SRD", "Kenzo");
        repository.save(new Vacinacao(animal, vacina, LocalDate.of(2024, 1, 10), "Dr. Vet"));
        repository.save(new Vacinacao(animal, vacina, LocalDate.of(2024, 6, 20), "Dr. Ana"));
        repository.save(new Vacinacao(animal2, vacina, LocalDate.now(), "Dr. Vet"));

        List<Vacinacao> historico = service.obterHistoricoAnimal(1L);
        assertEquals(2, historico.size());
    }

    @Test
    public void testObterHistoricoAnimalSemVacinacoes() {
        List<Vacinacao> historico = service.obterHistoricoAnimal(1L);
        assertTrue(historico.isEmpty());
    }

    @Test
    public void testObterHistoricoAnimalIdNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.obterHistoricoAnimal(null);
        });
        assertEquals("ID do animal não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void testDeletarVacinacao() {
        Vacinacao vacinacao = TestDataFactory.createVacinacao(1L, animal, vacina, LocalDate.now(), "Dr. Vet");
        repository.save(vacinacao);

        service.deletarVacinacao(1L);
        Optional<Vacinacao> opt = repository.findById(1L);
        assertFalse(opt.isPresent());
    }
}
