package com.cale.focustodo.controller;

import com.cale.focustodo.dto.TodoDto;
import com.cale.focustodo.entity.Todo;
import com.cale.focustodo.security.JwtUtilService;
import com.cale.focustodo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class TodoController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private JwtUtilService jwtUtil;

    @PostMapping("todos")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDto addTodo(@RequestHeader("Authorization") String tokenHeader,@RequestBody TodoDto todo) {
        String username = jwtUtil.extractUsernameFromRequest(tokenHeader);
        System.out.println("-----------"+username+"-------------");
        return todoService.saveTodo(todo,username);
    }

    @GetMapping("todos")
    public List<TodoDto> AllTodos(@RequestHeader("Authorization") String tokenHeader) {
        String username = jwtUtil.extractUsernameFromRequest(tokenHeader);
        return todoService.getTodos(username);
    }

    @GetMapping("favoritetodos")
    public List<TodoDto> favoriteTodos(@RequestHeader("Authorization") String tokenHeader) {
        String username = jwtUtil.extractUsernameFromRequest(tokenHeader);
        return todoService.getFavoriteTodos(username);
    }

    @GetMapping("completedtodos")
    public List<TodoDto> completedTodos(@RequestHeader("Authorization") String tokenHeader) {
        String username = jwtUtil.extractUsernameFromRequest(tokenHeader);
        return todoService.getCompleteTodos(username);
    }

    @GetMapping("todos/{id}")
    public TodoDto productById(@PathVariable int id) {
        return todoService.getTodoById(id);
    }

    @GetMapping("todosbyaction/{id}")
    public List<TodoDto> productByIddeneme(@PathVariable int id) {
        return todoService.getTodoByIdDetail(id);
    }

    @PutMapping("todos")
    public TodoDto updateTodo(@RequestBody TodoDto todoDto) {
        System.out.println(todoDto.toString());
        return todoService.updateTodo(todoDto);
    }

    @DeleteMapping("todos/{id}")
    public TodoDto deleteTodo(@PathVariable int id) {
        return todoService.deleteProduct(id);
    }

    @GetMapping("todos_p")
    @ResponseBody
    public List<Todo> findAllPaginated(@RequestParam("pageNumber") int pageNumber,
                                       @RequestParam("size") int size) {
        Page<Todo> resultPage = todoService.getPaginatedCharacters(pageNumber, size);
        return resultPage.getContent();
    }

}
