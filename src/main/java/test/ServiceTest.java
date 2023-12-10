package test;

import dto.UserDTOInput;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import service.UserService;
import spark.Spark;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {
    ModelMapper modelMapper = new ModelMapper();
    @Test
    public void userInsertionTesting(){
        UserService userService = new UserService();
        UserDTOInput userDTOInput = new UserDTOInput(1, "Uendel", "123456");

        userService.insertUser(userDTOInput);
        assertEquals(1, userService.listUsers().size());
    }
}
