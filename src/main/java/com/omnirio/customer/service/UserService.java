package com.omnirio.customer.service;

import com.omnirio.customer.entity.RoleEntity;
import com.omnirio.customer.entity.UserEntity;
import com.omnirio.customer.repository.UserRepository;
import com.omnirio.customer.util.Util;
import com.omnirio.customer.vo.Gender;
import com.omnirio.customer.vo.UserRequest;
import com.omnirio.customer.vo.UserResponse;
import com.omnirio.customer.vo.UserType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(UserRequest userRequest) {

        //TODO: add some user validation code & throw exceptions
        UserEntity userEntity = mapTo(userRequest, Util.getUUID());
        userEntity.getRoleEntity().setRoleId(Util.getUUID());

        UserEntity result = userRepository.save(userEntity);

        return result.getUserId();
    }

    public UserResponse getUser(String userId) {

        UserEntity result = userRepository.getOne(userId);

        return mapTo(result);
    }

    public List<UserResponse> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        List<UserResponse> result = mapTo(userEntities);
        return result;
    }

    public UserResponse updateUser(UserRequest userRequest, String userId) {
        //first find the Customer/User details by given userId
        UserEntity existingEntity = userRepository.getOne(userId);
        if (existingEntity == null) {
            //TODO throw error that entity doesn't exist
        }
        //update the entity with all fields sent in request by keeping the userId as it is.
        UserEntity updatedEntity = mapTo(userRequest, userId);
        updatedEntity.getRoleEntity().setRoleId(existingEntity.getRoleEntity().getRoleId());

        UserEntity result = userRepository.save(updatedEntity);

        return mapTo(result);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    private UserEntity mapTo(UserRequest userRequest, String userId) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setDateOfBirth(userRequest.getDateOfBirth());
        if (userRequest.getGender().equals(Gender.MALE)) {
            userEntity.setGender("M");
        }
        if (userRequest.getGender().equals(Gender.FEMALE)) {
            userEntity.setGender("F");
        }
        userEntity.setPhoneNumber(userRequest.getPhoneNumber());
        userEntity.setRoleEntity(mapTo(userRequest.getUserType(), userId));

        return userEntity;
    }

    private RoleEntity mapTo(UserType userType, String userId) {
        RoleEntity roleEntity = new RoleEntity();
        switch (userType) {
            case CUSTOMER -> {
                roleEntity.setRoleCode("Code01" + userId);
                roleEntity.setRoleName(UserType.CUSTOMER.name() + userId);
                roleEntity.setRoleType(UserType.CUSTOMER);
                break;
            }
            case BRANCH_MANAGER -> {
                roleEntity.setRoleCode("Code02" + userId);
                roleEntity.setRoleName(UserType.BRANCH_MANAGER.name() + userId);
                roleEntity.setRoleType(UserType.BRANCH_MANAGER);
                break;
            }
        }
        return roleEntity;
    }

    private List<UserResponse> mapTo(List<UserEntity> userEntities) {
        return userEntities
                .parallelStream()
                .map(this::mapTo)
                .collect(Collectors.toList());
    }

    private UserResponse mapTo(UserEntity userEntity) {

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userEntity.getUserId());
        userResponse.setUserName(userEntity.getUserName());
        userResponse.setDateOfBirth(userEntity.getDateOfBirth());
        String gender = userEntity.getGender();
        if (StringUtils.isNotBlank(gender)) {
            if (gender.equalsIgnoreCase("M")) {
                userResponse.setGender(Gender.MALE);
            }
            if (gender.equalsIgnoreCase("F")) {
                userResponse.setGender(Gender.FEMALE);
            }
        }
        userResponse.setPhoneNumber(userEntity.getPhoneNumber());
        userResponse.setUserType(userEntity.getRoleEntity().getRoleType());

        return userResponse;
    }

}
