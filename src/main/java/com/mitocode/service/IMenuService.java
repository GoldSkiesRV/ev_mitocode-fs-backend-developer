package com.mitocode.service;

import com.mitocode.model.Menu;

import java.util.List;

public interface IMenuService extends ICRUD<Menu, Integer> {

    List<Menu> getMenusByUsername(String username);

}
