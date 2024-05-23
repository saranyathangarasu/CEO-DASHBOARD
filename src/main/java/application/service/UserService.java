package application.service;

import java.util.List;

import application.Dto.UserDto;
import application.model.User;

public interface UserService {

	User findByUsername(String username);
	User save(UserDto userDto);
    int getTotalStudents();
    int getTotalWorkersByDepartment(String department);
    User findById(Long id);
    void updateUser(User user);
    User getUpdatedUser();
    User getUserById(Long id);
    void updateUserPassword(User updatedUser);

    
}