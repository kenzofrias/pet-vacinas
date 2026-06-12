package app.persistence;

import app.database.ConnectionFactory;
import app.model.Vacina;
import app.repository.VacinaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VacinaRepositoryJdbc implements VacinaRepository {

    @Override
    public Vacina save(Vacina vacina) {
        String sql = "INSERT INTO vacina (nome, fabricante) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vacina.getNome());
            stmt.setString(2, vacina.getFabricante());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vacina.setId(generatedKeys.getLong(1));
                }
            }
            return vacina;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar vacina", e);
        }
    }

    @Override
    public Optional<Vacina> findById(Long id) {
        String sql = "SELECT id, nome, fabricante FROM vacina WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vacina vacina = new Vacina(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("fabricante")
                    );
                    return Optional.of(vacina);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vacina por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Vacina> findAll() {
        List<Vacina> list = new ArrayList<>();
        String sql = "SELECT id, nome, fabricante FROM vacina ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Vacina(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("fabricante")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vacinas", e);
        }
        return list;
    }

    @Override
    public void update(Vacina vacina) {
        String sql = "UPDATE vacina SET nome = ?, fabricante = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vacina.getNome());
            stmt.setString(2, vacina.getFabricante());
            stmt.setLong(3, vacina.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar vacina", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM vacina WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar vacina", e);
        }
    }
}
