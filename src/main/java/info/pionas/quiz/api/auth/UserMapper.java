package info.pionas.quiz.api.auth;

import info.pionas.quiz.domain.user.api.NewUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface UserMapper {

    @Mapping(target = "roles", expression = "java(java.util.List.of(info.pionas.quiz.domain.user.api.Role.ROLE_USER))")
    NewUser map(NewUserRequest newUserRequest);
}
