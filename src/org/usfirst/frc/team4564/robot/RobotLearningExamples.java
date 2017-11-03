package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class RobotLearningExamples extends SampleRobot {
	RobotDrive drive;												//Talking #8
	Spark left;														//Talking #6
	Talon right;													//Talking #6
	
	Joystick stick;													//Talking #14
	Joystick stick2;												//Talking #15
	
	Autonomous auto;
	
	int forwardPolarity = -1;										//Talking #11
	double turnCalibration = 0.03;									//Talking #12
	int turnPolarity = -1;											//Talking #13

	@Override
	public void robotInit() {
		left = new Spark(0);										//Talking #6
		
		right = new Talon(1);										//Talking #6
		right.setInverted(true);									//Talking #7
		
		drive = new RobotDrive(left, right);						//Talking #8
		
		stick = new Joystick(0);									//Talking #14
		stick2 = new Joystick(1);									//Talking #15
		
		auto = new Autonomous();
	}
	
	/**
	 * Runs when "test" mode is selected in the FRC driver station.
	 */
	@Override
	public void test() {
		drive.arcadeDrive(stick);									//Talking #9
		//	versus
		drive.arcadeDrive(1 /*Forward*/, 1 /*Turn*/);				//Talking #10
		
		stick.getRawAxis(0 /*x-axis*/);								//Talking #16
		stick.getRawAxis(1 /*y-axis*/);								//Talking #16
		
		GenericHID.Hand.kLeft;										//Talking #17
		GenericHID.Hand.kRight;										//Talking #17
		
		stick.getTrigger();											//Talking #18
		stick.getTrigger(GenericHID.Hand.kRight/*Xbox only*/);		//Talking #18
		
		stick.getRawButton(0 /*Button ID*/);						//Talking #19
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		while(isEnabled() && isOperatorControl()) {					//Talking #5
			double forward = 0.99*stick.getRawAxis(1); //Gets y axis//Talking #20
			double turn = 0.99*stick.getRawAxis(0); //Gets x axis	//Talking #21
			
			drive.arcadeDrive(forward, turn); //Moves drive train	//Talking #22
			
			Timer.delay(0.02); //Loop ~50 times per second
		}
	}
	
	/**
	 * Runs when "autonomous" mode is selected in the FRC driver station.
	 */
	@Override
	public void autonomous() {
		auto.init();
		auto.start();
		while(isEnabled() && isAutonomous()) {
			auto.update();
		}
	}
}
