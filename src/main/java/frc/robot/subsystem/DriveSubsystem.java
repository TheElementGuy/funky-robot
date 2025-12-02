package frc.robot.subsystem;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

    private SparkMax power;
    private SparkMax steer;

    public DriveSubsystem() {

        power = new SparkMax(17, MotorType.kBrushless);
        power.configure(new SparkMaxConfig(), ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        steer = new SparkMax(6, MotorType.kBrushless);
        steer.configure(new SparkMaxConfig(), ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    }

    public void drive(double power, double turning) {
        this.power.set(power);
        steer.set(turning * 0.5);
    }
    
}
