package frc.robot.subsystem;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

    private SparkMax power;
    private SparkMax steer;

    private SlewRateLimiter driveLimiterr;
    private SlewRateLimiter turnLimiter;

    private PIDController pid;

    private DoubleEntry PEntry;
    private DoubleEntry IEntry;
    private DoubleEntry DEntry;
    private DoubleEntry steerPos;
    private DoubleEntry steerGoal;

    public DriveSubsystem() {

        power = new SparkMax(17, MotorType.kBrushless);
        power.configure(new SparkMaxConfig(), ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        steer = new SparkMax(6, MotorType.kBrushless);
        steer.configure(new SparkMaxConfig(), ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        driveLimiterr = new SlewRateLimiter(0.5);
        turnLimiter = new SlewRateLimiter(2);

        NetworkTable table = NetworkTableInstance.getDefault().getTable("Steer PID");
        PEntry = table.getDoubleTopic("P").getEntry(0);
        IEntry = table.getDoubleTopic("I").getEntry(0);
        DEntry = table.getDoubleTopic("D").getEntry(0);

        pid = new PIDController(1, 0, 0);

        PEntry.set(1);
        IEntry.set(0);
        DEntry.set(0);

        steerPos = table.getDoubleTopic("hoodPos").getEntry(0);
        steerGoal = table.getDoubleTopic("steerGoal").getEntry(0);

    }

    public void drive(double power, double turning) {
        power *= 0.5;

        power = driveLimiterr.calculate(power);
        turning = turnLimiter.calculate(turning);
        this.power.set(power);
        steer.set(turning * 0.5);
    }

    public void directionalDrive(Rotation2d direction) {

        pid.setP(PEntry.get());
        pid.setI(IEntry.get());
        pid.setD(DEntry.get());

        steer.set(pid.calculate(steer.getAbsoluteEncoder().getPosition(), direction.getRadians()));

        power.set(Math.pow(Math.E, -Math.pow((steer.getAbsoluteEncoder().getPosition() - direction.getRadians()) / 15, 2)));

    }

    @Override
    public void periodic() {
        steerPos.set(steer.getAbsoluteEncoder().getPosition());
    }
    
}
