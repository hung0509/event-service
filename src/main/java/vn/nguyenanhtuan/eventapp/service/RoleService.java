package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.request.RoleReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.RoleResDto;

public interface RoleService {
    public RoleResDto save(RoleReqDto req);
}
