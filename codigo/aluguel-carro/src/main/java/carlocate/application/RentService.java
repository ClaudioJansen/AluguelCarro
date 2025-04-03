package carlocate.application;

import carlocate.dto.RentRequest;
import carlocate.enums.CarStatus;
import carlocate.model.Rent;
import carlocate.repository.CarRepository;
import carlocate.repository.RentRepository;
import carlocate.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class RentService {

    private final CarRepository carRepository;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;

    public RentService(CarRepository carRepository, RentRepository rentRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createRent(RentRequest rent) {
        var car = carRepository.findById(rent.getCarId());

        if(car.isPresent() && car.get().getStatus().equals(CarStatus.AVAILABLE)){
            carRepository.updateCarStatus(rent.getCarId(), CarStatus.RENTED);

            var newRent = buildNewRent(rent);

            rentRepository.save(Objects.requireNonNull(newRent));
        }
    }

    public List<Rent> getAllRent() {
        return rentRepository.findAll();
    }

    public void deleteRent(Long id) {
        carRepository.updateCarStatus(id, CarStatus.AVAILABLE);

        rentRepository.deleteById(id);
    }

    private Rent buildNewRent(RentRequest rent) {
        var user = userRepository.findByEmail(rent.getUserEmail());
        var car = carRepository.findById(rent.getCarId());

        if(user.isPresent() && car.isPresent()){
            return Rent.builder()
                    .user(user.get())
                    .car(car.get())
                    .rentDate(LocalDate.now())
                    .returnDate(rent.getReturnDate())
                    .build();
        }
        return null;
    }

}
