package app.database;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    static {
        try {
            Class.forName("org.h2.Driver");
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar o driver do H2", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
    }

    private static void initializeDatabase() {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("sql/schema.sql")) {
            if (input == null) {
                throw new RuntimeException("Não foi possível encontrar o arquivo sql/schema.sql em resources.");
            }
            String schemaSql = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(schemaSql);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar o banco de dados a partir do schema.sql", e);
        }
    }
}
