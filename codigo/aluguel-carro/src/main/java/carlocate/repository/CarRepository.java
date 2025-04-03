package carlocate.repository;

import carlocate.enums.CarStatus;
import carlocate.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Modifying
    @Query("UPDATE Car c SET c.status = :status WHERE c.id = :id")
    void updateCarStatus(@Param("id") Long id, @Param("status") CarStatus status);

}
