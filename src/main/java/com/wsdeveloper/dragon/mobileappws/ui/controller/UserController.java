package com.wsdeveloper.dragon.mobileappws.ui.controller;

import com.wsdeveloper.dragon.mobileappws.ui.modelRequests.UserDetailsRequestModel;
import com.wsdeveloper.dragon.mobileappws.ui.modelResponses.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private HashMap<String, UserDetails> users = new HashMap<>();

    @GetMapping(path = "/alluserslist")
    public List<UserDetails> getAllUsers() {
        List<UserDetails> userslt = users.values().stream().collect(Collectors.toList());
        if (!userslt.isEmpty() || userslt == null) {
            return userslt;
        } else {
          return Collections.emptyList();
        }
    };

    @GetMapping(path="/defaultUserInf")
    public String defaultUserInf() {
        return "We are hitting the defalut route";
    }

	@GetMapping()
	public String fetchUserNmEm(@RequestParam(value="userName", defaultValue="Survivor", required = true)String userName, @RequestParam(value = "userEmail", defaultValue = "none@gmail.com", required = false)String userEmail) {
		return "user name and email fetched using the query parameters : Name - " + userName + " Email - " + userEmail;
	}

	@GetMapping(path="/{userID}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserDetails> getUserDetailsObj(@PathVariable String userID)
    {
        UserDetails userDetailsHH = new UserDetails();
		if (!StringUtils.isEmpty(userID)) {
            return new ResponseEntity<UserDetails>(users.get(userID), HttpStatus.OK);
        } else {
            return new ResponseEntity<UserDetails>(userDetailsHH, HttpStatus.OK);
        }
	};

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
    public ResponseEntity<UserDetails> createUser(@Valid @RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserDetails userDetails1 = new UserDetails();
        userDetails1.setUserName(userDetailsRequestModel.getFirstName());
        userDetails1.setUserLastName(userDetailsRequestModel.getLastName());
        userDetails1.setUserEmail(userDetailsRequestModel.getEmail());
        userDetails1.setUserPassword(userDetailsRequestModel.getPassword());

        String userId = UUID.randomUUID().toString();
        userDetails1.setUserId(userId);

        if(users == null) users = new HashMap<String, UserDetails>();
        users.put(userId, userDetails1);

        return new ResponseEntity<UserDetails>(userDetails1, HttpStatus.OK);
    }

	@PutMapping(path="/updateUserDetails")
	public UserDetails updateUser(@RequestBody UserDetailsRequestModel userDtlsRqstmodel) {
	    String userid = userDtlsRqstmodel.getUserid();
	    System.out.println("--Updating the user: " + userid);
	    if (!StringUtils.isEmpty(userid) && userid != null && users.containsKey(userid)) {
            users.get(userid).setUserEmail(userDtlsRqstmodel.getEmail());
            users.get(userid).setUserName(userDtlsRqstmodel.getFirstName());
            users.get(userid).setUserLastName(userDtlsRqstmodel.getLastName());
            users.get(userid).setUserPassword(userDtlsRqstmodel.getPassword());
            return users.get(userid);
        } else {
            System.out.println("--User details update operation failed --");
	        return new UserDetails();
        }
	}
	
	@DeleteMapping(path = "/deleteuser/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        System.out.println("--Deleting the user with id : " + id);
        List<UserDetails> filteredUsersList = users.values().stream().filter(ele -> ele.getUserId().equalsIgnoreCase(id)).collect(Collectors.toList());

		if (!StringUtils.isEmpty(id) && id != null && !filteredUsersList.isEmpty() && filteredUsersList != null) {
            users.remove(id);
            return ResponseEntity.noContent().build();
        } else {
            System.out.println(" --Please verify the userid-- ");
            return ResponseEntity.badRequest().build();
        }
	};
}
