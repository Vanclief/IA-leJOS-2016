import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
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

    final int soundThreshold = 50;

    float wheelDiameter = Float.parseFloat(pp.getProperty(PilotProps.KEY_WHEELDIAMETER, "4.96"));
    float trackWidth = Float.parseFloat(pp.getProperty(PilotProps.KEY_TRACKWIDTH, "13.0"));

    RegulatedMotor leftMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_LEFTMOTOR, "B"));
    RegulatedMotor rightMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_RIGHTMOTOR, "A"));
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

    // DRIVE --------------------------------------------------
    Behavior Drive = new Behavior(){
      public boolean takeControl() {
        return sound.readValue() <= soundThreshold;
      }

      public void suppress() {
        pilot.stop();
      }

      public void action() {
        LCD.drawString("Status: DRIVE", 0, 0);
        LCD.drawString("" + sound.readValue(), 0, 1);

        pilot.forward();
        while(sound.readValue() <= soundThreshold)
          Thread.yield(); //action complete when not on line
      }
    };

    // MOVE BACK --------------------------------------------------
    Behavior MoveBack = new Behavior(){
      private boolean suppress = false;

      public boolean takeControl() {
        return sound.readValue() > soundThreshold;
      }

      public void suppress() {
        suppress = true;
      }

      public void action() {
        LCD.drawString("Status: BACK", 0, 0);
        LCD.drawString("" + sound.readValue(), 0, 1);

        while (!suppress) {
          pilot.backward();
          while (!suppress && pilot.isMoving())
            Thread.yield();
        }
        pilot.stop();
        suppress = false;
      }
    };

    // RUN AWAY FROM SOUND --------------------------------------------------
    Behavior RunFromSound = new Behavior() {

      private boolean supress = false;

      // This behavior should be triggered if the sound value exceds the soundThreshold
      public boolean takeControl() {
        return sound.readValue() > soundThreshold;
      }

      // If this behavior stops, stop moving
      public void supress() {
        suppress = true;
      }

      public void action() {

        LCD.drawString("Status: SCARED", 0, 0);
        LCD.drawString("Im scared of", 1, 0);
        LCD.drawString("sounds. ", 2, 0);

        // While the behavior is still running, go backward and try going any of both sides
        while (!suppress) {

          // Beep since its a little ~bitch~ mousey mousey
          Sound.beepSequence();

          // Go back
          pilot.travel(-5 - rand.nextInt(5));

          // Get a random direction
          int angle = 60 + rand.nextInt(60);
          int side = (Math.random() > 0.5 ? -1 : 1);

          // Rotate
          pilot.rotate(-side * angle);

          while (!suppress && pilot.isMoving())
            Thread.yield();
        }

      }

    };

    Behavior[] behaviors = {MoveBack, Drive, RunFromSound};
    Button.waitForAnyPress();
    (new Arbitrator(behaviors)).start();
  }
}
