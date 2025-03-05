package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.nguyenanhtuan.eventapp.entity.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> , JpaSpecificationExecutor<Event> {
    List<Event> findAllByStatus(String status);
}
