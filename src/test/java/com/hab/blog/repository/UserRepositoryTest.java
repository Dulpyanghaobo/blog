package com.hab.blog.repository;

//@DataJpaTest
//public class UserRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
////    @TestModel
////    public void findByEmailShouldReturnUser() {
////        // Arrange - given
////        User user = new User();
////        user.setDisplayName("TestModel User");
////        user.setAvatar("TestModel Avatar");
////        user.setEmail("test@example.com");
////        user.setPassword("password");
////        entityManager.persist(user);
////        entityManager.flush();
////
////        // Act - when
////        Optional<User> found = userRepository.findByEmail(user.getEmail());
////
////        // Assert - then
////        assertThat(found).isPresent();
////        assertThat(found.orElseThrow().getEmail()).isEqualTo(user.getEmail());
////    }
//
//    @Test
//    public void findByEmailShouldReturnEmptyIfUserNotFound() {
//        // Arrange - given
//        String email = "nonexisting@example.com";
//
//        // Act - when
//        Optional<User> found = userRepository.findByEmail(email);
//
//        // Assert - then
//        assertThat(found).isNotPresent();
//    }
//}
