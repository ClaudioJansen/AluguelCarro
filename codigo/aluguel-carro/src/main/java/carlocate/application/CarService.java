package carlocate.application;

import carlocate.dto.CarRequest;
import carlocate.enums.CarStatus;
import carlocate.model.Car;
import carlocate.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car save(CarRequest car) {
        var newCar = buildNewCar(car);
        return carRepository.save(newCar);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    private Car buildNewCar(CarRequest car) {
        return Car.builder()
                .registration(car.getRegistration())
                .year(car.getYear())
                .mark(car.getMark())
                .model(car.getModel())
                .plate(car.getPlate())
                .status(CarStatus.AVAILABLE)
                .build();
    }
}
