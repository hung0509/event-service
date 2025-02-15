package vn.nguyenanhtuan.eventapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.dto.request.FacultyReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.FacultyResDto;
import vn.nguyenanhtuan.eventapp.entity.Faculty;
import vn.nguyenanhtuan.eventapp.mapper.FacultyMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.FacultyRepository;
import vn.nguyenanhtuan.eventapp.service.FacultyService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FacultyServiceImpl implements FacultyService {
    FacultyRepository facultyRepository;
    FacultyMapper facultyMapper;

    @Override
    @Transactional
    public FacultyResDto save(FacultyReqDto req) {
        Faculty faculty = facultyMapper.toFaculty(req);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toFacultyResDto(faculty);
    }

    @Override
    public List<FacultyResDto> getAll() {
        List<Faculty> facultyList = facultyRepository.findAll();

        return facultyList.stream().map(facultyMapper::toFacultyResDto).toList();
    }
}
