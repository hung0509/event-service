package vn.nguyenanhtuan.eventapp.mapper;

import org.mapstruct.Mapper;
import vn.nguyenanhtuan.eventapp.dto.request.FacultyReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.FacultyResDto;
import vn.nguyenanhtuan.eventapp.entity.Faculty;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    Faculty toFaculty(FacultyReqDto req);

    FacultyResDto toFacultyResDto(Faculty faculty);
}
