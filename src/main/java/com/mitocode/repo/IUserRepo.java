package com.mitocode.repo;

import com.mitocode.model.User;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepo extends IGenericRepo<User, Integer>  {

    //@Query("FROM User u WHERE u.username = ?")
    //Derived Query
    User findOneByUsername(String username);
}
