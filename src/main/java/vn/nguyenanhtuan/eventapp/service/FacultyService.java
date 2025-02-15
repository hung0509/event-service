package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.request.FacultyReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.FacultyResDto;

import java.util.List;

public interface FacultyService {
    public FacultyResDto save(FacultyReqDto facultyReqDto);

    public List<FacultyResDto> getAll();
}
