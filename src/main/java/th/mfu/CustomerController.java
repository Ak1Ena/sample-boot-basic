package th.mfu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    // public static Map<Integer, Customer> customers = new
    // HashMap<Integer,Customer>();
    // private int nextId = 1;
    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    private CustomerTierRepository customerTierRepo;


    @GetMapping("/customers")
    public ResponseEntity<Collection> getAllCustomers() {
        Collection result = customerRepo.findAll();
        return new ResponseEntity<Collection>(result, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        if (customer.getCustomerTier() != null && customer.getCustomerTier().getId() != null) {
            Optional<CustomerTier> tier = customerTierRepo.findById(customer.getCustomerTier().getId());
            if (tier.isPresent()) {
                customer.setCustomerTier(tier.get());
            }else{
                return new ResponseEntity<String>("Customer Tier not found", HttpStatus.BAD_REQUEST);
            }
        }
        customerRepo.save(customer);
        return new ResponseEntity<String>("Customer created", HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        if (customerRepo.existsById(id)) {
            Optional<Customer> customer = customerRepo.findById(id);

            return new ResponseEntity<Customer>(customer.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id) {
        customerRepo.deleteById(id);
        return new ResponseEntity<String>("Customer Deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/customers/name/{prefix}")
    public ResponseEntity<Collection> searchCustomerByName(@PathVariable String prefix) {
        List<Customer> customers = customerRepo.findByNameStartingWith(prefix);

        return new ResponseEntity<Collection>(customers, HttpStatus.OK);
    }
}
