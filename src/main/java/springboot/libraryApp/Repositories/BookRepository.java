package springboot.libraryApp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springboot.libraryApp.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
}
