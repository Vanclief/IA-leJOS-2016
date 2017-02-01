import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.SoundSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.RotateMoveController;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.PilotProps;


public class Sub {
	
	public static void main (String[] aArg) throws Exception {

     	PilotProps pp = new PilotProps();
    	pp.loadPersistentValues();
    	float wheelDiameter = Float.parseFloat(pp.getProperty(PilotProps.KEY_WHEELDIAMETER, "4.96"));
    	float trackWidth = Float.parseFloat(pp.getProperty(PilotProps.KEY_TRACKWIDTH, "13.0"));
    	RegulatedMotor leftMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_LEFTMOTOR, "A"));
    	RegulatedMotor rightMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_RIGHTMOTOR, "B"));
    	boolean reverse = Boolean.parseBoolean(pp.getProperty(PilotProps.KEY_REVERSE,"false"));
    

    	//reverse
		final RotateMoveController pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
		// final LightSensor light = new LightSensor(SensorPort.S1);
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
    	final SoundSensor sound = new SoundSensor(SensorPort.S4);

        pilot.setRotateSpeed(180);
        /**
         * this behavior wants to take control when the light sensor sees the line
         */
		Behavior Drive = new Behavior(){
			public boolean takeControl() {
				return sound.readValue() <= 30;
			}
			
			public void suppress() {
				pilot.stop();
			}
			
			public void action() {
      			LCD.drawString("Status: DRIVE", 0, 0);

				pilot.forward();
                while(sound.readValue() <= 30) 
                	Thread.yield(); //action complete when not on line
			}					
		};
		
		Behavior MoveBack = new Behavior(){
			private boolean suppress = false;
			
			public boolean takeControl() {
				return sound.readValue() > 30;
			}

			public void suppress() {
				suppress = true;
			}
			
			public void action() {
      			LCD.drawString("Status: BACK", 0, 0);

				while (!suppress) {
					pilot.backward();
					while (!suppress && pilot.isMoving()) 
						Thread.yield();
				}
				pilot.stop();
				suppress = false;
			}
		};

		Behavior[] behaviors = {MoveBack, Drive};
        Button.waitForAnyPress();
	    (new Arbitrator(behaviors)).start();
	}
}
