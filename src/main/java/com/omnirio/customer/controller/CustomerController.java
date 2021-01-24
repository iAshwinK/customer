package com.omnirio.customer.controller;

import com.omnirio.customer.service.UserService;
import com.omnirio.customer.vo.UserRequest;
import com.omnirio.customer.vo.UserResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    @ApiOperation(value = "Create Customer",tags = {"Customer"})
    public ResponseEntity<String> create(@RequestBody UserRequest userRequest){
        String response = userService.createUser(userRequest);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/{customerId}")
    @ApiOperation(value = "Retrieve Customer Info",tags = {"Customer"})
    public ResponseEntity<UserResponse> getCustomer(@PathVariable(value = "customerId") String customerId){
        UserResponse response = userService.getUser(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation(value = "Retrieve All Customer Info",tags = {"Customer"})
    public ResponseEntity<List<UserResponse>> getCustomerList(){
        List<UserResponse> response = userService.getUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{customerId}")
    @ApiOperation(value = "Update Customer Info",tags = {"Customer"})
    public ResponseEntity<UserResponse> updateCustomer(@RequestBody UserRequest userRequest,@PathVariable(value = "customerId") String customerId){
        UserResponse response = userService.updateUser(userRequest,customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    @ApiOperation(value = "Delete Customer Info",tags = {"Customer"})
    public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "customerId") String customerId){
        userService.deleteUser(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
