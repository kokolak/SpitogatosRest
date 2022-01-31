package com.spitogatos.controllers;

import com.spitogatos.entities.Properties;
import com.spitogatos.entities.UserCustomer;
import com.spitogatos.implrepositories.ImpUserRepository;
import com.spitogatos.pojo.CustomException;
import com.spitogatos.repositories.PropertiesRepository;
import com.spitogatos.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.xml.ws.BindingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class MainController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ImpUserRepository userRepoImpl;
    @Autowired
    private PropertiesRepository propRepo;

//    @GetMapping("/users")
//    public List<UserCustomer> users(HttpSession session) {
//        if (session.getAttribute("user") == null) {
//            throw new CustomException("not logged in");
//        }
//        return userRepo.findAll();
//    }
//    @GetMapping("/users/{id}")
//    public Optional<UserCustomer> user(@PathVariable Integer id, HttpSession session) {
//        if (session.getAttribute("user") == null) {
//            throw new CustomException("not logged in");
//        }
//        return userRepo.findById(id);
//    }
    @RequestMapping("/login/user")
    public UserCustomer loginUser(@RequestBody UserCustomer user, HttpServletRequest request) {
        UserCustomer u = userRepoImpl.userByUserNameAndPassword(user.getUsername(), user.getPassword());
        HttpSession ses = request.getSession(true);
        ses.setAttribute("user", u);
        return u;
    }

    @PostMapping("/register/user")
    public UserCustomer registerUser(@RequestBody UserCustomer user) {
        UserCustomer u = userRepo.findByUsername(user.getUsername());
        if (u != null) {
            throw new CustomException("A user already exists");
        }
        return userRepo.save(user);
    }

//    @PostMapping("/delete/user/{id}")
//    public void deleteUser(@PathVariable Integer id, HttpSession session) {
//        if (session.getAttribute("user") == null) {
//            throw new CustomException("not logged in");
//        }
//        userRepo.deleteByUserId(id);
//    }
//    @PostMapping("/update/user")
//    public UserCustomer updateUser(@RequestBody UserCustomer user, HttpSession session) {
//        UserCustomer u = (UserCustomer) session.getAttribute("user");
//        if (u == null) {
//            throw new CustomException("not logged in");
//        }
//        System.out.println("AAA " + session.getAttribute("user"));
//        u.setUsername(user.getUsername());
//        u.setPassword(user.getPassword());
//        return userRepo.save(u);
//    }
    //properties
//    @GetMapping("/properties")
//    public List<Properties> properties(HttpSession session) {
//        if (session.getAttribute("user") == null) {
//            throw new CustomException("not logged in");
//        }
//        return propRepo.findAll();
//    }
    @PostMapping("/user/properties")
    public List<Properties> userProperties(@RequestBody UserCustomer u) {
        UserCustomer user = userRepo.findByUsernameAndPassword(u.getUsername(), u.getPassword());
        if (user == null) {
            throw new CustomException("must login");
        }
        return propRepo.findByUserId(u);
    }

    @PostMapping("/create/property")
    public Properties createProperties(@RequestBody Properties property) {
        UserCustomer user = userRepo.findByUsernameAndPassword(property.getUserId().getUsername(), property.getUserId().getPassword());
        if (user == null) {
            throw new CustomException("must login");
        }
        if (property.getPrice() < 50 || property.getPrice() > 5000000) {
            throw new CustomException("not a valid price");
        }
        if (property.getArea() < 20 || property.getArea() > 1000) {
            throw new CustomException("not a valid area");
        }
        if (!("Ηράκλειο").equals(property.getRegion()) && !("Αθήνα").equals(property.getRegion()) && !("Θεσσαλονίκη").equals(property.getRegion()) && !("Πάτρα").equals(property.getRegion())) {
            throw new CustomException("not a valid region");
        }
        if (property.getAvailability() != 1 && property.getAvailability() != 0) {
            throw new CustomException("not a valid availability");
        }
        return propRepo.save(property);
    }

    @PostMapping("/delete/property/{id}")
    public void deleteProperty(@PathVariable Integer id) {
        propRepo.deleteByPropertyId(id);
    }

    @GetMapping("/property/{id}")
    public Optional<Properties> getProperty(@PathVariable Integer id) {
        return propRepo.findById(id);
    }

    @PostMapping("/update/property")
    public void updateProperty(@RequestBody Properties property) {
        UserCustomer user = userRepo.findByUsernameAndPassword(property.getUserId().getUsername(), property.getUserId().getPassword());
        if (user == null) {
            throw new CustomException("must login");
        }
        if (property.getPrice() < 50 || property.getPrice() > 5000000) {
            throw new CustomException("not a valid price");
        }
        if (property.getArea() < 20 || property.getArea() > 1000) {
            throw new CustomException("not a valid area");
        }
        if (!("Ηράκλειο").equals(property.getRegion()) && !("Αθήνα").equals(property.getRegion()) && !("Θεσσαλονίκη").equals(property.getRegion()) && !("Πάτρα").equals(property.getRegion())) {
            throw new CustomException("not a valid region");
        }
        if (property.getAvailability() != 1 && property.getAvailability() != 0) {
            throw new CustomException("not a valid availability");
        }
        propRepo.save(property);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.removeAttribute("user");
    }
}
