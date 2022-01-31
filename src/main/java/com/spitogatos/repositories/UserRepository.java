package com.spitogatos.repositories;

import com.spitogatos.entities.UserCustomer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserCustomer,Integer>{

    Optional<UserCustomer> findById(Integer id);

    UserCustomer  findByUsernameAndPassword(String username,String password);
    
    UserCustomer  findByUsername(String username);

    void deleteByUserId(Integer userid);
    
}
