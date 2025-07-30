package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("clientes")
    public List<Customer> getCustomers(){
        return customers;
    }
}
