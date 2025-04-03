package carlocate.controller;

import carlocate.application.RentService;
import carlocate.dto.RentRequest;
import carlocate.model.Rent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent")
@CrossOrigin(origins = "http://localhost:3000")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping
    public ResponseEntity<Void> createRent(@RequestBody RentRequest rent) {
        rentService.createRent(rent);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Rent>> getAllRent(){
        return ResponseEntity.ok(rentService.getAllRent());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long id){
        rentService.deleteRent(id);
        return ResponseEntity.noContent().build();
    }

}
