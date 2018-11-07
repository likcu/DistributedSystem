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
  private Integer chartId;

  private Integer userId;
  private Integer day;
  private Integer timeInterval;
  private Integer stepCount;

  public User() {
  }

  public Integer getChartId() {
    return chartId;
  }

  public void setChartId(Integer chartId) {
    this.chartId = chartId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
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
