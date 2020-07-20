package com.cale.focustodo.repository;

import com.cale.focustodo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Page<Todo> findAll(Pageable pageable);

    List<Todo> getByAction_Id(int id);

    @Query(value = "SELECT * FROM todo WHERE is_favorite = 1", nativeQuery = true)
    List<Todo> findTodoByFavorite();

    @Query(value = "SELECT * FROM todo WHERE is_completed = 1", nativeQuery = true)
    List<Todo> findTodoByCompleted();

    List<Todo> getByUserId(int id);

}
