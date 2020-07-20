package com.cale.focustodo.service;


import com.cale.focustodo.dto.ActionDto;
import com.cale.focustodo.entity.Action;
import com.cale.focustodo.repository.ActionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ActionService {


    @Autowired
    private ActionRepository actionRepository;
    private final ModelMapper modelMapper;

    public ActionService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ActionDto saveAction(ActionDto actionDto){
        Action actionEntity = modelMapper.map(actionDto, Action.class);
        actionEntity =  actionRepository.save(actionEntity);
        actionDto.setId(actionEntity.getId());
        return actionDto;
    }

    public List<ActionDto> getActions(){
        List<Action> data = actionRepository.findAll();
        return Arrays.asList(modelMapper.map(data, ActionDto[].class));
    }

    public ActionDto getActionById(int actionId){


        Action action =  actionRepository.findById(actionId).orElse(null);
        return modelMapper.map(action, ActionDto.class);



    }






}
