package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nguyenanhtuan.eventapp.entity.RegisEvent;

import java.io.Serializable;

@Repository
public interface RegisEventRepository extends JpaRepository<RegisEvent, Integer> {
}
