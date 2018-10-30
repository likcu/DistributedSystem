package neu.edu.cs6650.proj_2_server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserController {

  @Autowired
  private UserRepository users;

  @Autowired
  private StepByDayRepository stepByDayRepository;

  @PostMapping(value = "/create/{userNum}")
  public void createUsers(@PathVariable("userNum") Integer userNum) {
    for(int i = 0; i < userNum; i++) {
      User user = new User();
      users.save(user);
    }
  }

  @PostMapping(value = "{id}/{day}/{timeInterval}/{stepCount}")
  public void postStep(@PathVariable("id") Integer id, @PathVariable("day") Integer day,
                       @PathVariable("stepCount") Integer step) {
    User user = users.getOne(id);
    user.setStepSum(step + user.getStepSum());
    StepByDay stepByDay = stepByDayRepository.findByName(id + "-" + day);
    if(stepByDay == null)
      stepByDay = new StepByDay(step, day, id + "-" + day);
    else
      stepByDay.setStep(stepByDay.getStep() + step);
    stepByDayRepository.save(stepByDay);
    users.save(user);
  }

  @GetMapping(value = "/current/{id}")
  public Integer getCurStep(@PathVariable("id") Integer id) {
    return users.getOne(id).getStepSum();
  }

  @GetMapping(value = "/single/{id}/{day}")
  public Integer getStepByDay(@PathVariable("id") Integer id, @PathVariable("day") Integer day) {
    StepByDay stepByDay = stepByDayRepository.findByName(id + "-" + day);
    if(stepByDay == null)
      return 0;
    return stepByDay.getStep();
  }

  @GetMapping(value = "range/{id}/{startDay}/{numDays}")
  public Integer getByRange(@PathVariable("id") Integer id, @PathVariable("startDay") Integer startDay,
                            @PathVariable("numDays") Integer numDays) {
    return null;
  }
}
