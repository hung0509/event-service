package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nguyenanhtuan.eventapp.entity.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByStatus(String status);
}
