package org.razu.controllers;

import java.util.LinkedHashMap;
import java.util.Optional;
import javax.validation.Valid;
import org.razu.entity.User;
import org.razu.services.UserService;
import org.razu.utils.DateFormatProvider;
import org.razu.utils.ErrorUtils;
import org.razu.utils.MessgageUtils;
import org.razu.utils.ResponseTagName;
import static org.razu.utils.UrlUtils.USER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = USER)
public class UserController implements ResponseTagName, MessgageUtils {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping(path = USER_BY_UID)
    ResponseEntity<?> getUserByUId(@RequestParam(name = "id", required = true) Integer id) {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_200);
            serviceResponse.put(MESSAGE, BY_UID);
            serviceResponse.put(USERS, user);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, USER_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @GetMapping(path = USER_BY_UN)
    ResponseEntity<?> getUserByUName(@RequestParam(name = "userName", required = true) String userName) {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        Optional<User> user = userService.findUserByUserName(userName);
        if (user != null) {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_200);
            serviceResponse.put(MESSAGE, BY_UNAME);
            serviceResponse.put(USERS, user);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, USER_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @GetMapping(path = ALL_USER)
    ResponseEntity<?> getAllUser() {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        Iterable<User> userInfoList = userService.findAllUser();
        if (userInfoList.spliterator().getExactSizeIfKnown() > 0) {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_200);
            serviceResponse.put(MESSAGE, USER_AVAIL);
            serviceResponse.put(USERS, userInfoList);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, USER_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PutMapping(path = USER_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> webUserUpdate(@Valid @RequestBody User userInfo, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(ErrorUtils.userError(result), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
            Optional<User> existUser = userService.findUserByUserName(userInfo.getUsername());
            if (existUser != null) {

                existUser.get().setPassword(passwordEncoder.encode(userInfo.getPassword()));
                //existUser.setUpdateBy(userInfo.getUpdateBy());
                // existUser.setUpdateDate(DateFormatProvider.now());
                User editUser = userService.update(existUser.get());

                if (editUser != null) {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_200);
                    serviceResponse.put(MESSAGE, UPDATE_SUCC);
                    serviceResponse.put(USERS, editUser);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                } else {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_201);
                    serviceResponse.put(MESSAGE, UPDATE_FAILED);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                }
            } else {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_201);
                serviceResponse.put(MESSAGE, UN_NOT_AVAIL);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping(path = USER_DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> webUserDelete(@RequestBody User userInfo) {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        Optional<User> existUser = userService.findUserByUserName(userInfo.getUsername());
        if (existUser != null) {
            Boolean deleteUser = userService.delete(existUser.get());
            if (deleteUser) {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_200);
                serviceResponse.put(MESSAGE, DELETE_SUCC);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            } else {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_201);
                serviceResponse.put(MESSAGE, DELETE_FAILED);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            }
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, UN_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }
}
