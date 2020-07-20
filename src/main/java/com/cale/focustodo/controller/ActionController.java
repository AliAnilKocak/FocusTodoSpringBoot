package com.cale.focustodo.controller;

import com.cale.focustodo.dto.ActionDto;
import com.cale.focustodo.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class ActionController {

    @Autowired
    private ActionService actionService;


    @PostMapping("actions")
    @ResponseStatus(HttpStatus.CREATED)
    public ActionDto addAction(@RequestBody ActionDto action) {
        return actionService.saveAction(action);
    }

    @GetMapping("actions")
    public List<ActionDto> AllActions() {
        return actionService.getActions();
    }

    @GetMapping("actions/{id}")
    public ActionDto productById(@PathVariable int id) {
        return actionService.getActionById(id);
    }



}
