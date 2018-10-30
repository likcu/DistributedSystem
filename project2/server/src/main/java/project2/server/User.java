package project2.server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userChart")
public class User {
  @Id
  @GeneratedValue
  private Integer id;
  private Integer day;
  private Integer timeInterval;
  private Integer stepCount;

  public User() {
  }

  public Integer getId() {
    return id;
  }

  public Integer getDay() {
    return day;
  }

  public Integer getTimeInterval() {
    return timeInterval;
  }

  public Integer getStepCount() {
    return stepCount;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setDay(Integer day) {
    this.day = day;
  }

  public void setTimeInterval(Integer timeInterval) {
    this.timeInterval = timeInterval;
  }

  public void setStepCount(Integer stepCount) {
    this.stepCount = stepCount;
  }
}
