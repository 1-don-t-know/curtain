package com.sparta.curtain.service;

import com.sparta.curtain.dto.*;
import com.sparta.curtain.entity.Post;
import com.sparta.curtain.entity.User;
import com.sparta.curtain.entity.UserRoleEnum;
import com.sparta.curtain.entity.TokenBlacklist;
import com.sparta.curtain.repository.PostRepository;
import com.sparta.curtain.repository.TokenBlacklistRepository;
import com.sparta.curtain.repository.UserRepository;
import com.sparta.curtain.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final PostRepository postRepository;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원가입
    // - username, password를 Client에서 전달받기
    // - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
    // - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자`로 구성되어야 한다.
    // - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    // - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
    // 로그인
    // - username, password를 Client에서 전달받기
    // - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
    // - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고,
    // - 발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
    public void login(AuthRequestDto authRequestDto) {
        String email = authRequestDto.getEmail();
        String password = authRequestDto.getPassword();


        //유저 이메일 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록 된 사용자가 없습니다.")
        );

        //유저 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


    }

    //로그아웃
    @Transactional
    public void logout(String token) {
        //로그아웃이 될 경우 토큰을 로그아웃 리스트에 추가
        TokenBlacklist tokenBlacklist = new TokenBlacklist(token);
        tokenBlacklistRepository.save(tokenBlacklist);
    }

//    public void confirmEmail(String email, String authKey) {
//        User user = userRepository.findByEmail(email).orElseThrow(() ->new IllegalArgumentException("등록된 사용자가 없습니다."));
//        if (!user.getAuthKey().equals(authKey)) {
//            throw new IllegalArgumentException("이메일 인증에 실패했습니다.");
//        }
//        user.setIsConfirmTrue();
//    }

    // 프로필 조회
    public ProfileResponseDto getMyPage(User user) {
        List<ProfilePostListResponseDto> postList = postRepository.findAllByUser(user).stream()
                .map(ProfilePostListResponseDto::new)
                .sorted(Comparator.comparing(ProfilePostListResponseDto::getCreatedAt).reversed()) // 작성날짜 내림차순
                .collect(Collectors.toList());

        return new ProfileResponseDto(user, postList);
    }


    // 비밀번호 확인
    public boolean confirmPassword(UserDetailsImpl userDetails, String password) {
        if(passwordEncoder.matches(password, userDetails.getPassword())) {
            return true;
        } else {
            return false;
        }
    }


    // 비밀번호 변경
    @Transactional
    public ResponseEntity<String> updatePassword(User user, String newPassword) {
        String password = passwordEncoder.encode(newPassword);
        user.setPassword(password);
        userRepository.save(user);
        return ResponseEntity.ok("Success");
    }


    // 전체 회원 목록 조회
    public UserListResponseDto getUserList() {
        List<UserResponseDto> userList = userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());

        return new UserListResponseDto(userList);
    }

    public UserListResponseDto searchUsers(String keyword) {
        List<UserResponseDto> userList = userRepository.findByEmailContaining(keyword).stream().map(UserResponseDto::new).collect(Collectors.toList());

        return new UserListResponseDto(userList);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
    }
}
