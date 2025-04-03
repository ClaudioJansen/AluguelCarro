package carlocate.dto;

import lombok.Getter;

@Getter
public class CarRequest {
    private String registration;
    private String year;
    private String mark;
    private String model;
    private String plate;
}
