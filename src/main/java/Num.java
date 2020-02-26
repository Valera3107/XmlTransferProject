import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Num {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long num;

  public Num() {
  }

  public Num(Long num) {
    this.num = num;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
