package com.cale.focustodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionDto {
    private int id;
    private String name;
    private String icon;
    private String slug;

}
