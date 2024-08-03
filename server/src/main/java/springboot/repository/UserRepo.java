package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entity.Users;
@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
}
