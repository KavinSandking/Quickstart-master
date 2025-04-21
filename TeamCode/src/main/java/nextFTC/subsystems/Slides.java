package nextFTC.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;

import com.rowanmcalpin.nextftc.core.subsystems.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.*;

@Config
public class Slides implements Subsystem {

    public static final Slides INSTANCE = new Slides();

    private Slides() {
    }

    private MotorEx leftSlides = new MotorEx("Slides")
            .zeroed()
            .reversed();
    private MotorEx rightSlides = new MotorEx("Slides2");
    private MotorGroup slides = new MotorGroup(leftSlides,rightSlides);

    @Override
    public void periodic(){
        slides.setPower(
                controlSystem.calculate(
                        new KineticState(
                                motor.getCurrentPosition(),
                                motor.getVelocity()
                        )
                )
        );


    }
}
