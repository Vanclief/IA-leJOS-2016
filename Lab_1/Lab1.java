import lejos.nxt.*;

public class Lab1 {

  public static void main(String[] args) throws InterruptedException {

    moveForward(4000);

  }

  public static void moveForward(int timeInMilis) throws InterruptedException {
    for (int i = 0; i < timeInMilis; i++) {
      Motor.A.forward();
      Motor.B.forward();
      Thread.sleep(1);
    }
  }

  public void moveBackward() {
    Motor.A.backward();
    Motor.B.backward();
  }

  public static void rotateLeft() {
    int rot = 270;
    Motor.A.rotateTo(rot);
    Motor.B.rotateTo(-rot);
  }

  public static void rotateRight() {
    int rot = 270;
    Motor.B.rotateTo(rot);
    Motor.A.rotateTo(-rot);
  }

  //    UltrasonicSensor sonar=new UltrasonicSensor(SensorPort.S1);
  //    //    sonar.continuous();
  //    //    for (; ; ) {
  //    //      float d=sonar.getDistance();
  //    //      LCD.drawString(String.valueOf(d),0,0);
  //    //      Sound.playTone((int)d * 100,30,50);
  //    //      Thread.sleep(100);
  //    //    }
  //
  //
  //          })}
}
