package ru.practicum.mainservice.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.statsdto.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto dto);

    void deleteUser(int userId);

    List<UserDto> getUsers(int[] ids, PageRequest of);
}
