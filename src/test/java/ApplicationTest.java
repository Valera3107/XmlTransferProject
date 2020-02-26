

public class ApplicationTest {

  public static void main(String[] args) {
    System.out.println("Run test...");

    WorkflowTest workflowTest = new WorkflowTest();
    try {
      workflowTest.test();

      System.out.println("Tests successfully completed!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
