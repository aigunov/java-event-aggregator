package ru.practicum.mainservice.service.implemetations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.data.dto.user.NewUserRequest;
import ru.practicum.mainservice.data.model.User;
import ru.practicum.mainservice.mapper.Mapper;
import ru.practicum.mainservice.repository.UserRepository;
import ru.practicum.mainservice.service.interfaces.UserService;
import ru.practicum.statsdto.dto.UserDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto createUser(final NewUserRequest dto) {

        User user = Mapper.toUser(dto);
        log.info("User created: {}", user);
        return Mapper.toUserDto(repository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("The required object was not found."));
        log.info("User deleted: {}", user);
        repository.delete(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Long> ids, PageRequest of) {
        if (ids.isEmpty()) {
            log.info("Founded page of all users");
            return repository.findAll(of).stream().map(Mapper::toUserDto).collect(Collectors.toList());
        }
        List<UserDto> users = repository.findAllByIdIn(ids, of)
                .stream()
                .map(Mapper::toUserDto)
                .collect(Collectors.toList());
        log.info("Users found: {}", users);
        return users;
    }
}
