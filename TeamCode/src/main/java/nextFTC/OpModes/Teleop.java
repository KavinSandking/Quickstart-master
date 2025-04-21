package nextFTC.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveConditionalCommand;

import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.DifferentialTankDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;


import nextFTC.subsystems.Claw;
import nextFTC.subsystems.Elbows;
import nextFTC.subsystems.Slides;


@TeleOp(name = "testTele")
public class Teleop extends NextFTCOpMode {
    public Teleop() {
        super(Elbows.INSTANCE, Claw.INSTANCE, Slides.INSTANCE);
    }

    public String leftFrontName = "leftFront";
    public String leftBackName = "leftBack";
    public String rightFrontName = "rightFront";
    public String rightBackName = "rightBack";

    public MotorEx frontLeft;
    public MotorEx backLeft;
    public MotorEx frontRight;
    public MotorEx backRight;
    public MotorGroup leftMotors;
    public MotorGroup rightMotors;

    public Command driverControlled;
    double Delay = 0.5;
    boolean isClawOpen = false;
    boolean isElbowUp = false;


    @Override
    public void onInit() {


        frontLeft = new MotorEx(leftFrontName);
        backLeft = new MotorEx(leftBackName);
        frontRight = new MotorEx(rightFrontName);
        backRight = new MotorEx(rightBackName);


        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotors = new MotorGroup(frontLeft, backLeft);
        rightMotors = new MotorGroup(frontRight, backRight);




    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new DifferentialTankDriverControlled(leftMotors, rightMotors, gamepadManager.getGamepad1());
        driverControlled.invoke();
        gamepadManager.getGamepad2().getB().setPressedCommand(() -> {
            return new SequentialGroup(
                    Elbows.INSTANCE.elbowDown(),
                    Claw.INSTANCE.close()
            );
        });
        gamepadManager.getGamepad2().getA().setPressedCommand(() -> {
            return new SequentialGroup(
                    new InstantCommand(() -> {
                        isElbowUp = !isElbowUp;
                    }),
                    new PassiveConditionalCommand(
                            () -> isElbowUp,
                            Elbows.INSTANCE::elbowDown,
                            Elbows.INSTANCE::elbowUp
                    )
            );
        });
        git init
        gamepadManager.getGamepad2().getX().setPressedCommand(
                () ->{
                    return new SequentialGroup(
                            new InstantCommand(() -> {
                                isClawOpen = !isClawOpen;
                            }),
                            new PassiveConditionalCommand(
                                    () -> isClawOpen,
                                    Claw.INSTANCE::close,
                                    Claw.INSTANCE::open
                                    )
                    );
                }
        );
        gamepadManager.getGamepad1().getX().setPressedCommand(Slides.INSTANCE::slidesUp);
    }



}
