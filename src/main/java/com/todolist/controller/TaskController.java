package com.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todolist.model.Task;
import com.todolist.model.TaskStatus;
import com.todolist.model.User;
import com.todolist.service.TaskService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private com.todolist.repository.UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String showTasksPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println(" User en sesión: " + (user != null ? user.getUsername() : "NULL"));

        if (user == null) {
            System.out.println("X No hay usuario en sesión - Redirigiendo a login");
            return "redirect:/login";
        }

    // Recarga el usuario desde la base de datos para asegurar datos actualizados en la sesión
        User freshUser = userRepository.findById(user.getId()).orElse(null);
        if (freshUser == null) {
            System.out.println("X Usuario no existe en BD - Cerrando sesión");
            session.invalidate();
            return "redirect:/login";
        }

    System.out.println("Usuario válido: " + freshUser.getUsername());
    List<Task> tasks = taskService.getUserTasks(freshUser);
    model.addAttribute("user", freshUser);
    model.addAttribute("tasks", tasks);
    model.addAttribute("newTask", new Task());
    return "tasks";
    }

    @PostMapping("/create")
    public String createTask(@RequestParam String title,
            @RequestParam String description,
            @RequestParam String dueDate,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        try {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
            task.setDueDate(format.parse(dueDate));
        } catch (Exception e) {
            task.setDueDate(null);
        }
        task.setStatus(TaskStatus.PENDIENTE);
        task.setUser(user);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTaskStatus(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        taskService.toggleTaskStatus(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}