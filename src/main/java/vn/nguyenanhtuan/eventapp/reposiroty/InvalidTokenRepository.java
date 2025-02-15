package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nguyenanhtuan.eventapp.entity.InvalidToken;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
