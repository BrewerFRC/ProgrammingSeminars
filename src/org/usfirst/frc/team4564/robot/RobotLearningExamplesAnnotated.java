package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class RobotLearningExamplesAnnotated extends SampleRobot {
	//An object representing the drive base of the robot.
	RobotDrive drive;
	//Motor controllers for the drive base motors.
	Spark left;
	Talon right;
	
	//Dual joysticks/xbox controllers
	Joystick stick;
	Joystick stick2;
	
	//Setting negative will flip forward and backward in the drive base.
	int forwardPolarity = -1;
	//A turn value that will make the robot drive straight if there are differences in the efficiency of one side.
	double turnCalibration = 0.03;
	//Setting negative will flip left and right in the drive base.
	int turnPolarity = -1;

	@Override
	public void robotInit() {
		left = new Spark(0);
		
		right = new Talon(1);
		//Invert this motor only.  The leads may have been reversed or the motor mounted differently than the left.
		right.setInverted(true);
		
		//Initilize the drive base using the two motors you just set up.
		drive = new RobotDrive(left, right);
		
		stick = new Joystick(0);
		stick2 = new Joystick(1);
	}
	
	/**
	 * Runs when "test" mode is selected in the FRC driver station.
	 * This code is meant merely to give examples and will not compile.
	 */
	@Override
	public void test() {
		//A bad method for controlling the drive base, doesn't offer much custom control.
		drive.arcadeDrive(stick);
		
		//A good method, you can determine forward and turn values yourself from joystick input.
		//Use values between -1 and 1 for both to determine direction and magnitude.
		drive.arcadeDrive(1 /*Forward*/, 1 /*Turn*/);
		
		/**
		 * Get the raw axis values on a joystick or the left xbox stick.  Values between -1 and 1, 0 is center.
		 * Other axis IDs can be found on the controller tab of the driver station software.
		 */
		stick.getRawAxis(0 /*x-axis*/);
		stick.getRawAxis(1 /*y-axis*/);
		
		//Only for xbox controllers.  Use for triggers, bumpers, and sticks.
		GenericHID.Hand.kLeft;
		GenericHID.Hand.kRight;
		
		//Gets the trigger on a normal joystick.
		stick.getTrigger();
		//Gets the trigger on the specified side of an xbox controller.
		stick.getTrigger(GenericHID.Hand.kRight/*Xbox only*/);
		//Example use:
		if (stick.getTrigger(GenericHID.Hand.kRight)) {
			//Fire shooter
		}
		//Triggers can also be used as 0 to 1 axis values.
		
		/**
		 * Gets whether or not the button is pressed as a true/false value.
		 * Button IDs are available on the controller tab of the driver station software.
		 */
		stick.getRawButton(0 /*Button ID*/);
		//Example use:
		if (stick.getRawButton(0)) {
			//Run motor
		}
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		//Run continuously while the robot is enabled.
		while(isEnabled() && isOperatorControl()) {
			double forward = 0.99*stick.getRawAxis(1); //Forward = multipler * y-axis
			double turn = 0.99*stick.getRawAxis(0); //Turn = multiplier * x-axis
			
			drive.arcadeDrive(forward, turn); //Moves drive train with specified forward and turn values.
			
			Timer.delay(0.02); //Loop ~50 times per second
		}
	}
	
	/**
	 * Runs when "autonomous" mode is selected in the FRC driver station.
	 */
	@Override
	public void autonomous() {
		
	}
}
