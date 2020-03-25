package springboot.libraryApp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.libraryApp.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByRole(String role);
}
