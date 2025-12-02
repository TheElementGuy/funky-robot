// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.command.DriveCommand;
import frc.robot.subsystem.DriveSubsystem;

public class RobotContainer {

	private DriveSubsystem drive;
	private XboxController controller;
	private DriveCommand driveBrain;

	public RobotContainer() {

		drive = new DriveSubsystem();
		controller = new XboxController(0);
		driveBrain = new DriveCommand(drive, controller);

		drive.setDefaultCommand(driveBrain);

		System.out.println("Hello from Funky Robot!");
		configureBindings();
	}

	private void configureBindings() {
	}

	public Command getAutonomousCommand() {
		return Commands.print("No autonomous command configured");
	}
}
