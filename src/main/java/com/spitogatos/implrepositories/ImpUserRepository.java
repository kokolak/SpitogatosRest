package com.spitogatos.implrepositories;

import com.spitogatos.entities.UserCustomer;
import com.spitogatos.repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImpUserRepository {
  @Autowired
    private UserRepository userRepo;
    
  public UserCustomer userByUserNameAndPassword(String username,String password){
      return userRepo.findByUsernameAndPassword(username, password);
  }
  
}
