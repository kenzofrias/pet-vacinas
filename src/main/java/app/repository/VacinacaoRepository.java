package app.repository;

import app.model.Vacinacao;
import java.util.List;
import java.util.Optional;

public interface VacinacaoRepository {
    Vacinacao save(Vacinacao vacinacao);
    Optional<Vacinacao> findById(Long id);
    List<Vacinacao> findAll();
    List<Vacinacao> findByAnimalId(Long animalId);
    void update(Vacinacao vacinacao);
    void delete(Long id);
}
