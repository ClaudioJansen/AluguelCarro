package carlocate.application;

import carlocate.model.User;
import carlocate.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidLogin(String email, String password){
        var user = userRepository.findByEmail(email);

        return user.filter(value -> isSamePassword(value, password)).isPresent();
    }

    private boolean isSamePassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    public String findUserType(String email) {
        return email.contains("@admin") ? "admin" : "client";
    }
}
