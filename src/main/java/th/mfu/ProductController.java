package th.mfu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    public static Map<Integer,Product> products = new HashMap<Integer,Product>();
    private int nextId = 1;

    @GetMapping("/products")
    public ResponseEntity<Collection> getAllProducts(){
        Collection<Product> res = products.values();
        return new ResponseEntity<Collection>(res,HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        product.setId(nextId);
        products.put(nextId, product);
        nextId++;
        return new ResponseEntity<String>("Product Created!",HttpStatus.CREATED);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id){
        if (products.containsKey(id)) {
            Product product = products.get(id);
            return new ResponseEntity<Product>(product,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delProduct(@PathVariable Integer id){
        products.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
