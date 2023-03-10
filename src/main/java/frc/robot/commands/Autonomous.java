// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.automodes;
import frc.robot.commands.autonomous.driveShoot;
import frc.robot.commands.autonomous.fenderhigh;
import frc.robot.commands.autonomous.fenderpreset;
import frc.robot.commands.autonomous.none;
import frc.robot.commands.autonomous.testrot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Autonomous extends SequentialCommandGroup {

	/** Creates a new Autonomous. */
	public Autonomous(automodes a) {
		switch (a) {
			case backupOnly:
				addCommands(new DriveBackAndCalibClimb());
			case backupShoot:
				addCommands(new driveShoot());
			case none:
				addCommands(new none());
			// case left2ball:
			// 	addCommands(new twoBallLeft());
			// case ballfind:
			// 	addCommands(new ballfind());
			case testrot:
				addCommands(new testrot());
			case fender:
				addCommands(new fenderhigh());
			case fenderpreset:
				addCommands(new fenderpreset());
			// case fenderballtrack:
			// 	addCommands(new fenderballtrack());
			default:
				addCommands(new none());
		}
	}
}
