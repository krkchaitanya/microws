package com.wsdeveloper.dragon.mobileappws.ui.controller;

import com.wsdeveloper.dragon.mobileappws.ui.modelResponses.UserDetails;
import com.wsdeveloper.dragon.mobileappws.ui.modelRequests.UserDetailsRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping(path="/defaultUserInf")
    public String defaultUserInf() {
        return "We are hitting the defalut route";
    }

	@GetMapping()
	public String fetchUserNmEm(@RequestParam(value="userName", defaultValue="Survivor", required = true)String userName, @RequestParam(value = "userEmail", defaultValue = "none@gmail.com", required = false)String userEmail) {
		return "user name and email fetched using the query parameters : Name - " + userName + " Email - " + userEmail;
	}

	@GetMapping(path="/{userID}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public UserDetails getUserDetailsObj(@PathVariable String userID)
    {
        UserDetails userDetails = new UserDetails();
		if (!StringUtils.isEmpty(userID)) {
            userDetails.setUserEmail("nancyWheeler221@gmail.com");
            userDetails.setUserName("nancy wheeler");
            userDetails.setUserLastName("blake");
            userDetails.setUserPassword("23lrandom");
        }
        return userDetails;
	}

	@GetMapping(path="/profession")
    public ResponseEntity getUserProfessionInf(@RequestParam(value="userId", defaultValue = "3344", required = false)String userId) {
        UserDetails userDetails = new UserDetails();
        if (!StringUtils.isEmpty(userId)) {
            userDetails.setUserEmail("jonnathan33@gmail.com");
            userDetails.setUserName("jonnathan heiil");
            userDetails.setUserLastName("sdkksdlksd");
        }
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }


    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public ResponseEntity<UserDetails> createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserDetails userDetails1 = new UserDetails();
        userDetails1.setUserName(userDetailsRequestModel.getFirstName());
        userDetails1.setUserLastName(userDetailsRequestModel.getLastName());
        userDetails1.setUserEmail(userDetailsRequestModel.getEmail());
        userDetails1.setUserPassword(userDetailsRequestModel.getPassword());
        return new ResponseEntity<UserDetails>(userDetails1, HttpStatus.OK);
    }

	@PutMapping
	public String updateUser() {
		return "update user was called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "Delete user was called";
	}
}
