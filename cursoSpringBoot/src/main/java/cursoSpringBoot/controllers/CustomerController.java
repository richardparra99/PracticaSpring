package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class CustomerController {
    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(1, "Angelo R.", "angelor", "123"),
            new Customer(2, "erick C.", "cuellarE", "erick2025"),
            new Customer(3, "leornado S.", "SolizL", "SL2026"),
            new Customer(4, "Edgar R.", "RojasE", "RE2015")
    ));

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return ResponseEntity.ok(customers);
        //return customers;
    }

    //@RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @GetMapping("/{username}")
    public ResponseEntity<?>  getCliente(@PathVariable String username){
        for (Customer c: customers){
            if (c.getUsername().equalsIgnoreCase(username)){
                return ResponseEntity.ok(c);
                //return c;
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con el username: " + username);
        //return null;
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody Customer customer){
        customers.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente Resgitrado exitosamente: " + customer.getName());
        //return customer;
    }

    //@RequestMapping(method = RequestMethod.PUT)
    @PutMapping
    public ResponseEntity<?> putCliente(@RequestBody Customer customer){
        for (Customer c : customers){
            if (c.getID() == customer.getID()){
                c.setName(customer.getName());
                c.setUsername(customer.getUsername());
                c.setPassword(customer.getPassword());
                //mensaje personalizado
                //return ResponseEntity ok("Cliente actualizado: " + customer.getName());
                return ResponseEntity.noContent().build(); //-- mensaje no personalizado
            }
        }
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + customer.getName());
        return ResponseEntity.notFound().build();
    }

    //@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable int id){
        for (Customer c : customers){
            if (c.getID() == id){
                customers.remove(c);
                //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente eliminado: " + id);
                return ResponseEntity.noContent().build();
            }
        }
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + id);
        return ResponseEntity.notFound().build();
    }

    //@RequestMapping(method = RequestMethod.PATCH)
    @PatchMapping
    public ResponseEntity<?> patchCliente(@RequestBody Customer customer){
        for (Customer c: customers){
            if (c.getID() == customer.getID()){
                if (customer.getName() != null){
                    c.setName(customer.getName());
                }
                if (customer.getUsername() != null){
                    c.setUsername(customer.getUsername());
                }
                if (customer.getPassword() != null){
                    c.setPassword(customer.getPassword());
                }
                return ResponseEntity.ok("Cliente modificado: " + customer.getID());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado" + customer.getID());
    }
}
