package carlocate.controller;


import carlocate.application.LoginService;
import carlocate.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        var validLogin = loginService.isValidLogin(loginRequest.getEmail(), loginRequest.getPassword());

        var userType = loginService.findUserType(loginRequest.getEmail());

        return validLogin ? ResponseEntity.ok(userType) : ResponseEntity.badRequest().build();
    }

}