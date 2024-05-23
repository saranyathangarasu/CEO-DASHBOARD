package application.service;

import application.Dto.UserDto;
import application.model.User;
import application.repo.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setUsername1(userDto.getUsername1());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setDob(userDto.getDob());
        user.setDegree(userDto.getDegree());
        user.setAddress(userDto.getAddress());
        user.setMaritalStatus(userDto.getMaritalStatus());
        user.setDepartment(userDto.getDepartment());
        Set<String> roles = new HashSet<>();
        roles.add(userDto.getUsername());
        user.setRoles(roles);
        return userRepository.save(user);
    }
    
    @Override
    public int getTotalStudents() {
        return userRepository.countAllStudents();
    }

    @Override
    public int getTotalWorkersByDepartment(String department) {
        switch (department) {
            case "Finance":
                return userRepository.countFinance();
            case "Sales & Marketing":
                return userRepository.countSalesAndMarketing();
            case "Operational":
                return userRepository.countOperational();
            default:
                return 0;
        }
    }
 
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser) {
        // Validate input
        if (updatedUser == null || updatedUser.getId() == null) {
            throw new IllegalArgumentException("Invalid user or user ID.");
        }

        // Check if the user exists in the database
        Optional<User> existingUserOpt = userRepository.findById(updatedUser.getId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Update user fields as needed
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setUsername1(updatedUser.getUsername1());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setDegree(updatedUser.getDegree());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setMaritalStatus(updatedUser.getMaritalStatus());
            existingUser.setDepartment(updatedUser.getDepartment());

            // Save the updated user
            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with ID " + updatedUser.getId() + " not found.");
        }
    }

    @Override
    public User getUpdatedUser() {
        Long userId = 1L; 
        return userRepository.findById(userId).orElse(null);
    }
    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null); 
    }
    @Override
    public void updateUserPassword(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setPassword(updatedUser.getPassword()); 
            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with ID " + updatedUser.getId() + " not found.");
        }
    }

}