package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nguyenanhtuan.eventapp.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

}
