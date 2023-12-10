package service;

import dto.UserDTOInput;
import dto.UserDTOOutput;
import model.User;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private List<User> usersList;
    private ModelMapper modelMapper;

    public UserService(List<User> usersList, ModelMapper modelMapper){
        this.usersList = usersList;
        this.modelMapper = modelMapper;
    }

    public List<UserDTOOutput> listUsers(){
        return usersList.stream()
                .map(user -> modelMapper.map(user, UserDTOOutput.class))
                .collect(Collectors.toList());
    }

    public void insertUser(UserDTOInput userDTOInput){
        User user = modelMapper.map(userDTOInput, User.class);
        usersList.add(user);
    }

    public void changeUser(UserDTOInput userDTOInput){
        int id = userDTOInput.getId();
        int index = findIndexForId(id);

        if (index != 1){
            User updatedUser = modelMapper.map(userDTOInput, User.class);
            usersList.set(index, updatedUser);
        }
    }

    public UserDTOOutput searchUserForId(int id){
        Optional<User> optionalUser = usersList.stream()
                                               .filter(user -> user.getId() == id)
                                               .findFirst();

        return optionalUser.map(user -> modelMapper.map(user, UserDTOOutput.class))
                           .orElse(null);
    }

    public void deleteUser(int id){
        usersList.removeIf(user -> user.getId() == id);
    }

    private int findIndexForId(int id){
        for (int i = 0; i < usersList.size(); i++){
            if (usersList.get(i).getId() == id){
                return i;
            }
        }

        return -1;
    }
}
