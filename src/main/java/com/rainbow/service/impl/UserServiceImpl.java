package com.rainbow.service.impl;


import com.rainbow.auth.dto.LoginDTO;
import com.rainbow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public boolean login(LoginDTO dto) {
        verifyPassword(dto);

        return true;
    }

    private void verifyPassword(LoginDTO dto) {
        // todo 密码验证
    }
}
