package th.mfu;

import java.util.Collection;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductReviewController {
    @Autowired
    private ProductReviewRepository productReviewRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("/reviews")
    public Collection<ProductReview> getAllReviews(){
        return productReviewRepo.findAll();
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@RequestBody ProductReview review){
        if(review.getProduct()==null || review.getProduct().getId()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> co = customerRepo.findById(review.getCustomer().getId());
        if (!co.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } 
        Optional<Product> pr = productRepo.findById(review.getProduct().getId());
        if (!pr.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        review.setCustomer(co.get());
        review.setProduct(pr.get());
        productReviewRepo.save(review);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
