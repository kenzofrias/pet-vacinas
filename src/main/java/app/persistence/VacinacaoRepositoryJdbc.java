package app.persistence;

import app.database.ConnectionFactory;
import app.model.Animal;
import app.model.Vacina;
import app.model.Vacinacao;
import app.repository.VacinacaoRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VacinacaoRepositoryJdbc implements VacinacaoRepository {

    private Vacinacao mapResultSetToVacinacao(ResultSet rs) throws SQLException {
        Animal animal = new Animal(
                rs.getLong("animal_id"),
                rs.getString("animal_nome"),
                rs.getString("animal_raca"),
                rs.getString("animal_dono")
        );

        Vacina vacina = new Vacina(
                rs.getLong("vacina_id"),
                rs.getString("vacina_nome"),
                rs.getString("vacina_fabricante")
        );

        return new Vacinacao(
                rs.getLong("id"),
                animal,
                vacina,
                rs.getDate("data_vacinacao").toLocalDate(),
                rs.getString("responsavel")
        );
    }

    @Override
    public Vacinacao save(Vacinacao vacinacao) {
        String sql = "INSERT INTO vacinacao (animal_id, vacina_id, data_vacinacao, responsavel) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, vacinacao.getAnimal().getId());
            stmt.setLong(2, vacinacao.getVacina().getId());
            stmt.setDate(3, Date.valueOf(vacinacao.getData()));
            stmt.setString(4, vacinacao.getResponsavel());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vacinacao.setId(generatedKeys.getLong(1));
                }
            }
            return vacinacao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar vacinação", e);
        }
    }

    @Override
    public Optional<Vacinacao> findById(Long id) {
        String sql = "SELECT vn.id, vn.data_vacinacao, vn.responsavel, " +
                "a.id AS animal_id, a.nome AS animal_nome, a.raca AS animal_raca, a.dono AS animal_dono, " +
                "v.id AS vacina_id, v.nome AS vacina_nome, v.fabricante AS vacina_fabricante " +
                "FROM vacinacao vn " +
                "JOIN animal a ON vn.animal_id = a.id " +
                "JOIN vacina v ON vn.vacina_id = v.id " +
                "WHERE vn.id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToVacinacao(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vacinação por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Vacinacao> findAll() {
        List<Vacinacao> list = new ArrayList<>();
        String sql = "SELECT vn.id, vn.data_vacinacao, vn.responsavel, " +
                "a.id AS animal_id, a.nome AS animal_nome, a.raca AS animal_raca, a.dono AS animal_dono, " +
                "v.id AS vacina_id, v.nome AS vacina_nome, v.fabricante AS vacina_fabricante " +
                "FROM vacinacao vn " +
                "JOIN animal a ON vn.animal_id = a.id " +
                "JOIN vacina v ON vn.vacina_id = v.id " +
                "ORDER BY vn.id";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToVacinacao(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vacinações", e);
        }
        return list;
    }

    @Override
    public List<Vacinacao> findByAnimalId(Long animalId) {
        List<Vacinacao> list = new ArrayList<>();
        String sql = "SELECT vn.id, vn.data_vacinacao, vn.responsavel, " +
                "a.id AS animal_id, a.nome AS animal_nome, a.raca AS animal_raca, a.dono AS animal_dono, " +
                "v.id AS vacina_id, v.nome AS vacina_nome, v.fabricante AS vacina_fabricante " +
                "FROM vacinacao vn " +
                "JOIN animal a ON vn.animal_id = a.id " +
                "JOIN vacina v ON vn.vacina_id = v.id " +
                "WHERE vn.animal_id = ? " +
                "ORDER BY vn.data_vacinacao DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, animalId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToVacinacao(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vacinações do animal", e);
        }
        return list;
    }

    @Override
    public void update(Vacinacao vacinacao) {
        String sql = "UPDATE vacinacao SET animal_id = ?, vacina_id = ?, data_vacinacao = ?, responsavel = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, vacinacao.getAnimal().getId());
            stmt.setLong(2, vacinacao.getVacina().getId());
            stmt.setDate(3, Date.valueOf(vacinacao.getData()));
            stmt.setString(4, vacinacao.getResponsavel());
            stmt.setLong(5, vacinacao.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar vacinação", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM vacinacao WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar vacinação", e);
        }
    }
}
