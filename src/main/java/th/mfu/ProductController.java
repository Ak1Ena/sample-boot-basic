package th.mfu;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> res = productRepository.findAll();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        productRepository.save(product);
        return new ResponseEntity<String>("Product Created!",HttpStatus.CREATED);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<Product>(product.get(),HttpStatus.OK);
        }
        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delProduct(@PathVariable Integer id){
        productRepository.deleteById(id);
        return new ResponseEntity<String>("Product Deleted!",HttpStatus.NO_CONTENT);
    }
    @GetMapping("/products/description/{description}")
    public ResponseEntity<List<Product>> getProductsByDescription(@PathVariable String description) {
        List<Product> products = productRepository.findByDescriptionContaining(description);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/sorted")
    public ResponseEntity<List<Product>> getProductsSortedByPrice() {
        List<Product> products = productRepository.findByOrderByPrice();
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
