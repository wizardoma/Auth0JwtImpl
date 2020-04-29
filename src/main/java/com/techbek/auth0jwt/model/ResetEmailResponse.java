package com.techbek.auth0jwt.model;

import lombok.Data;

@Data
public class ResetEmailResponse {
    private String email;
    private String uuid;
    private String newpassword;
}
