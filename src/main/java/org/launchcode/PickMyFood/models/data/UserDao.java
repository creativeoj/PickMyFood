package org.launchcode.PickMyFood.models.data;
/**
 * Created by O.J SHIN
 */

import org.launchcode.PickMyFood.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);
}
