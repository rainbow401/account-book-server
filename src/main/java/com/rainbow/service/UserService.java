package com.rainbow.service;


import com.rainbow.auth.dto.LoginDTO;

public interface UserService {

    boolean login(LoginDTO dto);

}
