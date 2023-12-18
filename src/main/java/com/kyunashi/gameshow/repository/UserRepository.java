package com.kyunashi.gameshow.repository;

import com.kyunashi.gameshow.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
