package com.auth.server.controller;

import com.auth.server.config.JwtTokenUtil;
import com.auth.server.dto.request.AuthReq;
import com.auth.server.dto.response.CommonRes;
import com.auth.server.service.UserDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;
    private JwtTokenUtil tokenUtil;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtTokenUtil tokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<?> generateToken(@RequestBody AuthReq authReqDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReqDTO.getUsername(), authReqDTO.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authReqDTO.getUsername());
        final String jwt = tokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new CommonRes(jwt), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/validate")
    public ResponseEntity<?> validateToken(@RequestBody AuthReq authReqDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReqDTO.getUsername(), authReqDTO.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authReqDTO.getUsername());
        final String jwt = tokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new CommonRes(jwt), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Test API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hello"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping(path = "/test")
    public ResponseEntity<?> sayHello() {
        return ResponseEntity.ok("Hello");
    }
}
