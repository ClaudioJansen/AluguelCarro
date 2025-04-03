package carlocate.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RentRequest {

    private Long carId;
    private String userEmail;
    private LocalDate returnDate;

}
