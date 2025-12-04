package frc.robot.command;

import org.opencv.core.Mat;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystem.DriveSubsystem;

public class DriveCommand extends Command {
    
    private XboxController controller;
    private DriveSubsystem drive;

    public DriveCommand(DriveSubsystem drive, XboxController controller) {
        this.controller = controller;
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.directionalDrive(Rotation2d.fromRadians(Math.atan2(-controller.getLeftY(), -controller.getLeftX())));;
    }

}
