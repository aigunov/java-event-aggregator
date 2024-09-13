package ru.practicum.mainservice.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.mainservice.data.dto.NewUserRequest;
import ru.practicum.statsdto.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest dto);

    void deleteUser(Long userId);

    List<UserDto> getUsers(List<Long> ids, PageRequest of);
}
