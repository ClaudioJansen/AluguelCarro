package carlocate.model;

import carlocate.enums.CarStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registration;

    @Column(name = "`year`")
    private String year;

    private String mark;
    private String model;
    private String plate;

    @Enumerated(EnumType.STRING)
    private CarStatus status;
}

