package com.omnirio.customer.repository;

import com.omnirio.customer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {

    Optional<UserEntity> findByUserName(String userName);

}
