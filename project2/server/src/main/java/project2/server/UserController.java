package project2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping(value = "/users")
  public List<User> userList(){
    return userRepository.findAll();
  }
  @PostMapping(value = "{userId}/{day}/{timeInterval}/{stepCount}")
  public void userAdd(@PathVariable("userId") Integer userId,
                                @PathVariable("day") Integer day,
                                @PathVariable("timeInterval") Integer timeInterval,
                                @PathVariable("stepCount") Integer stepCount){
    User user = new User();
    user.setUserId(userId);
    user.setDay(day);
    user.setTimeInterval(timeInterval);
    user.setStepCount(stepCount);
    return;

  }

  @GetMapping(value = "current/{id}")
  public Integer getCurrentSteps(@PathVariable("id") Integer id){
    List<User> userList = userRepository.findAllByUserId(id);
    Integer total = 0;
    for(User user: userList){
      total += user.getStepCount();
    }
    return total;
    //return userRepository.findAllByUserId(id);
  }

  @GetMapping(value = "single/{id}/{day}")
  public Integer getSingleDaySteps(@PathVariable("id") Integer id,@PathVariable("day") Integer day){
    List<User> userList = userRepository.findAllByUserIdAndDay(id,day);
    Integer total = 0;
    for(User user: userList){
      total += user.getStepCount();
    }
    return total;
    //return userRepository.findAllByUserId(id);
    //return 1;
  }
}
