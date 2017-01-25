import lejos.nxt.*;

public class CommandTest {

  public static void main(String[] args) throws InterruptedException {

    // Our robot is sassy, so if we crash we are done.
    LCD.drawString("TEST COMMAND SIGN", 0, 0);

    for(int i = 0; i<4;i++){
      rotate(270, 0);
      moveForward(2250);
    }

  }


  public static void rotate(int degrees, int wheel) {
    // Motor A (right): 0
    // Motor B (left):  1
    Double d = degrees * 6.11111111;
    d = Math.ceil(d);
    int wheelDegrees = d.intValue();
    
    if (wheel == 0)  Motor.A.rotate(wheelDegrees);
    else if (wheel == 1) Motor.B.rotate(wheelDegrees);

  }

  public static void moveForward(int timeInMilis) throws InterruptedException {
    for (int i = 0; i < timeInMilis; i++) {
        Motor.A.forward();
        Motor.B.forward();
    }
    Motor.A.stop();
    Motor.B.stop();
  }
}
