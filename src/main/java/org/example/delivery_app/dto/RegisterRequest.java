package org.example.delivery_app.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private Integer attachmentId;
}
