package com.SpringOS.system.repository;

import com.SpringOS.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface UserRepository extends CrudRepository<User,Long>,CommonRepository<User, UserRepository> {

    public User findByUsername(String username);
}
