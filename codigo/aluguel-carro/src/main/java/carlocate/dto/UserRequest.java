package carlocate.dto;

import lombok.Getter;

@Getter
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String rg;
    private String address;
    private String profession;
}
