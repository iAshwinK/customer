package com.omnirio.customer.repository;

import com.omnirio.customer.entity.RoleEntity;
import com.omnirio.customer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,String> {

}
