package com.sda.app.controller;

import com.sda.app.dto.LoginDto;
import com.sda.app.dto.RegisterDto;
import com.sda.app.entity.User;
import com.sda.app.entity.UserRole;
import com.sda.app.service.UserService;
import com.sda.app.utils.ApiResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users") //adresa unde gasim metodele pentru user
public class UserController {
    @Autowired
    private UserService userService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllUser() {
        List<User> userList = this.userService.findAll();
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Lista utilizatori generata")
                .data(userList)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        User usr = new User();
        usr.setUsername(user.getUsername());
        usr.setEmail(user.getEmail());
        usr.setUserRole(user.getUserRole());
        usr.setPassword(this.encryptPassword(user.getPassword()));

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Utilizator creat cu success")
                .data(userService.createUser(usr))
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user, @PathVariable("id") Integer id) {
        User usr = new User();
        usr.setId(id);
        usr.setUsername(user.getUsername());
        usr.setEmail(user.getEmail());
        usr.setUserRole(user.getUserRole());
        usr.setPassword(this.encryptPassword(user.getPassword()));

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Utilizator actualizat cu success")
                .data(userService.updateUser(user))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Utilizator sters cu success")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginDto login) {
        Optional<User> optionalUser = this.userService.findByEmail(login.getEmail());

        if (optionalUser.isPresent()) {
            User usr = optionalUser.get();

            if (checkPassword(login.getPassword(), usr.getPassword())) {
                ApiResponse response = new ApiResponse.Builder()
                        .status(200)
                        .message("Utilizator logat cu success")
                        .data(usr)
                        .build();
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse.Builder()
                        .status(200)
                        .message("Parola Invalida")
                        .data(null)
                        .build();
                return ResponseEntity.ok(response);
            }

        } else {
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Utilizatorul nu exista")
                    .data(null)
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterDto register) {
        User usr = new User();
        usr.setUsername(register.getUsername());
        usr.setEmail(register.getEmail());
        usr.setUserRole(UserRole.USER);
        usr.setPassword(this.encryptPassword(register.getPassword()));

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Registered")
                .data(this.userService.createUser(usr))
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * This method it is used to encrypt the password before save it in database
     *
     * @param password - clear password
     * @return - encrypted password
     */
    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String password, String bdPassword) {
        return BCrypt.checkpw(password, bdPassword);
    }
}
