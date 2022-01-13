package dev.oxeg.gameservice;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class DatabaseResource implements QuarkusTestResourceLifecycleManager {
    static PostgreSQLContainer<?> db =
            new PostgreSQLContainer<>("postgres:14")
                    .withDatabaseName("test_db")
                    .withUsername("test_user")
                    .withPassword("test_pwd");

    @Override
    public Map<String, String> start() {
        db.start();
        return Map.ofEntries(
                Map.entry("quarkus.flyway.migrate-at-start", "true"),
                Map.entry("quarkus.datasource.jdbc.url", db.getJdbcUrl()),
                Map.entry("quarkus.datasource.username", db.getUsername()),
                Map.entry("quarkus.datasource.password", db.getPassword())
        );
    }

    @Override
    public void stop() {
        db.stop();
    }
}
