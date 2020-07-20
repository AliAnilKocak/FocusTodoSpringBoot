package com.cale.focustodo.repository;

import com.cale.focustodo.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action,Integer> {
    List<Action> findAll();
}
