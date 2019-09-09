package com.wsdeveloper.dragon.mobileappws.ui.modelRequests;

import lombok.Data;

@Data
public class UserDetailsRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
