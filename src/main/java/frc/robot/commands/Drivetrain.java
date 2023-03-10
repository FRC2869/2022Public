// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubSys;

public class Drivetrain extends CommandBase {
	
	private final DrivetrainSubSys drive;

	/** Creates a new Drivetrain. */
	public Drivetrain(DrivetrainSubSys drive) {
		this.drive = drive;
		addRequirements(drive);
	}
	
	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		drive.initSpeedList();
		DrivetrainSubSys.resetEncoders();
		// drive.initOdometry();
		drive.forceInitOdom();

	}
	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		drive.drive();
		// System.out.println(drive.getPose());
		// drive.autoDrive();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		drive.stop();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
