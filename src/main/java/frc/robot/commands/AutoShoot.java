// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.status;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterSubSys;

public class AutoShoot extends CommandBase {
	ShooterSubSys shooterSubSys;
	/** Creates a new AutoShoot. */
	public AutoShoot() {
		this.shooterSubSys = RobotContainer.shooterSubSys;
		addRequirements(shooterSubSys);
		// Use addRequirements() here to declare subsystem dependencies.
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		ShooterSubSys.rev();
		status.setString("shooting");
	}

	boolean done = false;
	Timer revtimer = new Timer();
	Timer timer = new Timer();
	double revdelay = 1, shoottime = 0.5;
	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (ShooterSubSys.isAtSpeed() && revtimer.get() == 0){
			revtimer.start();
		} else if (ShooterSubSys.isAtSpeed() && timer.get() == 0 && revtimer.hasElapsed(revdelay)){
			timer.start();
			shooterSubSys.shoot();
		} else if (timer.hasElapsed(shoottime) && revtimer.hasElapsed(revdelay)){
			done = true;
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		shooterSubSys.stop();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return done;
	}
}
