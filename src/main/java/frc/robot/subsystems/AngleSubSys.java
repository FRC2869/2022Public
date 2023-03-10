// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// 0 = -34
// 47 = 0

package frc.robot.subsystems;

import static frc.robot.Constants.angleEntry;
import static frc.robot.Constants.angletol;
import static frc.robot.Constants.angletolerance;
import static frc.robot.Constants.armlim;
import static frc.robot.Constants.base;
import static frc.robot.Constants.clamp;
import static frc.robot.Constants.closeAngle;
import static frc.robot.Constants.highGoalAngle;
import static frc.robot.Constants.limit;
import static frc.robot.Constants.lowGoalAngle;
import static frc.robot.Constants.opxbox;
import static frc.robot.Constants.target;
import static frc.robot.Constants.xbox;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AngleSubSys extends SubsystemBase {
	/** Creates a new AngleSubSys. */
	public AngleSubSys() {
		//TODO: Check if this limit is good
		arm.setSmartCurrentLimit(25, 30);
	}
	
	static final CANSparkMax arm = new CANSparkMax(5, MotorType.kBrushless);
	
	static final double ratio = 5 * 4 * 3 * 4;
	static double kp = 0.04, ki = 0.000, kd = 0.006, tolerance = 1;
	// TODO: Increase this tolerance value to 5 and see if we still score
	// accurately.

	static PIDController armPID = new PIDController(kp, ki, kd);
	// set target to real target - 10
	static boolean past = false;
	static boolean init = false;

	public void stop() {
		arm.set(0);
	}

	/**
	 * Allows other systems to adjust the tolerance & control accuracy of angle.
	 * When the angle reaches a value within `t` of the desired angle, it should
	 * stop.
	 * 
	 * @param t is measured in degrees.
	 */
	public void setPIDtolerance(double t) {
		tolerance = t;
		angletol = t;
	}

	/**
	 * Resets the arm's PID
	 */
	public static void resetPID() {
		armPID.reset();
		armPID.setPID(kp, ki, kd);
		armPID.setTolerance(tolerance);
	}

	/**
	 * On startup reset the arm's position and set the encoder to 0
	 * Can be called many times, only happens once
	 * to call again set AngleSubSys.init to false
	 */
	public static void init() {
		if (!init) {
			arm.getEncoder().setPosition(0);
			resetPID();
			init = true;
		}
	}

	/**
	 * sets the tolerance on the PID to be the Shuffleboard Value
	 */
	public void setPIDtolerance() {
		setPIDtolerance(angletolerance.getDouble(1));
	}

	/**
	 * Sets the target for the angle to AngleSubSys.max for high goal
	 */
	public void setTargetHigh() {
		target = highGoalAngle;
		setPIDtolerance();
	}

	/**
	 * Sets the target for the angle to AngleSubSys.closeAngle
	 */
	public void setTargetHighClose() {
		target = closeAngle;
		setPIDtolerance();
	}

	/**
	 * Sets the target for the angle to AngleSubSys.mid for low goal
	 */
	public void setTargetMid() {
		target = lowGoalAngle;
		setPIDtolerance();
	}

	/**
	 * Sets the target for the angle to AngleSubSys.base to put it all the way
	 * down
	 */
	public void setTargetLow() {
		target = base;
		setPIDtolerance();
	}

	/**
	 * Calls the setTarget_ functions based on the operator xbox buttons pressed
	 */
	public void setTarget() {
		if (opxbox.getAButton()) {
			setTargetHigh();
		} else if (opxbox.getYButton()) {
			setTargetMid();
		} else if (opxbox.getBButton()) {
			setTargetLow();
		}
	}

	/**
	 * sets angle target
	 * @param angle angle
	 */
	public void setTarget(double angle){
		target = angle;
	}

	/**
	 * Moves the arm based on if in manual control or calls pidMove to move to
	 * target
	 */
	public void run() {
		if (xbox.getStartButtonReleased()) {
			arm.getEncoder().setPosition(0);
		}
		// If in Manual Control
		if (opxbox.getStartButton()) {
			arm.set(armlim * (opxbox.getRightTriggerAxis() - opxbox.getLeftTriggerAxis()));
			armPID.reset();
			target = getAngle();
		}
		else {
			if (getAngle() > limit) {
				// arm.set(0);
				target = lowGoalAngle;
			} else if (getAngle() < -36) {
				// arm.set(0);
				target = base;
			} else {
				// if (!opxbox.getAButton() && !opxbox.getYButton() && !opxbox.getBButton()) {
				// arm.set(armlim * (opxbox.getRightTriggerAxis() -
				// opxbox.getLeftTriggerAxis()));
				// // arm.set(armlim * (xbox.getRightTriggerAxis() -
				// xbox.getLeftTriggerAxis()));
				// armPID.reset();
				// } else {
				// }
				setTarget();
				pidmove();
			}
		}
		// Put the current angle in ShuffleBoard
		angleEntry.setNumber(getAngle());
	}

	/**
	 * Moves to AngleSubSys.target and clamps the power to AngleSubSys.armlim
	 */
	public void pidmove() {
		double error = target - getAngle();
		double power = armPID.calculate(-error);
		power = clamp(power, -armlim, armlim);
		// System.out.println(power);
		arm.set(power);
	}

	/**
	 * 
	 * why is there a local variable here?
	 */
	// public void lift() {
	// 	double target = highGoalAngle;
	// 	pidmove();
	// 	// setTargetHigh();
	// 	// pidmove();
	// }

	// /**
	//  * 
	//  * why is there a local variable here?
	//  */
	// public void descend() {
	// 	double target = base;
	// 	pidmove();
	// 	// setTargetLow();
	// 	// pidmove();
	// }


	double liftTolerance = 2;

	/**
	 * Checks if the arm is at the right angle
	 * 
	 * @return true if its within liftTolerance of target
	 */
	public boolean isLifted() {
		return (Math.abs(target - getAngle()) < liftTolerance);
	}

	/**
	 * Gets the angle given the bottom is -34 deg
	 * 
	 * @return the angle
	 */
	public static double getAngle() {
		return arm.getEncoder().getPosition() * 0.72340426 - 34;
		// return 60;
	}

	/**
	 * Gets the raw encoder value
	 * 
	 * @return the encoder of the arm
	 */
	public static double getEncAngle() {
		return arm.getEncoder().getPosition();
	}

	/**
	 * Changes an angle to an encoder
	 * 
	 * @param theta the angle
	 * @return the encoder value
	 */
	public static double ang2Enc(double theta) {
		return (theta + 34) / 0.72340426;
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
