package app.repository;

import app.model.Vacina;
import java.util.List;
import java.util.Optional;

public interface VacinaRepository {
    Vacina save(Vacina vacina);
    Optional<Vacina> findById(Long id);
    List<Vacina> findAll();
    void update(Vacina vacina);
    void delete(Long id);
}
