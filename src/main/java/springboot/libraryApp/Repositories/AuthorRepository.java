package springboot.libraryApp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springboot.libraryApp.models.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {
}
