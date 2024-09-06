package ru.practicum.mainservice.service.implemetations;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.service.interfaces.UserService;
import ru.practicum.statsdto.dto.UserDto;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDto createUser(UserDto dto) {
        return null;
    }

    @Override
    public void deleteUser(int userId) {

    }

    @Override
    public List<UserDto> getUsers(int[] ids, PageRequest of) {
        return List.of();
    }
}
