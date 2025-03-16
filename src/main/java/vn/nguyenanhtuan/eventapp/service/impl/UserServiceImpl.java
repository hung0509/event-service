package vn.nguyenanhtuan.eventapp.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.dto.request.FacultyUpdateReq;
import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.request.UserUpdateReq;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.entity.Role;
import vn.nguyenanhtuan.eventapp.entity.User;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.UserMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.RoleRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.UserRepository;
import vn.nguyenanhtuan.eventapp.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JavaMailSender mailSender;
    AmazonS3 amazonS3;

    @NonFinal
    @Value("${aws.bucket}")
    String AWS_BUCKET;

    @NonFinal
    @Value("${aws.folder}")
    String AWS_FOLDER;



    public UserResponseDto save(UserRequestDto req) {
        if(userRepository.existsByEmail(req.getEmail()))
            throw new GlobalException(ErrorCode.USER_ALREADY_EXIST);

        User user = userMapper.toUser(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        Role role = roleRepository.findById(3).orElseThrow(() -> new GlobalException(ErrorCode.ROLE_NOT_EXIST));

        user.setRole(role);
        user = userRepository.save(user);

        return userMapper.toUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAll() {
        var list = userRepository.findAll();
        return list.stream().map(userMapper::toUserResponseDto).toList();
    }

    @Override
    public List<UserResponseDto> getByRole(String name) {
        Role role = roleRepository.findByRoleName(name);

        if(Objects.isNull(role)){
            throw new GlobalException(ErrorCode.ROLE_NOT_EXIST);
        }

        var list = userRepository.findByRole(role);
        return list.stream().map(userMapper::toUserResponseDto).toList();
    }

    @Override
    public UserResponseDto update(UserUpdateReq req, int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_EXIST));
        log.info(req.getPassword());

            if(req.getFacultyName() != null)
                user.setFacultyName(req.getFacultyName());


            if(req.getFacultyDescription() != null)
                user.setFacultyDescription(req.getFacultyDescription());


            if(req.getPassword() != null)
                user.setPassword(passwordEncoder.encode(req.getPassword()));

            if(req.getUsername() != null)
                user.setUsername(req.getUsername());

        if(req.getPhone() != null)
            user.setPhone(req.getPhone());

        user = userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateFaculty(FacultyUpdateReq req, int id)  {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_EXIST));
//        String password = (user.getPassword() != null) ? passwordEncoder.encode(user.getPassword()) : null;

        if(req.getFacultyName() != null)
            user.setFacultyName(req.getFacultyName());

        try {
            if (req.getFacultyLogo() != null)
                user.setFacultyLogo(uploadImage(req.getFacultyLogo()));
        }catch(Exception e){

        }

        if(req.getFacultyDescription() != null)
            user.setFacultyDescription(req.getFacultyDescription());

        if(req.getPassword() != null)
            user.setPassword(passwordEncoder.encode(req.getPassword()));

//        if(password != null)
//            user.setPassword(password);

        user = userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_EXIST));

        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto registerFaculty(UserRequestDto req) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(req.getEmail());
        message.setSubject("Đăng ký tổ chức mơ");
        message.setText("Email: " + req.getEmail() + "/n Password: abc@123");
        message.setFrom("hungtaithe12@gmail.com");
        mailSender.send(message);

        Role role = roleRepository.findById(2).orElseThrow(() -> new GlobalException(ErrorCode.ROLE_NOT_EXIST));

        User user = userMapper.toUser(req);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode("abc@123"));
        user = userRepository.save(user);

        return UserResponseDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .facultyName(user.getFacultyName())
                .build();
    }

    private String uploadImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        InputStream inputStream = file.getInputStream();

        //Kiểm tra nếu không phải là ảnh thì không cho phép tiếp tục
        if (!contentType.equals("image/jpeg")
                && !contentType.equals("image/png")
                && !contentType.equals("image/webp")
                && !contentType.equals("image/gif")
                && !contentType.equals("image/bmp")) {
            throw new GlobalException(ErrorCode.NOT_VALID_FORMAT_IMAGE);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(file.getSize());

        String keyName = AWS_FOLDER + "/" + file.getOriginalFilename();
        PutObjectRequest request = new PutObjectRequest(AWS_BUCKET, keyName, inputStream, metadata);
        amazonS3.putObject(request);

        URL url = amazonS3.getUrl(AWS_BUCKET, keyName);
        return url.toString();
    }
}
