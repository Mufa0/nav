package com.five.nav.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    long id;

    String name;

    String lastname;

    String email;

    //Rest of response object will be added
}
