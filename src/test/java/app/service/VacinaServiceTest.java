package app.service;

import app.model.Vacina;
import app.repository.VacinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VacinaServiceTest {

    private VacinaRepository repository;
    private VacinaService service;

    @BeforeEach
    public void setUp() {
        this.repository = new MockRepositories.MockVacinaRepository();
        this.service = new VacinaServiceImpl(repository);
    }

    @Test
    public void testCadastrarVacinaSucesso() {
        Vacina vacina = new Vacina("Antirrábica", "Zoetis");
        Vacina salva = service.cadastrarVacina(vacina);

        assertNotNull(salva.getId());
        assertEquals("Antirrábica", salva.getNome());
        assertEquals("Zoetis", salva.getFabricante());
    }

    @Test
    public void testCadastrarVacinaSemNome() {
        Vacina vacina = new Vacina("", "Zoetis");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarVacina(vacina);
        });
        assertEquals("Nome da vacina é obrigatório.", exception.getMessage());
    }

    @Test
    public void testCadastrarVacinaSemFabricante() {
        Vacina vacina = new Vacina("Antirrábica", "  ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarVacina(vacina);
        });
        assertEquals("Fabricante da vacina é obrigatório.", exception.getMessage());
    }

    @Test
    public void testBuscarVacinaPorIdExistente() {
        Vacina vacina = TestDataFactory.createVacina(5L, "Antirrábica", "Zoetis");
        repository.save(vacina);

        Optional<Vacina> opt = service.buscarVacinaPorId(5L);
        assertTrue(opt.isPresent());
        assertEquals("Antirrábica", opt.get().getNome());
    }

    @Test
    public void testBuscarVacinaPorIdInexistente() {
        Optional<Vacina> opt = service.buscarVacinaPorId(999L);
        assertFalse(opt.isPresent());
    }

    @Test
    public void testListarVacinas() {
        repository.save(new Vacina("Antirrábica", "Zoetis"));
        repository.save(new Vacina("V10", "Boehringer"));

        List<Vacina> list = service.listarVacinas();
        assertEquals(2, list.size());
    }

    @Test
    public void testAtualizarVacina() {
        Vacina vacina = TestDataFactory.createVacina(1L, "Antirrábica", "Zoetis");
        repository.save(vacina);

        vacina.setNome("Antirrábica Premium");
        service.atualizarVacina(vacina);

        Optional<Vacina> opt = repository.findById(1L);
        assertTrue(opt.isPresent());
        assertEquals("Antirrábica Premium", opt.get().getNome());
    }

    @Test
    public void testDeletarVacina() {
        Vacina vacina = TestDataFactory.createVacina(1L, "Antirrábica", "Zoetis");
        repository.save(vacina);

        service.deletarVacina(1L);
        Optional<Vacina> opt = repository.findById(1L);
        assertFalse(opt.isPresent());
    }
}
