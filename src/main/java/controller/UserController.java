package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDTOInput;
import dto.UserDTOOutput;
import service.UserService;

import static spark.Spark.*;

public class UserController {
    private UserService userService;
    private ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper){
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    public void responseRequests(){
        get("users", (req, res) -> {
            res.type("application/json");
            res.status(200);
            return objectMapper.writeValueAsString(userService.listUsers());
        });

        get("users/:id", (req, res) -> {
           int id = Integer.parseInt(req.params(":id"));
            UserDTOOutput userDTOOutput = userService.searchUserForId(id);
            if (userDTOOutput != null){
                res.type("application/json");
                res.status(200);
                return objectMapper.writeValueAsString(userDTOOutput);
            } else {
                res.status(404);
                return "User not FOUND!";
            }
        });

        delete("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            userService.deleteUser(id);
            res.status(204);
            return "";
        });

        post("/users", (req, res) ->{
            UserDTOInput userDTOInput = objectMapper.readValue(req.body(), UserDTOInput.class);
            userService.insertUser(userDTOInput);
            res.status(201);
            return "User entered successfully!";
        });

        put("/users/:id", (req, res) -> {
           int id = Integer.parseInt(req.params(":id"));
           UserDTOInput userDTOInput = objectMapper.readValue(req.body(), UserDTOInput.class);
           userDTOInput.setId(id);
           userService.changeUser(userDTOInput);
           res.status(200);
           return "User changed successfully!";
        });
    }
}
