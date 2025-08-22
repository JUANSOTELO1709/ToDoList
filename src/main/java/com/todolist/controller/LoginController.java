package com.todolist.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // ← IMPORT AÑADIDO
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todolist.model.User;
import com.todolist.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    // Muestra el formulario para registrar un nuevo usuario
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // Procesa el registro de un nuevo usuario y lo guarda en la base de datos
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
            @RequestParam String password,
            Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        model.addAttribute("msg", "Usuario registrado correctamente");
        return "login";
    }

    // Crea el usuario 'juan' con contraseña '0608' (para pruebas rápidas)
    @GetMapping("/create-user")
    public String createUser(Model model) {
        if (!userRepository.existsByUsername("juan")) {
            User user = new User();
            user.setUsername("juan");
            user.setPassword("0608");
            userRepository.save(user);
            model.addAttribute("msg", "Usuario 'juan' creado correctamente.");
        } else {
            model.addAttribute("msg", "El usuario 'juan' ya existe.");
        }
        return "login";
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

    Optional<User> userOptional = userRepository.login(username, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            session.setAttribute("user", user);  // ← ¡Esto es crucial!
            return "redirect:/tasks";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }

    //LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruye la sesión
        return "redirect:/login";
    }
}