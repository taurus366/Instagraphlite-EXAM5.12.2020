package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);

    @Query("SELECT u FROM User u ORDER BY u.post.size DESC, u.id")
    List<User> exportAllUsersByPostOrderedByCountOfPostDescThenByUserId();
}
