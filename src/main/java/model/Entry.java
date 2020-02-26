package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Entry {
  @Id
  private Long field;

  public Entry() {
  }

  public Entry(Long field) {
    this.field = field;
  }

  public Long getField() {
    return field;
  }

  public void setField(Long num) {
    this.field = num;
  }

  @Override
  public String toString() {
    return "Entity{" +
      "num=" + field +
      '}';
  }
}
