package com.sena.mysqlwithjpa;

import com.sena.mysqlwithjpa.entity.User;
import com.sena.mysqlwithjpa.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/demo")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 1. CREATE: Endpoint para guardar un usuario validando con @Valid.
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * 2. READ: Endpoint para listar todos los usuarios.
     */
    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 3. READ BY ID: Endpoint para obtener un usuario específico por su ID.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario con ID " + id + " no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 4. UPDATE: Endpoint para actualizar los datos de un usuario existente.
     */
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody User userDetails) {

        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User existingUser = userOptional.get();
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setAge(userDetails.getAge());
        existingUser.setPhone(userDetails.getPhone());

        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * 5. DELETE: Endpoint para eliminar un usuario por su ID.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        if (!userRepository.existsById(id)) {
            response.put("deleted", false);
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        userRepository.deleteById(id);
        response.put("deleted", true);
        response.put("message", "Usuario con ID " + id + " eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint tradicional con parámetros de consulta/formulario.
     */
    @PostMapping("/add")
    public String addNewUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "18") Integer age,
            @RequestParam(required = false, defaultValue = "3001234567") String phone) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        user.setPhone(phone);

        userRepository.save(user);

        return "Saved";
    }
}
