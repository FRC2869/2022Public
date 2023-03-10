/*
Pre match checklist
Falcon shafts collars tight
angler left right
check bearings dont fall into water cut plate when resting down
check barstock bend
missing churro on right side
grease chain
shuffleboard values ok

*/

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/*
IGNORE THIS: LOOK In Inputs.java for the real one
Driver
rt forward
lt backwards
l stick x 
rb boost
y align
x ramp
b shoot (x and b need to be held together to be shot)
r stick manual control / low goal
when hub is fully in view then low goal can be shot at full power
dpad up down pid climber up down
dpad left blue ball track
dpad right red ball track

Operator
a high goal
x intake
y low goal
b lower arm
rt up angle
lt down angle
lb eject
rb force in
dpad up down move climber
*/

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
	public static final XboxController xbox = new XboxController(0);
	public static final XboxController opxbox = new XboxController(1);
	public static final Joystick xboxjoystick = new Joystick(0);
	public static final Joystick opxboxjoystick = new Joystick(1);
	// public static final JoystickButton autoDriveButton = new JoystickButton(xboxjoystick, 3); // X
	// public static final JoystickButton spinDriveButton = new JoystickButton(xboxjoystick, 4); // Y
	// public static final JoystickButton resetDriveButton = new JoystickButton(xboxjoystick, 2); // B
	// public static final JoystickButton stopDriveButton = new JoystickButton(xboxjoystick, 1); // A
	// public static final JoystickButton driveDriveButton = new JoystickButton(xboxjoystick, 5); // LB
	// public static final JoystickButton spinDriveButton = new JoystickButton(xboxjoystick, 5); // lb
	// public static final JoystickButton closeHigh = new JoystickButton(opxboxjoystick, XboxController.Button.kLeftBumper.value);
	public static final JoystickButton pointDriveButton = new JoystickButton(opxboxjoystick, XboxController.Button.kLeftBumper.value);
	public static final AnalogInput ultra = new AnalogInput(2);
	public static double targetAngle = 0;
	public static final double armlim = 0.6;
	public static double base = -32, highGoalAngle = 70, lowGoalAngle = 55, target = base, limit = 80, closeAngle = 80; // angles
	public static final double closeVel = 5900;
	public static final double turnspeed = 0.7;
	public static final double slowspeed = 0.45;
	public static final double midspeed = 0.8;
	public static final double manClimbSpeed = 0.1;
	public static final double autoturnspeed = 0.4;

	public static enum shooterState{
		intake, mid, fender, high
	}

	public static NetworkTableEntry distanceEntry;
	// public static NetworkTableEntry autorotateEntry;
	public static NetworkTableEntry ballinEntry;
	public static NetworkTableEntry revedEntry;
	public static NetworkTableEntry velconstantEntry;
	public static NetworkTableEntry launchvelconstantEntry;
	public static NetworkTableEntry slider775;
	public static NetworkTableEntry angleEntry;
	public static NetworkTableEntry angleEntryT;
	public static NetworkTableEntry angleEntryA;
	public static NetworkTableEntry leftFalEntry;
	public static NetworkTableEntry rightFalEntry;
	public static NetworkTableEntry tarSpeedEntry;
	public static NetworkTableEntry batVoltageEntry;
	public static NetworkTableEntry batVoltageEntryT;
	public static NetworkTableEntry batVoltageEntryA;
	public static NetworkTableEntry time;
	public static NetworkTableEntry timeT;
	public static NetworkTableEntry timeA;
	public static NetworkTableEntry tarspeedthing;
	public static NetworkTableEntry status;
	public static NetworkTableEntry statusT;
	public static NetworkTableEntry statusA;

	public static NetworkTableEntry ptrack;
	public static NetworkTableEntry angletolerance;
	public static double angletol = 2;
	public static NetworkTableEntry closeEntry;
	public static NetworkTableEntry closeAngleEntry;
	public static NetworkTableEntry powerRevEntry;
	public static NetworkTableEntry lenc;
	public static NetworkTableEntry renc;
	public static double powerRev = 1;

	public static double[] distancedriven = new double[2];

	public static double autodistance = 1.85;


	public static double encToDist(double e){
		return e * 0.1524 * Math.PI / 10;
	}

	public static double distToEnc(double d){
		return (d*10)/(0.1524 * Math.PI);
	}

	public enum automodes{
		backupOnly, backupShoot, left2ball, none, ballfind, testrot, fender, fenderpreset, fenderballtrack
	}
	
	// public static SendableChooser<Command> autopicker = new SendableChooser<Command>();
	
	public static SendableChooser<automodes> newautopick = new SendableChooser<automodes>();
	public static NetworkTableEntry autoPickerEntry;
	public static Command auto;

	// public static final PhotonCamera photon = new PhotonCamera("photonvision");

	// @Deprecated
	// public static void initPhoton(){
	// 	// photonResetPipe();
	// }

	// @Deprecated
	// public static void photonBlue(){
	// 	// photon.setPipelineIndex(1);
	// }
	// @Deprecated
	// public static void photonRed(){
	// 	// photon.setPipelineIndex(0);
	// }
	// @Deprecated
	// public static void photonResetPipe(){
	// 	// if (DriverStation.getAlliance() == Alliance.Blue){
	// 	// 	photonBlue();
	// 	// } else if (DriverStation.getAlliance() == Alliance.Red){
	// 	// 	photonRed();
	// 	// } else {
	// 	// 	photon.setPipelineIndex(2);
	// 	// }
	// }

	// /**
	//  * 
	//  * @return best target or null if not found
	//  */
	// @Deprecated
	// static PhotonTrackedTarget pTarget(){
	// 	// PhotonPipelineResult result = photon.getLatestResult();
	// 	// if(result.hasTargets()){
	// 	// 	for (int i = 0; i < result.targets.size(); i++){
	// 	// 		if (result.targets.get(i).getPitch() > -15){
	// 	// 			return result.targets.get(i);
	// 	// 		}
	// 	// 	}
	// 	// 	// return result.getBestTarget();
	// 	// }

	// 	return null;
	// }

	// /**
	//  * 
	//  * @returns yaw of best ball or Double.POSITIVE_INFINITY if none found
	//  */
	// @Deprecated
	// public static double pTrack(){
	// 	// return Double.POSITIVE_INFINITY;

	// 	// onFlash();
	// 	// PhotonTrackedTarget target = pTarget();
	// 	// if (target != null){
	// 	// 	return target.getYaw();
	// 	// }

	// 	return Double.POSITIVE_INFINITY;
	// }

	public static void autoSchedule(Command comm){
		if (!comm.isScheduled()){
			comm.schedule();
		}
	}

	// @Deprecated
	// public static double distance(){
	// 	// return 18*12.0/39.37;
	// 	return LimelightSubSys.getDistance();
	// }
	public static void setAngle(double angle) {
		targetAngle = angle;
	}

	public static double getTarAngle() {
		return targetAngle;
	}

	// @Deprecated
	// public static double getAngle() {
	// 	return AngleSubSys.getAngle();
	// }

	// @Deprecated
	// public static double ang2Enc(double theta){
	// 	return AngleSubSys.ang2Enc(theta);
	// }
 
	// @Deprecated
	// public static double getUltra() {
	// 	// SmartDashboard.putNumber("ultrasonic", dist);
	// 	// double dist = (ultra.getVoltage()*1000)/9.77;
	// 	// return dist;

	// 	return 10; // if no ultrasonic
	// }

	public static double clamp(double inp, double min, double max) {
		if (inp > max) {
			inp = max;
		}
		if (inp < min) {
			inp = min;
		}
		return inp;
	}

	// private static final WPI_TalonSRX flashlight = new WPI_TalonSRX(20);
	// private static boolean initFlash = false;

	// @Deprecated
	// public static void initFlash() {
	// 	flashlight.configContinuousCurrentLimit(1);
	// 	flashlight.configPeakCurrentLimit(1);
	// 	flashlight.enableCurrentLimit(true);
	// 	initFlash=true;

	// 	// System.out.println("INIT");
	// }

	// @Deprecated
	// public static void onFlash() {
	// 	// System.out.println("ON");
	// 	if (!initFlash) {
	// 		initFlash();
	// 		// System.out.println("ON FAIL");
	// 	}

	// 	// System.out.println("ON SUCCESS");
	// 	// flashlight.set((5.0/RobotController.getBatteryVoltage()));
	// 	// flashlight.set(0.4);
	// 	// flashlight.set(1);
	// }

	// @Deprecated
	// public static void offFlash() {
	// 	// System.out.println("OFF");
	// 	if (!initFlash) {
	// 		initFlash();
	// 		// System.out.println("OFF FAIL");
	// 	}
	// 	flashlight.set(0);
	// 	// System.out.println("OFF SUCCESS");
	// }

	// @Deprecated
	// public static double getFlash() {
	// 	return flashlight.get();
	// 	// return 0;
	// }

	// public static final PowerDistribution pdp = new PowerDistribution();

	// @Deprecated
	// public static double getFlashCurrent() {
	// 	// return flashlight.getOutputCurrent();
	// 	return flashlight.getSupplyCurrent();
	// 	// return pdp.getCurrent(9);
	// 	// return 0;
	// }

	
	// static NetworkTable table;
	// static NetworkTableEntry tx;
	// static NetworkTableEntry ty;
	// static NetworkTableEntry ta;
	// static boolean networkInit = false;
	// @Deprecated
	// public static void networkInit(){
	// 	// if (!networkInit){
	// 	if (true){
	// 		try{
	// 			// NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	// 			// NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
	// 			NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(2);
	// 			NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
				
	// 			// NetworkTableEntry tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
	// 			// NetworkTableEntry ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
	// 			// NetworkTableEntry ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta");
	// 			networkInit = true;
	// 		}catch (Exception e){
	// 			System.out.println("Network init fail");
	// 		}
	// 	}
	// }

	// @Deprecated
	// public static void onLime(){
	// 	// NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
	// 	offLime();
	// }
	// @Deprecated
	// public static void offLime(){
	// 	NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
	// }


	//BELOW HERE is copied from falcon ex code
	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon FX supports multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 30;

	/**
	 * PID Gains may have to be adjusted based on the responsiveness of control
	 * loop.
	 * kF: 1023 represents output value to Talon at 100%, 20660 represents Velocity
	 * units at 100% output
	 * 
	 * kP kI kD kF Iz PeakOut
	 */
	public final static Gains kGains_Velocit = new Gains(0.1, 0.001, 10, 1023.0 / 20660.0, 300, 1.00);
}
