import lejos.nxt.*;

public class Lab1 {

  public static void main(String[] args) throws InterruptedException {

    // Variables
    UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(
        SensorPort.S1
        );
    TouchSensor leftBump = new TouchSensor(SensorPort.S2);
    TouchSensor rightBump = new TouchSensor(SensorPort.S3);
    SoundSensor sound = new SoundSensor(SensorPort.S4);

    // If we don't crash, stuff happens
    while(!leftBump.isPressed() && !rightBump.isPressed()) {

      int distance = ultrasonicSensor.getDistance();
      int soundValue = sound.readValue();

      LCD.drawString("Status: Ok", 0, 0);
      LCD.drawString(
          "Distance: " + distance, 0, 1
          );
      LCD.drawString(
          "Sound: " + soundValue, 0, 2
          );

      if (soundValue > 90) {
        drawCommandSign();
      }

      if (distance == 255) {
        moveForward(1000);
      } else {
        int random = 0 + (int)(Math.random() * 1);
        moveBackward(2000);
        rotateDegrees(30, random);
      }

    }

    // Our robot is sassy, so if we crash we are done.
    LCD.drawString("Status: Not Ok", 0, 0);
    LCD.drawString("I crashed, so I.", 0, 1);
    LCD.drawString("refuse to move.", 0, 1);

    Button.waitForAnyPress();

  }

  public static void drawCommandSign() throws InterruptedException {

    for(int i = 0; i<4;i++){
      rotateDegrees(270, 0);
      moveForward(2250);
    }

  }

  public static void rotateDegrees(int degrees, int wheel) {
    // Motor A (right): 0
    // Motor B (left):  1
    Double d = degrees * 6.11111111;
    d = Math.ceil(d);
    int wheelDegrees = d.intValue();

    if (wheel == 0) {
      Motor.A.rotate(wheelDegrees);
    } else if (wheel == 1) {
      Motor.B.rotate(wheelDegrees);
    }
  }

  public static void moveForward(int timeInMilis) throws InterruptedException {

    TouchSensor leftBump = new TouchSensor(SensorPort.S2);
    TouchSensor rightBump = new TouchSensor(SensorPort.S3);

    for (int i = 0; i < timeInMilis; i++) {
      if (!leftBump.isPressed() && !rightBump.isPressed()) {
        Motor.A.forward();
        Motor.B.forward();
      } else {
        Motor.A.backward();
        Motor.B.backward();
      }
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

