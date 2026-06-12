package app.persistence;

import app.database.ConnectionFactory;
import app.model.Animal;
import app.repository.AnimalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalRepositoryJdbc implements AnimalRepository {

    @Override
    public Animal save(Animal animal) {
        String sql = "INSERT INTO animal (nome, raca, dono) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getRaca());
            stmt.setString(3, animal.getDono());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    animal.setId(generatedKeys.getLong(1));
                }
            }
            return animal;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar animal", e);
        }
    }

    @Override
    public Optional<Animal> findById(Long id) {
        String sql = "SELECT id, nome, raca, dono FROM animal WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Animal animal = new Animal(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("raca"),
                            rs.getString("dono")
                    );
                    return Optional.of(animal);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animal por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Animal> findAll() {
        List<Animal> list = new ArrayList<>();
        String sql = "SELECT id, nome, raca, dono FROM animal ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Animal(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("raca"),
                        rs.getString("dono")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais", e);
        }
        return list;
    }

    @Override
    public void update(Animal animal) {
        String sql = "UPDATE animal SET nome = ?, raca = ?, dono = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getRaca());
            stmt.setString(3, animal.getDono());
            stmt.setLong(4, animal.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar animal", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar animal", e);
        }
    }
}
