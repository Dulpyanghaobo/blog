package com.hab.blog.integration;

//@SpringBootTest
//@ActiveProfiles("mysql")
//@Testcontainers
//public class MySQLIntegrationTest {
//
//    @Container
//    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
//            .withDatabaseName("testdb")
//            .withUsername("root")
//            .withPassword("");
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    // Use @DynamicPropertySource to replace Spring DataSource properties with Testcontainer properties
//    @DynamicPropertySource
//    static void databaseProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
//        registry.add("spring.datasource.password", mysqlContainer::getPassword);
//        registry.add("spring.datasource.username", mysqlContainer::getUsername);
//    }
//
//    @Test
//    void testSimpleSelect() {
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS numbers (id INT, number VARCHAR(50));");
//        jdbcTemplate.execute("INSERT INTO numbers (id, number) VALUES (1, 'one');");
//        String number = jdbcTemplate.queryForObject("SELECT number FROM numbers WHERE id = 1", String.class);
//        assertThat(number).isEqualTo("one");
//    }
//
//
//}
