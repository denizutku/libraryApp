package springboot.libraryApp.Security;

import org.springframework.stereotype.Service;
import springboot.libraryApp.models.User;

@Service
public interface UserService {

    public User findUserByEmail(String email) ;
    public User saveUser(User user);
}

