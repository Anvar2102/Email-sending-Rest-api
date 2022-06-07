package uz.pdp.appjwtemailauth.Repository;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appjwtemailauth.Entity.Product;

import java.util.UUID;

@RepositoryRestResource(path = "product")

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
