package com.hab.blog.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("mysql")
@Testcontainers
public class MySQLIntegrationTest {

    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("");

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // Use @DynamicPropertySource to replace Spring DataSource properties with Testcontainer properties
    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
    }

    @Test
    void testSimpleSelect() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS numbers (id INT, number VARCHAR(50));");
        jdbcTemplate.execute("INSERT INTO numbers (id, number) VALUES (1, 'one');");
        String number = jdbcTemplate.queryForObject("SELECT number FROM numbers WHERE id = 1", String.class);
        assertThat(number).isEqualTo("one");
    }


}
