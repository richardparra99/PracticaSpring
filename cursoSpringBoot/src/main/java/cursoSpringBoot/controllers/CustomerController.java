package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerController {
    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(1, "Angelo R.", "angelor", "123"),
            new Customer(2, "erick C.", "cuellarE", "erick2025"),
            new Customer(3, "leornado S.", "SolizL", "SL2026"),
            new Customer(4, "Edgar R.", "RojasE", "RE2015")
    ));

    @GetMapping("/clientes")
    public List<Customer> getCustomers(){
        return customers;
    }

    @GetMapping("/clientes/{username}")
    public Customer getCliente(@PathVariable String username){
        for (Customer c: customers){
            if (c.getUsername().equalsIgnoreCase(username)){
                return c;
            }
        }
        return null;
    }

    @PostMapping("/clientes")
    public Customer postCliente(@RequestBody Customer customer){
        customers.add(customer);
        return customer;
    }

    @PutMapping("/clientes")
    public Customer putCliente(@RequestBody Customer customer){
        for (Customer c : customers){
            if (c.getID() == customer.getID()){
                c.setName(customer.getName());
                c.setUsername(customer.getUsername());
                c.setPassword(customer.getPassword());
                return c;
            }
        }
        return null;
    }

    @DeleteMapping("/clientes/{id}")
    public Customer deleteCliente(@PathVariable int id){
        for (Customer c : customers){
            if (c.getID() == id){
                customers.remove(c);
                return c;
            }
        }
        return null;
    }
}
