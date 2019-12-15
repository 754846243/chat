package com.group.chat.repository;

import com.group.chat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 陈雨菲
 * @description UserJpaRepository
 * @data
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByUsername(String username);

    public User findUserById(Integer id);

    public User save(User user);
}
