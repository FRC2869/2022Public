// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Inputs.*;

import java.util.LinkedList;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
// TODO implement these
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// import org.opencv.core.Mat;
// import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


// not necessary but if something breaks uncomment below
// import frc.robot.commands.Shoot;
// import frc.robot.subsystems.ShootSubSys;
// import frc.robot.subsystems.IntakeSubSys;

public class DrivetrainSubSys extends SubsystemBase {
	private static final CANSparkMax right1 = new CANSparkMax(1, MotorType.kBrushless);
	private static final CANSparkMax right2 = new CANSparkMax(2, MotorType.kBrushless);
	private static final CANSparkMax left1 = new CANSparkMax(3, MotorType.kBrushless);
	private static final CANSparkMax left2 = new CANSparkMax(4, MotorType.kBrushless);
	public static final MotorControllerGroup right = new MotorControllerGroup(right1, right2);
	public static final MotorControllerGroup left = new MotorControllerGroup(left1, left2);
	private static final DifferentialDrive difDrive = new DifferentialDrive(left, right);
	private static final AHRS navx = new AHRS(SPI.Port.kMXP);

	public DrivetrainSubSys() {
		difDrive.setSafetyEnabled(false);

		final int defenseCurrentLimit = 50;
		// Added for defense by StyPulse:
		right1.setSmartCurrentLimit(defenseCurrentLimit);
		right2.setSmartCurrentLimit(defenseCurrentLimit);
		left1.setSmartCurrentLimit(defenseCurrentLimit);
		left2.setSmartCurrentLimit(defenseCurrentLimit);
	}


	public void brake(){
		System.out.println("brake");
		right1.setIdleMode(IdleMode.kBrake);
		right2.setIdleMode(IdleMode.kBrake);
		left1.setIdleMode(IdleMode.kBrake);
		left2.setIdleMode(IdleMode.kBrake);
	}

	public void coast(){
		System.out.println("coast");
		right1.setIdleMode(IdleMode.kCoast);
		right2.setIdleMode(IdleMode.kCoast);
		left1.setIdleMode(IdleMode.kCoast);
		left2.setIdleMode(IdleMode.kCoast);
	}

	public static void drv(double speed, double turn){
		difDrive.arcadeDrive(turn, speed);
	}

	public void autoInit(){
		left1.getEncoder().setPosition(0);
		left2.getEncoder().setPosition(0);
		right1.getEncoder().setPosition(0);
		right2.getEncoder().setPosition(0);
	}

	public double getEncDistance(){
		// return left1.getEncoder().getPosition();
		double encoders[] = {left1.getEncoder().getPosition(), left2.getEncoder().getPosition(), -right1.getEncoder().getPosition(), -right2.getEncoder().getPosition()};
		return average(encoders);
	}

	public static double autospeed = 0.5;

	public void auto(){
		// SmartDashboard.putNumber("test", getEncDistance());
		drv(-autospeed, 0);
	}

	static PIDController pidturn = new PIDController(0.8, 0.5, 0.07);
	// PhotonCamera photoncam = new PhotonCamera("photonvision");
	
	void autoTurn(double d){
		drv(0,clamp(pidturn.calculate(d)/10,-autoTurnSpeed,autoTurnSpeed));
	}


	double autoTurnSpeed = 0.4;	
	void autoTurn(double d, double speed){
		drv(speed,clamp(pidturn.calculate(d)/10,-autoTurnSpeed,autoTurnSpeed));
	}

	public double[] getenc(){
		double[] thing = {left1.getEncoder().getPosition(), right1.getEncoder().getPosition()};
		return thing;
	}

	// @Deprecated
	// public void limeTurn(){
	// 	// drv(0,clamp(pidturn.calculate(-LimelightSubSys.getLimeX())/10,-0.5,0.5));
	// 	autoTurn(-LimelightSubSys.getLimeX());
	// }

	public void resetTurn(){
		pidturn.reset();
	}

	boolean isBallTurn = false; //checks if first time running ballturndrive and resets pid
	/**
	 * turns towards ball
	 * */	
	// @Deprecated
	// public void ballTurn(){
	// 	if (!isBallTurn){
	// 		isBallTurn = true;
	// 		pidturn.reset();
	// 	}
	// 	double track = -pTrack();
	// 	if (track != Double.POSITIVE_INFINITY){
	// 		autoTurn(track);
	// 	}
	// }
	// /**
	//  * 
	//  */
	// @Deprecated
	// public void ballTurnDrive(){
	// 	if (!isBallTurn){
	// 		isBallTurn = true;
	// 		pidturn.reset();
	// 	}
	// 	double track = -pTrack();
	// 	if (track != Double.NEGATIVE_INFINITY){
	// 		autoTurn(track,0.5);
	// 		// drv(0,track/10);
	// 		// System.out.println(track);
	// 	}
	// }

	// @Deprecated
	// public boolean isBallPointed(){
	// 	return (pTrack() != Double.POSITIVE_INFINITY && Math.abs(pTrack()) < 2);
	// 	// return false;
	// }

	LinkedList<Double> speedlist;

	public void initSpeedList(){
		System.out.println("init list");
		speedlist = new LinkedList<>();
		for (int i = 0; i < 30; i++){
			speedlist.add(0.0);
		}
	}

	void updateSpeedList(double s){
		speedlist.removeFirst();
		speedlist.add(s);
	}

	double avglastspeeds(){
		double avg = 0;
		for (int i = 0; i < speedlist.size(); i++){
			avg += speedlist.get(i);
		}
		avg /= speedlist.size();
		return avg;
	}

	public void drive() {
		double speed = getDriveSpeed();
		updateSpeedList(speed);
		if (Math.abs(speed) < 0.25){
			speed = avglastspeeds();
			SmartDashboard.putBoolean("smoothing", true);
		} else {
			SmartDashboard.putBoolean("smoothing", false);
		}
		if (getSlowSpeed()){
			speed *= slowspeed;
		} else if (getMidSpeed()){
			speed *= midspeed;
		} else {
			// leave speed at full power
		}
		drv(speed, getTurn()*turnspeed);
		
		// if (xbox.getYButtonPressed()){
		// 	resetTurn();
		// }
		// if (xbox.getYButton()){
		// 	// System.out.println(limeturn.calculate(-LimelightSubSys.getLimeX()));
		// 	limeTurn();
		// }

		// if (xbox.getPOV() == 270){
		// 	photonBlue();
		// 	ballTurn();
		// } else if (xbox.getPOV() == 90){
		// 	photonRed();
		// 	ballTurn();
		// } else {
		// 	offFlash();
		// 	isBallTurn = false;
		// }

		// drv(0,0);

		// if (xbox.getLeftBumperPressed()){
		// 	limeturn.reset();
		// }
		// if (xbox.getLeftBumper()){
		// 	if (photoncam.getLatestResult().hasTargets()){
		// 		double dist = photoncam.getLatestResult().getBestTarget().getYaw();
		// 		drv(0,clamp(limeturn.calculate(dist/1),-0.5,0.5));
		// 	}
		// }
		// System.out.println(LimelightSubSys.getX());
		// SmartDashboard.putNumber("x", LimelightSubSys.getLimeX());

	}

	// @Deprecated
	// public static void limeturn(){
	// 	drv(0,clamp(pidturn.calculate(-LimelightSubSys.getLimeX())/10,-0.5,0.5));
	// }
	
	// @Deprecated
	// public void point(){
	// 	drv(0,LimelightSubSys.getLimeX()/20);
	// }

	// @Deprecated
	// public boolean isLimePointed(){
	// 	return (LimelightSubSys.getLimeX()/20 < 0.1);
	// }

	boolean start = true;
	public static double target = 0;

	// @Deprecated
	// public void autoDrive(){
	// 	autodrv();
	// 	// if (xbox.getXButton() && !start){
	// 	// 	autodrv();
	// 	// } else {
	// 	// 	drive();
	// 	// }
		
	// 	// if (xbox.getYButton()){
	// 	// 	resetEncoders();
	// 	// 	start = false;
	// 	// 	target = getUltra();
	// 	// }
	// }

	// @Deprecated
	// public void autodrv(){
	// 	// double speed = xbox.getRightTriggerAxis() - xbox.getLeftTriggerAxis();
	// 	// speed *= 0.4;

	// 	// double pos = getUltra();
	// 	// double turn = xbox.getLeftX()*0.5;
	// 	// double kp = 0.3;
	// 	// double speed = kp * (pos - target);
	// 	// double maxspeed = 0.3;
	// 	// speed = clamp(speed, -maxspeed, maxspeed);
	// 	// drv(speed, turn);
	// }
	
	double pos;
	double spinTarget;
	public boolean spun = false;
	Timer spintime = new Timer();
	double kp=0.4,ki=0.0,kd=0.1;
	PIDController spinner = new PIDController(kp,ki,kd);
	
	public void setRot(double angle){
		spun = false;
		navx.reset();
		pos = navx.getYaw();
		spinTarget = pos+angle;
		spintime.reset();
		spintime.start();
		spinner.reset();
		// kp = SmartDashboard.getNumber("kp", kp);
		// ki = SmartDashboard.getNumber("ki", ki);
		// kd = SmartDashboard.getNumber("kd", kd);
		spinner.setPID(kp, ki, kd);
		spinner.setTolerance(5);
		// spinner.enableContinuousInput(-180, 180);
	}
	
	public void setRot0(){
		spun = false;
		// ahrs.reset(); // TODO check if this is necessary
		pos = navx.getYaw();
		spinTarget = 0;
		spintime.reset();
		spintime.start();
		spinner.reset();
		// kp = SmartDashboard.getNumber("kp", kp);
		// ki = SmartDashboard.getNumber("ki", ki);
		// kd = SmartDashboard.getNumber("kd", kd);
		spinner.setPID(kp, ki, kd);
		spinner.setTolerance(2);
		// spinner.enableContinuousInput(-180, 180);
	}

	double[] errors = new double[10];
	int count = 0;

	public void resetPID(){
		resetTurn();
		// for (int i = 0; i < errors.length; i++){
		// 	errors[i] = 999;
		// }
	}

	public double average(double[] arr){
		// double avg = 0;
		// for (int i = 0; i < arr.length; i++){
		// 	avg += Math.abs(arr[i]/arr.length);
		// }
		// return avg;
		double sum = 0;
		for (int i = 0; i < arr.length; i++){
			sum += arr[i];
		}
		return sum/arr.length;
	}

	public void spin(){
		double livepos = navx.getYaw();
		if (livepos<0){
			livepos += 360;
		}
		// double kp = 0.3;
		// double turn = kp * (spinTarget-livepos);
		double maxspeed = 0.6;
		double turn;
		double error = spinTarget-livepos;
		// errors[count] = error;
		// count++;
		// if (count >= errors.length){
		// 	count = 0;
		// }
		turn = spinner.calculate(-error);
		turn = clamp(turn, -maxspeed, maxspeed);
		if (navx.isConnected()){
			drv(0, turn);
		} else{
			spun=true;
		}
		// SmartDashboard.putString("livepos", livepos+"");
		// SmartDashboard.putString("pos", pos+"");
		// SmartDashboard.putString("spintarget", spinTarget+"");
		// SmartDashboard.putString("error", error+"");
		// System.out.println(ahrs.getYaw());
		// if (spintime.hasElapsed(1)){
		// if (Math.abs(error) < 1 || spintime.hasElapsed(2)){
		if (spinner.atSetpoint() || spintime.hasElapsed(10)){
			spun = true;
			System.out.println("fin");
		}
	}
	
	public void stop() {
		stopDrive();
	}

	public static void stopDrive() {
		drv(0, 0);
	}

	public static void resetEncoders(){
		left1.getEncoder().setPosition(0);
		left2.getEncoder().setPosition(0);
		right1.getEncoder().setPosition(0);
		right2.getEncoder().setPosition(0);
		left1.getEncoder().setPositionConversionFactor(1);
		left2.getEncoder().setPositionConversionFactor(1);
		right1.getEncoder().setPositionConversionFactor(1);
		right2.getEncoder().setPositionConversionFactor(1);
		navx.reset();
		target = 10.0;
		// System.out.println("RESET");
	}

	public static void ahrsReset(){
		navx.reset();
	}
	
	@Override
	public void periodic() {
		// This method will be called once per scheduler run
		initOdometry();
		updateOdom();
	}

	DifferentialDriveOdometry odometry;

	Pose2d pose;

	public double getGyroHeading(){
		return navx.getAngle();
	}

	boolean odominit = false;
	public void initOdometry(){
		if (!odominit){
			// Rotation2d rotation2d = new Rotation2d(getGyroHeading());
			forceInitOdom();
		}
	}

	public void forceInitOdom(){
		// Rotation2d rotation2d = Rotation2d.fromDegrees(getGyroHeading());
		// odometry = new DifferentialDriveOdometry(rotation2d);
		odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));
	}

	double factor = ((6.0*Math.PI/39.0)/10.71) * (36.0/39.0) * (40.75/39.0);

	public void updateOdom(){
		// Encoder leftenc = (Encoder) left1.getEncoder();
		// Encoder rightenc = (Encoder) right1.getEncoder();
		// System.out.println(getGyroHeading());
		// Rotation2d angle = Rotation2d.fromDegrees((getGyroHeading()/2.0) * (90.0/133.0) * (90.0/120.0) * (90.0/112.0) * (90.0/109.0));
		Rotation2d angle = Rotation2d.fromDegrees(-getGyroHeading()*2);
		// Rotation2d angle = navx.getRotation2d();
		double leftdis = left1.getEncoder().getPosition()*factor;
		double rightdis = -right1.getEncoder().getPosition()*factor;
		pose = odometry.update(angle, leftdis, rightdis);
		// pose = odometry.update(angle, 0,0);
		// System.out.println(getGyroHeading());
		// System.out.println(angle);
		// System.out.println(pose);
		// System.out.println(leftdis + "\t" + rightdis);
		// System.out.println(left1.getEncoder().getPosition() + "\t" + -right1.getEncoder().getPosition());
		// pose = odometry.update(gyroAngle, leftenc.getDistance(), rightenc.getDistance());
	}

	public Pose2d getPose(){
		return pose;
	}

	double posD;
	double driveTarget;
	public boolean drove = false;
	Timer drivetime = new Timer();
	double kpd=0.4,kid=0.0,kdd=0.1;
	PIDController driver = new PIDController(kp,ki,kd);
	
	public void setDrivePID(double distance){
		drove = false;
		resetEncoders();
		posD = getEncDistance();
		driveTarget = posD+distance;
		drivetime.reset();
		drivetime.start();
		driver.reset();
		kpd = SmartDashboard.getNumber("kp", kp);
		kid = SmartDashboard.getNumber("ki", ki);
		kdd = SmartDashboard.getNumber("kd", kd);
		driver.setPID(kpd, kid, kdd);
		driver.setTolerance(5);
	}
	
	double[] errorsD = new double[10];
	int countD = 0;
	public void drivePID(){
		double livepos = left1.getEncoder().getPosition();
		double maxspeed = 0.6;
		double speed;
		double error = spinTarget-livepos;
		speed = driver.calculate(-error);
		speed = clamp(speed, -maxspeed, maxspeed);
		drv(speed,0);
		if (driver.atSetpoint() || spintime.hasElapsed(10)){
			drove = true;
			System.out.println("fin");
		}
	}

}
