package dev.jorm.cli.config;

import dev.jorm.cli.output.Printer;
import dev.jorm.core.model.SchemaModel;
import dev.jorm.db.ConnectionManager;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConfigResolver {

    public static ConnectionManager resolve(SchemaModel model) {
        String url = null;
        String username = null;
        String password = null;

        // 1. Environment Variable
        String envUrl = System.getenv("DATABASE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            Printer.info("Using database configuration from Environment Variable (DATABASE_URL)");
            return createConnectionManager(envUrl, null, null);
        }

        // 2. .env file
        File envFile = new File(".env");
        if (envFile.exists()) {
            try {
                List<String> lines = Files.readAllLines(envFile.toPath());
                for (String line : lines) {
                    if (line.trim().startsWith("DATABASE_URL=")) {
                        String val = line.substring(line.indexOf('=') + 1).trim();
                        if (val.startsWith("\"") && val.endsWith("\"")) {
                            val = val.substring(1, val.length() - 1);
                        }
                        Printer.info("Using database configuration from .env file");
                        return createConnectionManager(val, null, null);
                    }
                }
            } catch (Exception e) {
                Printer.warn("Failed to read .env file: " + e.getMessage());
            }
        }

        // 3. application.properties
        File appProps = new File("src/main/resources/application.properties");
        if (appProps.exists()) {
            try (InputStream input = new FileInputStream(appProps)) {
                Properties prop = new Properties();
                prop.load(input);
                url = prop.getProperty("spring.datasource.url");
                username = prop.getProperty("spring.datasource.username");
                password = prop.getProperty("spring.datasource.password");

                if (url != null && !url.isEmpty()) {
                    Printer.info("Using database configuration from application.properties");
                    return createConnectionManager(url, username, password);
                }
            } catch (Exception e) {
                Printer.warn("Failed to read application.properties: " + e.getMessage());
            }
        }

        // 3.5 application.yml
        File appYml = new File("src/main/resources/application.yml");
        if (!appYml.exists()) {
            appYml = new File("src/main/resources/application.yaml");
        }
        if (appYml.exists()) {
            try (InputStream input = new FileInputStream(appYml)) {
                Yaml yaml = new Yaml();
                Map<String, Object> data = yaml.load(input);
                if (data != null && data.containsKey("spring")) {
                    Map<String, Object> spring = (Map<String, Object>) data.get("spring");
                    if (spring != null && spring.containsKey("datasource")) {
                        Map<String, Object> datasource = (Map<String, Object>) spring.get("datasource");
                        if (datasource != null) {
                            url = (String) datasource.get("url");
                            username = (String) datasource.get("username");
                            password = (String) datasource.get("password");

                            if (url != null && !url.isEmpty()) {
                                Printer.info("Using database configuration from application.yml");
                                return createConnectionManager(url, username, password);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Printer.warn("Failed to read application.yml: " + e.getMessage());
            }
        }

        // 4. docker-compose.yml
        File dockerCompose = new File("docker-compose.yml");
        if (!dockerCompose.exists()) {
            dockerCompose = new File("docker-compose.yaml");
        }
        if (dockerCompose.exists()) {
            try (InputStream input = new FileInputStream(dockerCompose)) {
                Yaml yaml = new Yaml();
                Map<String, Object> data = yaml.load(input);
                if (data != null && data.containsKey("services")) {
                    Map<String, Object> services = (Map<String, Object>) data.get("services");
                    for (Map.Entry<String, Object> entry : services.entrySet()) {
                        Map<String, Object> service = (Map<String, Object>) entry.getValue();
                        String image = (String) service.get("image");
                        if (image != null && (image.startsWith("postgres") || image.startsWith("mysql"))) {
                            String port = "5432";
                            if (image.startsWith("mysql")) port = "3306";

                            if (service.containsKey("ports")) {
                                List<String> ports = (List<String>) service.get("ports");
                                if (ports != null && !ports.isEmpty()) {
                                    String p = ports.get(0);
                                    if (p.contains(":")) {
                                        port = p.split(":")[0];
                                    }
                                }
                            }

                            String dbName = "postgres";
                            if (image.startsWith("mysql")) dbName = "mysql";
                            String user = "postgres";
                            if (image.startsWith("mysql")) user = "root";
                            String pass = "";

                            if (service.containsKey("environment")) {
                                Object envObj = service.get("environment");
                                if (envObj instanceof Map) {
                                    Map<String, String> env = (Map<String, String>) envObj;
                                    if (env.containsKey("POSTGRES_DB")) dbName = env.get("POSTGRES_DB");
                                    if (env.containsKey("POSTGRES_USER")) user = env.get("POSTGRES_USER");
                                    if (env.containsKey("POSTGRES_PASSWORD")) pass = env.get("POSTGRES_PASSWORD");

                                    if (env.containsKey("MYSQL_DATABASE")) dbName = env.get("MYSQL_DATABASE");
                                    if (env.containsKey("MYSQL_USER")) user = env.get("MYSQL_USER");
                                    if (env.containsKey("MYSQL_ROOT_PASSWORD")) pass = env.get("MYSQL_ROOT_PASSWORD");
                                    if (env.containsKey("MYSQL_PASSWORD")) pass = env.get("MYSQL_PASSWORD");
                                } else if (envObj instanceof List) {
                                    List<String> envList = (List<String>) envObj;
                                    for (String envStr : envList) {
                                        if (envStr.startsWith("POSTGRES_DB=")) dbName = envStr.substring("POSTGRES_DB=".length());
                                        if (envStr.startsWith("POSTGRES_USER=")) user = envStr.substring("POSTGRES_USER=".length());
                                        if (envStr.startsWith("POSTGRES_PASSWORD=")) pass = envStr.substring("POSTGRES_PASSWORD=".length());

                                        if (envStr.startsWith("MYSQL_DATABASE=")) dbName = envStr.substring("MYSQL_DATABASE=".length());
                                        if (envStr.startsWith("MYSQL_USER=")) user = envStr.substring("MYSQL_USER=".length());
                                        if (envStr.startsWith("MYSQL_ROOT_PASSWORD=")) pass = envStr.substring("MYSQL_ROOT_PASSWORD=".length());
                                        if (envStr.startsWith("MYSQL_PASSWORD=")) pass = envStr.substring("MYSQL_PASSWORD=".length());
                                    }
                                }
                            }

                            String dialect = image.startsWith("postgres") ? "postgresql" : "mysql";
                            url = "jdbc:" + dialect + "://localhost:" + port + "/" + dbName;
                            Printer.info("Using database configuration from docker-compose.yml");
                            return createConnectionManager(url, user, pass);
                        }
                    }
                }
            } catch (Exception e) {
                Printer.warn("Failed to read docker-compose.yml: " + e.getMessage());
            }
        }

        // 5. schema.jorm
        if (model != null && model.config() != null) {
            for (SchemaModel.ConfigEntry entry : model.config()) {
                if ("database.url".equals(entry.key())) {
                    url = entry.value();
                } else if ("database.username".equals(entry.key())) {
                    username = entry.value();
                } else if ("database.password".equals(entry.key())) {
                    password = entry.value();
                }
            }
            if (url != null && !url.isEmpty()) {
                Printer.info("Using database configuration from schema.jorm");
                return createConnectionManager(url, username, password);
            }
        }

        throw new RuntimeException("Could not find database configuration. Please define DATABASE_URL, or use .env, application.properties, docker-compose.yml or schema.jorm");
    }

    private static ConnectionManager createConnectionManager(String url, String username, String password) {
        if (url.startsWith("postgresql://") || url.startsWith("mysql://")) {
            url = "jdbc:" + url;
        }
        if (username != null && password != null) {
            return new ConnectionManager(url, username, password);
        }
        return new ConnectionManager(url);
    }
}
