package neu.edu.cs6650.proj_2_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer stepSum;

//  @OneToMany(mappedBy = "user")
//  private Set<StepByDay> steps;

  public User() {
    stepSum = 0;
//    steps = new HashSet<>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getStepSum() {
    return stepSum;
  }

  public void setStepSum(Integer stepSum) {
    this.stepSum = stepSum;
  }

}
