package com.cale.focustodo.service;


import com.cale.focustodo.dto.TodoDto;
import com.cale.focustodo.entity.Action;
import com.cale.focustodo.entity.ApplicationUser;
import com.cale.focustodo.entity.Todo;
import com.cale.focustodo.repository.ActionRepository;
import com.cale.focustodo.repository.TodoRepository;
import com.cale.focustodo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TodoService {


    private TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;

    public TodoService(TodoRepository todoRepository, ModelMapper modelMapper, UserRepository userRepository, ActionRepository actionRepository) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.actionRepository = actionRepository;
    }


    public TodoDto saveTodo(TodoDto todoDto,String userName) {

        Todo todoEntity = modelMapper.map(todoDto, Todo.class);

        ApplicationUser currentUser = userRepository.findByUsername(userName);
        Action currentAction = actionRepository.getOne(todoDto.getAction_id());

        todoEntity.setUser(currentUser);
        todoEntity.setAction(currentAction);

        todoEntity = todoRepository.save(todoEntity);
        todoDto.setId(todoEntity.getId());
        return todoDto;
    }


    public List<TodoDto> getFavoriteTodos(String username) {
        ApplicationUser currentUser = userRepository.findByUsername(username);
        List<Todo> data = todoRepository.findTodoByFavorite();
        return Arrays.asList(modelMapper.map(data, TodoDto[].class));
    }

    public List<TodoDto> getCompleteTodos(String username) {
        ApplicationUser currentUser = userRepository.findByUsername(username);
        List<Todo> data = todoRepository.findTodoByCompleted();
        return Arrays.asList(modelMapper.map(data, TodoDto[].class));
    }

    public List<TodoDto> getTodos(String username) {
        ApplicationUser currentUser = userRepository.findByUsername(username);
        List<Todo> data = todoRepository.getByUserId(currentUser.getId());
        return Arrays.asList(modelMapper.map(data, TodoDto[].class));
    }


    public Page<Todo> getPaginatedCharacters(int pageNumber, int size) { //TODO TodoDto
        PageRequest pageable = PageRequest.of(pageNumber - 1, size);
        Page<Todo> resultPage = todoRepository.findAll(pageable);
        if (pageNumber > resultPage.getTotalPages()) {
            throw new IllegalStateException("Not Found Page Number:" + pageNumber);
        }
        return resultPage;
    }

    public TodoDto getTodoById(int todoId) {
        Todo currentTodo = todoRepository.getOne(todoId);
        return modelMapper.map(currentTodo, TodoDto.class);

    }

    public List<TodoDto> getTodoByIdDetail(int actionId) {
        List<Todo> currentTodo = todoRepository.getByAction_Id(actionId);
        return Arrays.asList(modelMapper.map(currentTodo, TodoDto[].class));
    }

    public TodoDto deleteProduct(int todoId) {
        TodoDto todo = getTodoById(todoId);
        todoRepository.deleteById(todoId);
        return todo;
    }

    public TodoDto updateTodo(TodoDto todoDto) {
        System.out.println(todoDto.toString());
        Todo existingTodo = todoRepository.findById(todoDto.getId()).orElse(null);
        existingTodo.setTitle(todoDto.getTitle());
        existingTodo.setDescription(todoDto.getDescription());
        existingTodo.setFavorite(todoDto.isFavorite());
        existingTodo.setTime(todoDto.getTime());
        existingTodo.setEnergy(todoDto.getEnergy());
        existingTodo.setDueDate(todoDto.getDueDate());
        existingTodo.setCompleted(todoDto.isCompleted());
        Todo todoResponse = todoRepository.save(existingTodo);
        return modelMapper.map(todoResponse,TodoDto.class);

    }


}
