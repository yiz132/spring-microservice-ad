package com.james.ad.service.impl;

import com.james.ad.constant.Constants;
import com.james.ad.dao.AdUserRepository;
import com.james.ad.entity.AdUser;
import com.james.ad.exception.AdException;
import com.james.ad.service.IUserService;
import com.james.ad.utils.CommonUtils;
import com.james.ad.vo.CreateUserRequest;
import com.james.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    private final AdUserRepository adUserRepository;

    public UserServiceImpl(AdUserRepository adUserRepository) {
        this.adUserRepository = adUserRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = adUserRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser = adUserRepository.save(new AdUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername())
        ));
        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(),newUser.getUpdateTime());
    }
}
