package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;

public class DriveTrain extends RobotDrive {
	private static DriveTrain instance;
	private static Spark leftFront = new Spark(Constants.PWM_DRIVE_FL), rightFront = new Spark(Constants.PWM_DRIVE_FR), 
			leftBack = new Spark(Constants.PWM_DRIVE_BL), rightBack = new Spark(Constants.PWM_DRIVE_BR);
	private Encoder encoder;
	
	public DriveTrain() {
		super(leftFront, leftBack, rightFront, rightBack);
		instance = this;
		
		encoder = new Encoder(Constants.DIO_DRIVE_ENCODER_A, Constants.DIO_DRIVE_ENCODER_B, false, EncodingType.k1X);
		encoder.setDistancePerPulse(109.5/17688.0);
	}
	
	public double getDistance() {
		return encoder.getDistance();
	}
	
	public static DriveTrain instance() {
		return instance;
	}
}