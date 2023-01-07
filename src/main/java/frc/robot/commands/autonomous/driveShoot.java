// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveBackAndCalibClimb;
import frc.robot.commands.LiftShoot;
import frc.robot.commands.angle.AutoDown;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class driveShoot extends SequentialCommandGroup {
	/** Creates a new driveShoot. */
	public driveShoot() {
		// Add your commands in the addCommands() call, e.g.
		// addCommands(new FooCommand(), new BarCommand());
		addCommands(new DriveBackAndCalibClimb(), new LiftShoot(), new AutoDown());
	}
}
