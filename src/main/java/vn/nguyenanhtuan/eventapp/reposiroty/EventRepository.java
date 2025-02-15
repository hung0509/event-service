package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nguyenanhtuan.eventapp.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
