package app.service;

import app.model.Vacinacao;
import java.util.List;
import java.util.Optional;

public interface VacinacaoService {
    Vacinacao registrarVacinacao(Vacinacao vacinacao);
    Optional<Vacinacao> buscarVacinacaoPorId(Long id);
    List<Vacinacao> listarVacinacoes();
    List<Vacinacao> obterHistoricoAnimal(Long animalId);
    void atualizarVacinacao(Vacinacao vacinacao);
    void deletarVacinacao(Long id);
}
