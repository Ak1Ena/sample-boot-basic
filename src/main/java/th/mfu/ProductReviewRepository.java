package th.mfu;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductReviewRepository extends CrudRepository<ProductReview, Integer>{
    List<ProductReview> findAll();
    List<ProductReview> findByProductId(Integer id);
    List<ProductReview> findByCustomerId(Integer id);

}
