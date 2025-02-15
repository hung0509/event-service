package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nguyenanhtuan.eventapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
