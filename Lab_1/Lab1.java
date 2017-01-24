import lejos.nxt.*;

public class Lab1 {

	public static void main(String[] args) throws InterruptedException {

		
		LCD.drawString("A: " + Motor.A.getTachoCount(), 0, 0);
		LCD.drawString("B: " + Motor.B.getTachoCount(), 0, 1);
		//Button.waitForAnyPress();
		
		for(int i = 0; i<4;i++){
			rotateDegrees(270, 0);
			moveForward(2250);
		}
		
		Button.waitForAnyPress();
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
		for (int i = 0; i < timeInMilis; i++) {
			Motor.A.forward();
			Motor.B.forward();
			Thread.sleep(1);
		}
		Motor.A.stop();
		Motor.B.stop();
	}
	
	public void moveBackward() {
		Motor.A.backward();
		Motor.B.backward();
	}
	
	public void rotateLeft() {
		Motor.A.backward();
		Motor.B.forward();
	}
	
	public void rotateRight() {
		Motor.A.forward();
		Motor.B.backward();
	}
	
//	  UltrasonicSensor sonar=new UltrasonicSensor(SensorPort.S1);
//	  sonar.continuous();
//	  for (; ; ) {
//	    float d=sonar.getDistance();
//	    LCD.drawString(String.valueOf(d),0,0);
//	    Sound.playTone((int)d * 100,30,50);
//	    Thread.sleep(100);
//	  }
	
    
}