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
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
            String envUser = System.getenv("DATABASE_USERNAME");
            String envPass = System.getenv("DATABASE_PASSWORD");
            ParsedDbUrl parsed = parseDbUrl(envUrl, envUser, envPass);
            return createConnectionManager(parsed.jdbcUrl(), parsed.username(), parsed.password());
        }

        // 2. .env file
        File envFile = new File(".env");
        if (envFile.exists()) {
            try {
                Map<String, String> env = readDotEnv(envFile);
                String dotenvUrl = env.get("DATABASE_URL");
                if (dotenvUrl != null && !dotenvUrl.isBlank()) {
                    String dotenvUser = env.get("DATABASE_USERNAME");
                    String dotenvPass = env.get("DATABASE_PASSWORD");
                    Printer.info("Using database configuration from .env file");
                    ParsedDbUrl parsed = parseDbUrl(dotenvUrl, dotenvUser, dotenvPass);
                    return createConnectionManager(parsed.jdbcUrl(), parsed.username(), parsed.password());
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

    private record ParsedDbUrl(String jdbcUrl, String username, String password) {}

    private static ParsedDbUrl parseDbUrl(String url, String username, String password) {
        String jdbcUrl = url;
        String user = username;
        String pass = password;

        if (jdbcUrl.startsWith("postgresql://") || jdbcUrl.startsWith("mysql://")) {
            URI uri = URI.create(jdbcUrl);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            if (host == null || path == null || path.isBlank()) {
                return new ParsedDbUrl("jdbc:" + jdbcUrl, user, pass);
            }

            String userInfo = uri.getUserInfo();
            if ((user == null || user.isBlank()) && userInfo != null && !userInfo.isBlank()) {
                int idx = userInfo.indexOf(':');
                if (idx >= 0) {
                    user = userInfo.substring(0, idx);
                    pass = idx < userInfo.length() - 1 ? userInfo.substring(idx + 1) : "";
                } else {
                    user = userInfo;
                }
            }

            if (port == -1) {
                port = "mysql".equalsIgnoreCase(scheme) ? 3306 : 5432;
            }

            jdbcUrl = "jdbc:" + scheme + "://" + host + ":" + port + path;
            return new ParsedDbUrl(jdbcUrl, blankToNull(user), blankToNull(pass));
        }

        if (jdbcUrl.startsWith("jdbc:")) {
            ParsedDbUrl fromQuery = parseQueryCredentials(jdbcUrl);
            if (isBlank(user) && !isBlank(fromQuery.username())) {
                user = fromQuery.username();
            }
            if (isBlank(pass) && !isBlank(fromQuery.password())) {
                pass = fromQuery.password();
            }
            return new ParsedDbUrl(jdbcUrl, blankToNull(user), blankToNull(pass));
        }

        return new ParsedDbUrl(jdbcUrl, blankToNull(user), blankToNull(pass));
    }

    private static ParsedDbUrl parseQueryCredentials(String jdbcUrl) {
        int q = jdbcUrl.indexOf('?');
        if (q < 0 || q >= jdbcUrl.length() - 1) {
            return new ParsedDbUrl(jdbcUrl, null, null);
        }
        String query = jdbcUrl.substring(q + 1);
        String user = null;
        String pass = null;
        for (String pair : query.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) {
                continue;
            }
            String key = URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8);
            String val = URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8);
            if ("user".equalsIgnoreCase(key) || "username".equalsIgnoreCase(key)) {
                user = val;
            } else if ("password".equalsIgnoreCase(key)) {
                pass = val;
            }
        }
        return new ParsedDbUrl(jdbcUrl, blankToNull(user), blankToNull(pass));
    }

    private static Map<String, String> readDotEnv(File envFile) throws Exception {
        Map<String, String> map = new HashMap<>();
        List<String> lines = Files.readAllLines(envFile.toPath());
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            if (trimmed.startsWith("export ")) {
                trimmed = trimmed.substring("export ".length()).trim();
            }
            int idx = trimmed.indexOf('=');
            if (idx <= 0) {
                continue;
            }
            String key = trimmed.substring(0, idx).trim();
            String val = trimmed.substring(idx + 1).trim();
            val = stripQuotes(val);
            map.put(key, val);
        }
        return map;
    }

    private static String stripQuotes(String val) {
        if (val == null) {
            return null;
        }
        if ((val.startsWith("\"") && val.endsWith("\"")) || (val.startsWith("'") && val.endsWith("'"))) {
            return val.substring(1, val.length() - 1);
        }
        return val;
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private static String blankToNull(String s) {
        return isBlank(s) ? null : s;
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
