package com.james.ad.service;

import com.james.ad.exception.AdException;
import com.james.ad.vo.CreateUserRequest;
import com.james.ad.vo.CreateUserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request)
            throws AdException;
}
