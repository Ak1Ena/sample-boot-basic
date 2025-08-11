package th.mfu;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController controller;
    
    //create product and search
    @Test
    public void createAndSearch() {
        //create product
        Product p = new Product();
        p.setDescription("HelloWorld 32");
        p.setManufactureDate(LocalDate.of(2023, 1, 1));
        p.setName("HelloWorld");
        p.setPrice(2000.00);
         ResponseEntity<String> response = controller.addProduct(p);
         assertEquals(HttpStatus.CREATED, response.getStatusCode());

         //get
         ResponseEntity<List<Product>> getProduct = controller.getAllProducts();
         assertEquals(1, getProduct.getBody().size());
        
         //search
         ResponseEntity<List<Product>> res = controller.getProductsByDescription("HelloW");
         assertEquals(HttpStatus.OK, res.getStatusCode());
         assertEquals(1, res.getBody().size());

    }
    //create product and delete
    @Test
    public void createAndDelete() {
        //create
        Product p = new Product();
        p.setDescription("HelloWorld 32");
        p.setManufactureDate(LocalDate.of(2023, 1, 1));
        p.setName("HelloWorld");
        p.setPrice(2000.00);
         ResponseEntity<String> response = controller.addProduct(p);
         assertEquals(HttpStatus.CREATED, response.getStatusCode());
         //get 
          ResponseEntity<List<Product>> getProduct = controller.getAllProducts();
         assertEquals(1, getProduct.getBody().size());
         //delete
         ResponseEntity<String> deleteResponse = controller.delProduct(p.getId());
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        //get
          ResponseEntity<List<Product>> getProducts = controller.getAllProducts();
         assertEquals(0, getProducts.getBody().size());
         //check
          ResponseEntity<List<Product>> res = controller.getProductsByDescription("HelloW");
         assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }
}