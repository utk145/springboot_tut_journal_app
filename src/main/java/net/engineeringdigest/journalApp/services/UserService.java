package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.types.UserRoles;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntity user) {
        userRepository.save(user);
    }

    public void saveNewUser(UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList(UserRoles.USER));
        userRepository.save(user);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void grantAdminRole(UserEntity user) {
        if (!user.getRoles().contains(UserRoles.ADMIN)) {
            List<UserRoles> temp1 = user.getRoles();
            temp1.add(UserRoles.ADMIN);
            user.setRoles(temp1);
        }
        userRepository.save(user); // Save the user with the updated roles
    }


}
