package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Num {
  @Id
  private Long num;

  public Num() {
  }

  public Num(Long num) {
    this.num = num;
  }

  public Long getNum() {
    return num;
  }

  public void setNum(Long num) {
    this.num = num;
  }

  @Override
  public String toString() {
    return "Entity{" +
      "num=" + num +
      '}';
  }
}
