package com.example.service;

import com.example.dto.AuthDTO;
import com.example.dto.AuthResponseDTO;
import com.example.dto.RegistrationDTO;
import com.example.dto.RegistrationResponseDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import com.example.exps.AppBadRequestException;
import com.example.exps.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Value("${server.host}")
    private String serverHost;

    public void sendRegistrationEmail(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Registration verification.\n");
        stringBuilder.append("Click to below link to complete registration\n");
        stringBuilder.append("Link: ");
        stringBuilder.append(serverHost).append("/api/v1/auth/email/verification/");
        stringBuilder.append(JwtUtil.encode(toAccount));
        // https://kun.uz/api/v1/auth//email/verification/dasdasdasd.asdasdad.asda
        // localhost:8080/api/v1/auth/email/verification/dasdasdasd.asdasdad.asda
//        sendEmail(toAccount, "Registration", stringBuilder.toString());
    }

    public AuthResponseDTO login(AuthDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisible(
                dto.getEmail(),
                MD5Util.getMd5Hash(dto.getPassword()),
                true);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email or password incorrect");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadRequestException("Wrong status");
        }
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return responseDTO;
    }
//    public RegistrationResponseDTO registration(RegistrationDTO dto) {
//        // check -?
//        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
//        if (optional.isPresent()) {
//            throw new ItemNotFoundException("Email already exists mazgi.");
//        }
//        ProfileEntity entity = new ProfileEntity();
//        entity.setName(dto.getName());
//        entity.setSurname(dto.getSurname());
//        entity.setRole(ProfileRole.USER);
//        entity.setPhone(dto.getPhone());
//        entity.setEmail(dto.getEmail());
//        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
//        entity.setStatus(GeneralStatus.REGISTER);
//        profileRepository.save(entity);
//        String s = "Verification link was send to email: " + dto.getEmail();
//        return new RegistrationResponseDTO(s);
//    }

    public RegistrationResponseDTO emailVerification(String jwt) {
        // asjkdhaksdh.daskhdkashkdja
        String email = JwtUtil.decodeEmailVerification(jwt);
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email not found.");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.REGISTER)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(entity);
        return new RegistrationResponseDTO("Registration Done");
    }
    public RegistrationResponseDTO registration(RegistrationDTO dto) {
        // check -?
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ItemNotFoundException("Email already exists mazgi.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(ProfileRole.USER);
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTER);
        // send email
//        mailSenderService.sendRegistrationEmail(dto.getEmail());
        // save
        profileRepository.save(entity);
        String s = "Verification link was send to email: " + dto.getEmail();
        return new RegistrationResponseDTO(s);
    }
}
