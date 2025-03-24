package carlocate.controller;


import carlocate.application.ClienteService;
import carlocate.model.Cliente;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController extends HttpServlet {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("")
    public ResponseEntity<Cliente> postCliente(@RequestBody Cliente cliente) {
        Cliente created = clienteService.createCliente(cliente);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.getCliente(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> patchCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Cliente updated = clienteService.updateCliente(id, clienteDetails);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

}