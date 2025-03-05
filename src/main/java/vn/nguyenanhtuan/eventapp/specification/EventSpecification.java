package vn.nguyenanhtuan.eventapp.specification;

import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventReqDto;
import vn.nguyenanhtuan.eventapp.entity.Event;

import java.time.Instant;
import java.util.Objects;

public class EventSpecification {
    public static Specification<Event> isStatus(String status)
    {
        return (root, query, criteriaBuilder) -> status == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("status"), status);
    }

    public static Specification<Event> isTitleOrLocation(String key) {
        return (root, query, criteriaBuilder) -> {
            if (key == null) {
                return criteriaBuilder.conjunction();
            }
            String pattern = "%" + key + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), pattern),
                    criteriaBuilder.like(root.get("location"), pattern)
            );
        };
    }

    public static Specification<Event> isTitle(String title)
    {
        return (root, query, criteriaBuilder) -> title == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("title"), "%"+title+"%");
    }

    public static Specification<Event> startDate(String startDate)
    {
        return (root, query, criteriaBuilder) -> {
            if(startDate == null){
                return criteriaBuilder.conjunction();
            }

            String date = startDate + " 00:00:00";
            return criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), Instant.parse(date));
        };
    }

    public static Specification<Event> endDate(String endTo) {
        return (root, query, criteriaBuilder) -> {
            if(endTo == null){
                return criteriaBuilder.conjunction();
            }

            String date = endTo + " 23:59:59";
            return criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), Instant.parse(date));
        };
    }

    public static Specification<Event> category(Integer id) {
        return (root, query, criteriaBuilder) -> Objects.isNull(id) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("category").get("id"), id);
    }


    public static Specification<Event> getSpecification(RegisEventReqDto req) {
        Specification<Event> spec = Specification.where(isStatus(req.getStatus()))
                .and(isTitle(req.getTitle()))
                .and(isStatus(req.getStatus()))
                .and(isTitleOrLocation(req.getKey()))
                .and(startDate(req.getStartDate()))
                .and(category(req.getCategoryId()))
                .and(endDate(req.getEndDate()));
        return spec;

    }
}
