package com.hab.blog.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    private User validUser;
//    private UserDto userDto;
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    void setUp() {
//        validUser = new User();
//        validUser.setDisplayName("TestUser");
//        validUser.setAvatar("TestAvatar");
//        validUser.setEmail("test@example.com");
//        validUser.setPassword("password");
//
//        userDto = new UserDto();
//        userDto.setDisplayName("TestUser");
//        userDto.setAvatar("TestAvatar");
//        userDto.setEmail("test@example.com");
//        userDto.setPassword("password");
//    }

//    @TestModel
//    public void createUser_ShouldReturnUser_WhenRequestIsValid() throws Exception {
//        given(userService.createUser(anyString(), anyString(), anyString(), anyString())).willReturn(validUser);
//
//        mockMvc.perform(post("/api/v1/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.displayName").value(validUser.getDisplayName()))
//                .andExpect(jsonPath("$.avatar").value(validUser.getAvatar()))
//                .andExpect(jsonPath("$.email").value(validUser.getEmail()));
//
//        verify(userService, times(1)).createUser(anyString(), anyString(), anyString(), anyString());
//    }
}
