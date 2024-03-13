package com.oalvarez.backend.usuarios.apirest.services;

import com.oalvarez.backend.usuarios.apirest.dao.IUserDao;
import com.oalvarez.backend.usuarios.apirest.dto.CreateResponseDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserResponseDTO;
import com.oalvarez.backend.usuarios.apirest.entity.Role;
import com.oalvarez.backend.usuarios.apirest.entity.User;
import com.oalvarez.backend.usuarios.apirest.mapper.CreateResponseMapper;
import com.oalvarez.backend.usuarios.apirest.mapper.UserDtoResponseMapper;
import com.oalvarez.backend.usuarios.apirest.mapper.UserRequestMapper;
import com.oalvarez.backend.usuarios.apirest.mapper.UserResponseMapper;
import com.oalvarez.backend.usuarios.apirest.mapper.UsersResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {

    @Mock
    private IUserDao userDao;

    @Mock
    private UserRequestMapper requestUserMapper;

    @Mock
    private UserResponseMapper responseUserMapper;

    @Mock
    private CreateResponseMapper createResponseMapper;

    @Mock
    private UserDtoResponseMapper responseUserDtoMapper;

    @Mock
    private UsersResponseMapper responseUsersMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testLoadUserByUsernameWhenUserExists() {
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword("password");
        mockUser.setEnabled(true);
        List <Role> roles = new ArrayList<>();
        Role rol = new Role();
        rol.setId(1L);
        rol.setNombre("nombreRol");
        roles.add(rol);
        mockUser.setRoles(roles);

        when(userDao.findByEmail(email)).thenReturn(mockUser);

        UserDetails userDetails = userService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());

        verify(userDao, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsernameWhenUserDoesNotExist() {
        String email = "nonexistent@example.com";

        when(userDao.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(email);
        });

        verify(userDao, times(1)).findByEmail(email);
    }

    @Test
    public void testFindAll() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        users.add(user1);
        users.add(user2);

        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        userResponseDTOs.add(new UserResponseDTO());
        userResponseDTOs.add(new UserResponseDTO());

        when(userDao.findAll()).thenReturn(users);
        when(responseUsersMapper.map(anyList())).thenReturn(userResponseDTOs);

        List<UserResponseDTO> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(userDao, times(1)).findAll();
        verify(responseUsersMapper, times(1)).map(anyList());
    }

    @Test
    public void testCreate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        User mappedUser = new User();
        mappedUser.setEmail(userDTO.getEmail());

        when(requestUserMapper.map(userDTO)).thenReturn(mappedUser);
        when(userDao.save(mappedUser)).thenReturn(mappedUser);
        when(createResponseMapper.map(mappedUser)).thenReturn(new CreateResponseDTO());

        CreateResponseDTO result = userService.create(userDTO);

        assertNotNull(result);
        verify(userDao, times(1)).save(any(User.class));
        verify(requestUserMapper, times(1)).map(userDTO);
        verify(createResponseMapper, times(1)).map(any(User.class));
    }

    @Test
    public void testFindByIdWhenUserExists() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        UserResponseDTO mockResponseDTO = new UserResponseDTO();

        when(userDao.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
        when(responseUserMapper.map(mockUser)).thenReturn(mockResponseDTO);

        UserResponseDTO result = userService.findById(userId);

        assertNotNull(result);
        verify(userDao, times(1)).findById(userId);
        verify(responseUserMapper, times(1)).map(mockUser);
    }

    @Test
    public void testFindByIdWhenUserDoesNotExist() {
        Long userId = 2L;
        when(userDao.findById(userId)).thenReturn(java.util.Optional.empty());

        UserResponseDTO result = userService.findById(userId);

        assertNull(result);
        verify(userDao, times(1)).findById(userId);
    }

    @Test
    public void testUpdate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("update@example.com");

        User updatedUser = new User();
        updatedUser.setId(userDTO.getId());
        updatedUser.setEmail(userDTO.getEmail());

        when(requestUserMapper.map(userDTO)).thenReturn(updatedUser);
        when(userDao.save(updatedUser)).thenReturn(updatedUser);
        when(responseUserMapper.map(updatedUser)).thenReturn(new UserResponseDTO());

        UserResponseDTO result = userService.update(userDTO);

        assertNotNull(result);
        verify(userDao, times(1)).save(any(User.class));
        verify(requestUserMapper, times(1)).map(userDTO);
        verify(responseUserMapper, times(1)).map(any(User.class));
    }

    @Test
    public void testFindByUuidWhenUserExists() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        User mockUser = new User();
        mockUser.setUuid(uuid);

        when(userDao.findByUuid(uuid)).thenReturn(mockUser);
        when(responseUserMapper.map(mockUser)).thenReturn(new UserResponseDTO());

        UserResponseDTO result = userService.findByUuid(uuid);

        assertNotNull(result);
        verify(userDao, times(1)).findByUuid(uuid);
        verify(responseUserMapper, times(1)).map(mockUser);
    }

    @Test
    public void testFindByUuidUpdateWhenUserExists() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        User mockUser = new User();
        mockUser.setUuid(uuid);

        when(userDao.findByUuid(uuid)).thenReturn(mockUser);
        when(responseUserDtoMapper.map(mockUser)).thenReturn(new UserDTO());

        UserDTO result = userService.findByUuidUpdate(uuid);

        assertNotNull(result);
        verify(userDao, times(1)).findByUuid(uuid);
        verify(responseUserDtoMapper, times(1)).map(mockUser);
    }

    @Test
    public void testExistsByEmail() {
        String email = "exist@test.com";
        when(userDao.existsByEmail(email)).thenReturn(true);

        boolean exists = userService.existsByEmail(email);

        assertTrue(exists);
        verify(userDao, times(1)).existsByEmail(email);
    }

}