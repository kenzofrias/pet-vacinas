package app.database;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    public static final String URL;
    public static final String USER;
    public static final String PASSWORD;

    static {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Não foi possível encontrar o arquivo config.properties");
            }
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar as configurações do banco de dados", e);
        }
    }
}
