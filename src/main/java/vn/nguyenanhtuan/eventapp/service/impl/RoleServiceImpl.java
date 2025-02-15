package vn.nguyenanhtuan.eventapp.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.dto.request.RoleReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.RoleResDto;
import vn.nguyenanhtuan.eventapp.entity.Role;
import vn.nguyenanhtuan.eventapp.reposiroty.RoleRepository;
import vn.nguyenanhtuan.eventapp.service.RoleService;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    @Override
    public RoleResDto save(RoleReqDto req) {
        Role role = new Role();
        role.setRoleName(req.getRoleName());

        role = roleRepository.save(role);

        return RoleResDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }
}
