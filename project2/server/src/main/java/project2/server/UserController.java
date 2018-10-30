package project2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
//  @Autowired
//  private UserRepository users;

  @GetMapping(value = "/hello/{id}")
  public String say(@PathVariable("id") Integer id){
    return "id:" + id;
    //return userProperties.getId();
  }

//  @PostMapping(value = "/hello")
//  public Integer say2(){
//    return userProperties.getStepCount();
//  }
}
