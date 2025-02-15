package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nguyenanhtuan.eventapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
