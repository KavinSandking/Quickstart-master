package nextFTC.subsystems;

import androidx.annotation.NonNull;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.Feedforward;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.ResetEncoder;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;


@Config
public class Slides extends Subsystem {

    public static final Slides INSTANCE = new Slides();

    private Slides() {
    }

    public MotorEx leftSlide;
    public MotorEx rightSlide;
    public MotorGroup Slides;


    public static double kf=0;
    public static double kp = 0.007, ki = 0, kd = 0;

    public String name = "Slides";
    public PIDFController controller = new PIDFController(kp, ki, kd, (pos)-> kf, 20);


    public String name2= "Slides2";

    public static double target =0;

    public Command slidesUp() {
        return new RunToPosition(Slides, 3000, controller, this);
    }

    public Command slidesDown() {
        return new RunToPosition(Slides, 0, controller, this);
    }

    public Command slidesSlightlyUp(){
        return new RunToPosition(Slides, 500, controller, this);
    }
    public Command manualControl(float power) {
        return new SetPower(Slides, power, this);
    }
    public Command resetEncoder(){
        return new ResetEncoder(Slides.getLeader(),this);
    }


    public double getCurrentPosition() {
        return Slides.getLeader().getCurrentPosition();
    }


    @Override
    @NonNull
    public Command getDefaultCommand() {
        return new HoldPosition(Slides, controller, this);
    }

    @Override
    public void periodic(){

        controller.setKP(kp);
        controller.setKI(ki);
        controller.setKD(kd);
        controller.setSetPointTolerance(10);
        controller.setTarget(target);
        OpModeData.INSTANCE.telemetry.addData("pos",getCurrentPosition());
        OpModeData.INSTANCE.telemetry.addData("target",target);
        OpModeData.INSTANCE.telemetry.update();
    }



    @Override
    public void initialize() {
        leftSlide = new MotorEx(name);
        rightSlide = new MotorEx(name2);
        Slides = new MotorGroup(rightSlide, leftSlide);




    }
}
