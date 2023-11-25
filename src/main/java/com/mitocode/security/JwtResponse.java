package com.mitocode.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/*@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode*/
public record JwtResponse(String jwtToken) {

}
