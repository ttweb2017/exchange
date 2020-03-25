package com.voicetm.karaoke.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.voicetm.karaoke.config.JwtTokenUtil;
import com.voicetm.karaoke.domain.User;
import com.voicetm.karaoke.domain.Views;
import com.voicetm.karaoke.model.JwtRequest;
import com.voicetm.karaoke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.voicetm.karaoke.model.JwtResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userDetailsService;

    @Autowired
    public JwtAuthenticationController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            UserService userDetailsService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userDetailsService.addUser(user));
    }

    @JsonView(Views.IdName.class)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User user){
        Map<String, Object> s = new HashMap<>();
        s.put("user", Objects.requireNonNull(user));
        return ResponseEntity.ok(s);
    }

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public ResponseEntity<?> activate(@PathVariable String code) {
        boolean isActivated = userDetailsService.activateUser(code);

        if (isActivated) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
