package vn.unigap.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  // Method to find a User by their email
  Optional<User> findByEmail(String email);
}
