package com.cale.focustodo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "action")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Action extends BaseEntity {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;

    private String name;
    private String icon;
    private String slug;



}
