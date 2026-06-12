package app.service;

import app.model.Vacina;
import java.util.List;
import java.util.Optional;

public interface VacinaService {
    Vacina cadastrarVacina(Vacina vacina);
    Optional<Vacina> buscarVacinaPorId(Long id);
    List<Vacina> listarVacinas();
    void atualizarVacina(Vacina vacina);
    void deletarVacina(Long id);
}
