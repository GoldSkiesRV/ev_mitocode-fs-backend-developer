package com.mitocode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuDTO {

    @EqualsAndHashCode.Include
    private Integer idMenu;

    private String icon;
    private String name;
    private String url;
    private List<RolDTO> roles;
}
