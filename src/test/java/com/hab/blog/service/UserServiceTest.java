package com.hab.blog.service;

import com.hab.blog.api.v1.auth.UserRepository;
import com.hab.blog.api.v1.auth.Service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
@SpringBootTest
public class UserServiceTest {

    private UserService userService;

    @MockBean
    private UserRepository userRepository;
//
//    @BeforeEach
//    public void setUp() {
//        userService = new UserService(emailService, userRepository, verificationTokenRepository);
//    }
//
//    @TestModel
//    public void userRepositoryIsMocked() {
//        assertNotNull(userRepository, "UserRepository should be mocked!");
//    }

//    @TestModel
//    public void testCreateUser() {
//        // Arrange
//        String displayName = "TestName";
//        String avatar = "TestAvatar";
//        String email = "test@example.com";
//        String password = "password";
//        User user = new User();
//        user.setDisplayName(displayName);
//        user.setAvatar(avatar);
//        user.setEmail(email);
//        user.setPassword(password);
//
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        // Act
//        User createdUser = userService.createUser(displayName, avatar, email, password);
//
//        // Assert
//        assertNotNull(createdUser, "The created User should not be null");
//        assertEquals(displayName, createdUser.getDisplayName());
//        assertEquals(avatar, createdUser.getAvatar());
//        assertEquals(email, createdUser.getEmail());
//        assertEquals(password, createdUser.getPassword());
//        verify(userRepository).save(any(User.class));
//    }
}
