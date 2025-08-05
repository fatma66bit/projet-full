package tn.enis.Mapper;

import tn.enis.Dto.UserDTO;
import tn.enis.Entity.User;
import tn.enis.Role;

public class UserMapper {

    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole().name()); // ✅ Ajout du rôle
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setPassword(dto.getPassword());
        if (dto.getRole() != null) {
            user.setRole(Role.valueOf(dto.getRole())); // ✅ Mappage vers enum
        }
        return user;
    }
}

