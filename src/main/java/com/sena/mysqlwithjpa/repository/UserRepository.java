package com.sena.mysqlwithjpa.repository;

import com.sena.mysqlwithjpa.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}