package org.example.newsfeed_project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.*;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserProfilePutResponseDto userProfilePutService(Long id, UserProfilePutRequestDto requestDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User findUser = optionalUser.get();

        findUser.updateIntroduction(requestDto.getIntroduction());
        return new UserProfilePutResponseDto(findUser.getIntroduction());
    }

    public MessageResponseDto SignUp(SignUpRequestDto request){
        userRepository.save(new User(request.getEmail(),request.getPassword(), request.getUserName(), request.getIntroduction() ,true));
    return new MessageResponseDto("생성 완료 되었습니다.");
    }

    public List<UserFindAllResponseDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(UserFindAllResponseDto::toDto)
                .toList();
    }
}
