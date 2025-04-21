package nextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveConditionalCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.core.subsystems.Subsystem;

public class Claw implements Subsystem {

    public static final Claw INSTANCE = new Claw();
    private Claw(){}

    public Servo clawServo;
    public String name = "claw";

    public boolean isClawOpen = false;

    public Command open() {
        return new SequentialGroup(
                new ServoToPosition(clawServo, 0.0, this)
        );
    }
    public Command close(){
        return new SequentialGroup(
                new ServoToPosition(clawServo, 1.0, this));
    }

    @Override
    public void periodic(){
        OpModeData.INSTANCE.telemetry.addData("claw", clawServo.getPosition());
        OpModeData.INSTANCE.telemetry.update();
    }



    @Override
    public void initialize(){
        clawServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);

    }
}
