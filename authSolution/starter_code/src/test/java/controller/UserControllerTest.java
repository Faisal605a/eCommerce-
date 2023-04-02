package controller;

import com.example.demo.UtilsTest;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void SetUp(){
        userController = new UserController();
        UtilsTest.injectObject(userController, "userRepository", userRepository);
        UtilsTest.injectObject(userController, "cartRepository", cartRepository);
        UtilsTest.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);

    }

    @Test
    public void createUser1(){
         when(bCryptPasswordEncoder.encode("123")).thenReturn("thisIsHashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Faisal");
        createUserRequest.setPassword("123");
        createUserRequest.setConfirmPassword("11");

       final ResponseEntity responseEntity =  userController.createUser(createUserRequest);

       assertNotNull(responseEntity);
       assertEquals(200, responseEntity.getStatusCodeValue());

       User user = (User) responseEntity.getBody();
       assertNotNull(user);
       assertEquals("Faisal", user.getUsername());
       assertEquals("123", user.getPassword());
       assertEquals(0L, user.getId());

    }

}
