package vn.nguyenanhtuan.eventapp.mapper;

import org.mapstruct.Mapper;
import vn.nguyenanhtuan.eventapp.dto.request.RoleReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.RoleResDto;
import vn.nguyenanhtuan.eventapp.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleReqDto req);

    RoleResDto toRoleResDto(Role role);
}
