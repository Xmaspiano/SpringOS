package com.SpringOS.system.service.impl;

import com.SpringOS.system.entity.RelationshipUserRole;
import com.SpringOS.system.entity.User;
import com.SpringOS.system.repository.UserRepository;
import com.SpringOS.system.service.RelationshipUserRoleService;
import com.SpringOS.system.service.UserService;
import com.SpringOS.system.util.PasswordHelper;
import com.SpringOS.system.util.shiro.RetryLimitHashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl
        extends CommonServiceImpl<User, UserRepository>
        implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    RelationshipUserRoleService relationshipUserRoleRepositoryService;

    @Autowired
    RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher;

    public User save(User user){
        RelationshipUserRole relationshipUserRole = new RelationshipUserRole();

        relationshipUserRole.setRoleId(user.getRoleId());
        user.setSalt(retryLimitHashedCredentialsMatcher.getSalt());
        user.setPassword(retryLimitHashedCredentialsMatcher.encryptPassword(user.getPassword(), user.getCredentialsSalt()));
        System.out.println(user);
        user = super.save(user);

        relationshipUserRole.setUserId(user.getId());
        relationshipUserRoleRepositoryService.save(relationshipUserRole);
        return user;
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public User changePassword(Long userId, String newPassword){
        User user = super.findOne(userId);
        user.setPassword(newPassword);
        return this.save(user);
    }

    public User findByUsername(String username){
        return getRepository().findByUsername(username);
    }

}