package com.cale.focustodo.dto;

import com.cale.focustodo.entity.Action;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private int id;

    @NotNull
    private String title;
    private String description;
    private boolean isFavorite;
    private String time;
    private String energy;
    private boolean isCompleted;
    private Date dueDate;
    private int action_id;
    private Action action;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
