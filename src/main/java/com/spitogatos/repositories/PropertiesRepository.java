package com.spitogatos.repositories;

import com.spitogatos.entities.Properties;
import com.spitogatos.entities.UserCustomer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertiesRepository extends JpaRepository<Properties,Integer>{

     void deleteByPropertyId(Integer userid);
     
     List<Properties> findByUserId(UserCustomer u);
}
