package app.service;

import app.model.Vacina;
import app.persistence.VacinaRepositoryJdbc;
import app.repository.VacinaRepository;

import java.util.List;
import java.util.Optional;

public class VacinaServiceImpl implements VacinaService {
    private final VacinaRepository repository;

    public VacinaServiceImpl() {
        this.repository = new VacinaRepositoryJdbc();
    }

    public VacinaServiceImpl(VacinaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vacina cadastrarVacina(Vacina vacina) {
        if (vacina.getNome() == null || vacina.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da vacina é obrigatório.");
        }
        if (vacina.getFabricante() == null || vacina.getFabricante().trim().isEmpty()) {
            throw new IllegalArgumentException("Fabricante da vacina é obrigatório.");
        }
        return repository.save(vacina);
    }

    @Override
    public Optional<Vacina> buscarVacinaPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Vacina> listarVacinas() {
        return repository.findAll();
    }

    @Override
    public void atualizarVacina(Vacina vacina) {
        repository.update(vacina);
    }

    @Override
    public void deletarVacina(Long id) {
        repository.delete(id);
    }
}
