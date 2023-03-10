// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubSys;
import static frc.robot.Constants.*;

public class RotateNoEncoder extends CommandBase {

	private final DrivetrainSubSys drive;
	// private final double distance;

	private int numCycles;
	private int cycleCount = 0;
	boolean done = false;
	double turnValue;
	/**
	 * Creates the Rotate Command
	 * 
	 * @param goRight If set to true then machine rotates right.
	 * @param angle angles
	 */
	public RotateNoEncoder(boolean goRight, double angle) {
		this.drive = RobotContainer.drivetrainSubSys;
		// this.distance = distance;
		angle = (5*angle)/3;
		this.numCycles = (int)angle;
		addRequirements(drive);
		if (goRight) {
			turnValue = autoturnspeed;
		} else {
			turnValue = -autoturnspeed;
		}
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		// do we need toinitialize anything?
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (cycleCount < numCycles) {
			if (cycleCount < 5) {
				DrivetrainSubSys.drv(0, turnValue * cycleCount / 5);
			} else if (cycleCount > numCycles - 5) {
				DrivetrainSubSys.drv(0, turnValue * (numCycles - cycleCount) / 5);
			} else {
				DrivetrainSubSys.drv(0, turnValue);
			}
			cycleCount++;
		} else {
			done = true;
		}


	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drive.stop();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return done;
	}
}
