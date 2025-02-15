package vn.nguyenanhtuan.eventapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "invalid_token")
public class InvalidToken {
    @Id
    String id;

    @Column(name = "expire_date")
    Date expireDate;
}
