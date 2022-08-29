package com.yueonsu.www.user;

import com.yueonsu.www.user.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 회원 등록 - 회원가입
     * @param entity
     * @return
     */
    int insUser(UserEntity entity);

    /**
     * 회원 조회 - 로그인, 아이디/이메일 중복검사
     * @param entity
     * @return
     */
    UserEntity selUser(UserEntity entity);
}
