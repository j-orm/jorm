package dev.jorm.cli.util;

import java.sql.SQLException;

public final class ExceptionUtils {

    private ExceptionUtils() {}

    public static boolean isDebugEnabled() {
        String env = System.getenv("JORM_DEBUG");
        if (env == null) {
            return false;
        }
        return "1".equals(env) || "true".equalsIgnoreCase(env) || "yes".equalsIgnoreCase(env);
    }

    public static void maybePrintDebug(Throwable t) {
        if (isDebugEnabled()) {
            t.printStackTrace(System.err);
        }
    }

    public static String userMessage(Throwable t) {
        Throwable root = rootCause(t);
        String rootMessage = root.getMessage();
        if (rootMessage == null || rootMessage.isBlank()) {
            rootMessage = root.getClass().getName();
        }

        String friendly = friendlyMessage(root);
        if (!isDebugEnabled()) {
            friendly = friendly + " (Define JORM_DEBUG=true para ver detalhes.)";
        }
        return friendly + " Causa: " + root.getClass().getSimpleName() + ": " + rootMessage;
    }

    private static String friendlyMessage(Throwable root) {
        String genericMsg = root.getMessage() == null ? "" : root.getMessage();
        if (genericMsg.contains("No suitable driver found")) {
            return "Driver JDBC não encontrado para o DATABASE_URL. Confirma o esquema (postgresql/mysql) e actualiza o CLI para uma versão com drivers incluídos.";
        }
        if (root instanceof SQLException sql) {
            String state = sql.getSQLState();
            String msg = sql.getMessage() == null ? "" : sql.getMessage();

            if ("28P01".equals(state) || msg.contains("password authentication failed")) {
                return "Falha de autenticação ao ligar à base de dados. Confirma user/password (DATABASE_USERNAME/DATABASE_PASSWORD) ou inclui credenciais no DATABASE_URL.";
            }

            if ((state != null && state.startsWith("08")) || msg.contains("Connection refused") || msg.contains("timeout")) {
                return "Não foi possível ligar à base de dados (ligação recusada/timeout). Confirma host/porta e se a base de dados está a correr.";
            }

            if (msg.contains("does not exist") || msg.contains("relation") && msg.contains("does not exist")) {
                return "A migração falhou por causa de um objecto em falta na base de dados (tabela/coluna). Confirma o estado das migrações e o schema.";
            }
        }

        return "Ocorreu um erro ao executar a migração.";
    }

    private static Throwable rootCause(Throwable t) {
        Throwable cur = t;
        while (cur.getCause() != null && cur.getCause() != cur) {
            cur = cur.getCause();
        }
        return cur;
    }
}
