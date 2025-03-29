package vn.nguyenanhtuan.eventapp.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nguyenanhtuan.eventapp.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
}
