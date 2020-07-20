package com.cale.focustodo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;

    private String title;
    private String description;
    private boolean isFavorite;
    private String time;
    private boolean isCompleted;
    private String energy;
    private Date dueDate;

    @JoinColumn(name = "action_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Action action;

    @JsonIgnore
    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private ApplicationUser user;


}
