package com.SpringOS.system.service;


import com.SpringOS.system.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService extends CommonService<User> {
    public User changePassword(Long userId, String newPassword);

    public User findByUsername(String username);
}
