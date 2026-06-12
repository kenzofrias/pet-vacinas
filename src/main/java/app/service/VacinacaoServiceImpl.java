package app.service;

import app.model.Vacinacao;
import app.persistence.VacinacaoRepositoryJdbc;
import app.repository.VacinacaoRepository;

import java.util.List;
import java.util.Optional;

public class VacinacaoServiceImpl implements VacinacaoService {
    private final VacinacaoRepository repository;

    public VacinacaoServiceImpl() {
        this.repository = new VacinacaoRepositoryJdbc();
    }

    public VacinacaoServiceImpl(VacinacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vacinacao registrarVacinacao(Vacinacao vacinacao) {
        if (vacinacao.getAnimal() == null || vacinacao.getAnimal().getId() == null) {
            throw new IllegalArgumentException("Animal válido é obrigatório para registrar vacinação.");
        }
        if (vacinacao.getVacina() == null || vacinacao.getVacina().getId() == null) {
            throw new IllegalArgumentException("Vacina válida é obrigatória para registrar vacinação.");
        }
        if (vacinacao.getData() == null) {
            throw new IllegalArgumentException("Data da vacinação é obrigatória.");
        }
        if (vacinacao.getResponsavel() == null || vacinacao.getResponsavel().trim().isEmpty()) {
            throw new IllegalArgumentException("Responsável pela vacinação é obrigatório.");
        }
        return repository.save(vacinacao);
    }

    @Override
    public Optional<Vacinacao> buscarVacinacaoPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Vacinacao> listarVacinacoes() {
        return repository.findAll();
    }

    @Override
    public List<Vacinacao> obterHistoricoAnimal(Long animalId) {
        if (animalId == null) {
            throw new IllegalArgumentException("ID do animal não pode ser nulo.");
        }
        return repository.findByAnimalId(animalId);
    }

    @Override
    public void atualizarVacinacao(Vacinacao vacinacao) {
        repository.update(vacinacao);
    }

    @Override
    public void deletarVacinacao(Long id) {
        repository.delete(id);
    }
}
