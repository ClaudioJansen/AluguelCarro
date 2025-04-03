package carlocate.application;

import carlocate.dto.UserRequest;
import carlocate.model.User;
import carlocate.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserRequest user) throws IllegalArgumentException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists!");
        }
        var newUser = buildNewUser(user);
        userRepository.save(newUser);
    }

    private static User buildNewUser(UserRequest user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .cpf(user.getCpf())
                .rg(user.getRg())
                .address(user.getAddress())
                .profession(user.getProfession())
                .build();
    }
}
