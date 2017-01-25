import lejos.nxt.*;

public class SensorTest {

  public static void main(String[] args) throws InterruptedException {

    // Variables
    TouchSensor leftBump = new TouchSensor(SensorPort.S2);
    TouchSensor rightBump = new TouchSensor(SensorPort.S3);

    // If we don't crash
    while(!leftBump.isPressed() && !rightBump.isPressed()) {

      LCD.drawString("Status: Ok", 0, 0);
      moveForward(1000);

    }

    moveBackward(2000);
    LCD.drawString("Status: Not Ok", 0, 0);

    Button.waitForAnyPress();

  }

  public static void moveForward(int timeInMilis) throws InterruptedException {

    for (int i = 0; i < timeInMilis; i++) {
        Motor.A.forward();
        Motor.B.forward();
      Thread.sleep(1);
    }
    Motor.A.stop();
    Motor.B.stop();
  }

  public static void moveBackward(int timeInMilis) throws InterruptedException {
    for (int i = 0; i < timeInMilis; i++) {
      Motor.A.backward();
      Motor.B.backward();
      Thread.sleep(1);
    }
    Motor.A.stop();
    Motor.B.stop();
  }
}

