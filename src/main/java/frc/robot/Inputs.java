package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/*
 * Inputs:
 * Driver(xbox)
 *  Right/Left Trigger - move forward/backward
 *  Left/Right Bumper - Slow/Mid Speed
 *  Left Joystick - Turn
 * 
 * 	X Button - Intake
 *  B Button - Auto Shoot Fender
 *  A Button - Auto Shoot Tarmac
 * 
 * 	Right Stick - Manual Shooter
 * 
 *  D-pad up/down - manual climbing 
 * 
 * Operator(opxbox)
 *  Y Button - Angle for High Goal at Tarmac Distance
 *  B Button - Angle for Low Goal
 *  A Button - Angle Down
 *  
 * 
 * 	X button - Intake
 * 
 * 	Left Bumper - Auto Shoot Fender
 *  Right Bumper - Auto Shoot Tarmac 
 * 
 *  D-pad up/down - climbing
 *  
 *  Start + D-pad up/down - climb override Be Careful, will NOT auto stop
 * 
 * 	Back + Y/A - Shooter Adjustment
 */


/**
 * used to get inputs (ie getIntake() or getShoot())
 */
public class Inputs {
	private static final XboxController xbox = new XboxController(0);
	private static final XboxController opxbox = new XboxController(1);
	

	/*
	 * Driver Controls
	 */

	//Shooter
	
	public static double getManualShooterSpeed(){
		double speed = xbox.getRightY();
		if (Math.abs(speed) < 0.1){
			speed = 0;
		}
		return speed;
	}

	//Drive
	public static double getDriveSpeed(){
		double speed = xbox.getRightTriggerAxis() - xbox.getLeftTriggerAxis();
		if (Math.abs(speed) < 0.1){
			speed = 0;
		}
		return speed;
	}
	public static boolean getSlowSpeed(){
		return xbox.getRightBumper();
	}
	public static boolean getMidSpeed(){
		return xbox.getLeftBumper();
	}
	public static double getTurn(){
		return xbox.getLeftX();
	}
	

	/*
	 * Shared Controls
	 */
	public static boolean getFenderPressed(){
		return opxbox.getLeftBumperPressed() || xbox.getBButton();
	}

	public static boolean getTarmacPressed(){
		return opxbox.getRightBumperPressed() || xbox.getAButton();
	}

	public static boolean getIntake(){
		return xbox.getXButton() || opxbox.getXButton();
	}

	public static boolean climbUp(){
		return xbox.getPOV() == 0 || opxbox.getPOV() == 0;
	}
	public static boolean climbDown(){
		return xbox.getPOV() == 180 || opxbox.getPOV() == 180;
	}

	/*
	 * Operator Controls
	 */

	//Angle
	public static boolean getHigh(){
		return opxbox.getBackButtonPressed() && opxbox.getYButton();
	}
	public static boolean getLow(){
		return opxbox.getBackButtonPressed() && opxbox.getAButton();
	}
	public static boolean getMid(){
		return opxbox.getBButton();
	}

	public static boolean getManualClimb(){
		return opxbox.getStartButtonPressed();
	}

	public static boolean getShooterAdjUp(){
		return opxbox.getBackButtonPressed() && opxbox.getYButtonPressed();
	}
	public static boolean getShooterAdjDown(){
		return opxbox.getBackButtonPressed() && opxbox.getAButtonPressed();
	}
	
	
	/*
	 * Deprecated Functions (Ignore these)
	 */

	// @Deprecated
	// public static boolean getFlashButton(){
	// 	return opxbox.getRightStickButton();
	// }

	// @Deprecated
	// public static boolean getFlashButtonPressed(){
	// 	return opxbox.getRightStickButtonPressed();
	// }

	// @Deprecated
	// public static boolean getStartBallTrack(){
	// 	return xbox.getPOV() == 90;
	// }

	// @Deprecated
	// public static boolean getCancelBallTrack(){
	// 	return xbox.getPOV() == 270;
	// }

	// @Deprecated
	// public static boolean getFender(){
	// 	// return opxbox.getPOV() == 270;
	
	// 	return false;
	// }

	// @Deprecated
	// public static boolean getLaunchpad(){
	// 	// return opxbox.getPOV() == 90;
	// 	return false;
	// }

	// @Deprecated
	// public static boolean getOpOverride(){
	// 	return opxbox.getStartButton();
	// }

	// @Deprecated
	// public static boolean getSaveOverride(){
	// 	return xbox.getStartButtonReleased();
	// }

	// @Deprecated
	// public static double getArmPow(){
	// 	return opxbox.getRightTriggerAxis() - opxbox.getLeftTriggerAxis();
	// }

	// @Deprecated
	// public static boolean getEject(){
	// 	// return opxbox.getLeftBumper();
	// 	return false;
	// }

	// @Deprecated
	// public static boolean getForceIn(){
	// 	// return opxbox.getRightBumper();
	// 	return false;
	// }

	// @Deprecated
	// public static boolean getRev(){
	// 	return false;
	// 	// return xbox.getXButton();
	// }

	// @Deprecated
	// public static boolean getShoot(){
	// 	// return xbox.getBButton();
	// 	return false;
	// }
}
