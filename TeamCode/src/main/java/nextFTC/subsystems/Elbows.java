package nextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveConditionalCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToSeperatePositions;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.core.subsystems.Subsystem;

import java.util.Map;

import nextFTC.config;

public class Elbows implements Subsystem {

    public static final Elbows INSTANCE = new Elbows();
    private Elbows(){}

    private Servo Elbow,Elbow2;



    public boolean isElbowUp = false;
    public Command elbowUp(){
        return new SequentialGroup(
                new InstantCommand(() -> isElbowUp = true),
                new ServoToPosition(Elbow2, config.elbow2UpPosition, this)
        );
    }
    /*public Command elbowUp(){
        return new SequentialGroup(
                new InstantCommand(() -> isElbowUp = true),
                new MultipleServosToSeperatePositions(
                        Map.of(Elbow, 0.4, Elbow2, 0.6),this)
        );
    }*/
    /*public Command elbowDown(){
        return new SequentialGroup(
                new InstantCommand(() -> isElbowUp = false),
                new MultipleServosToSeperatePositions(
                        Map.of(Elbow, 0.188, Elbow2, 0.878),this)
        );
    }*/
    public Command elbowDown(){
        return new SequentialGroup(
                new InstantCommand(() -> isElbowUp = false),
                new ServoToPosition(Elbow2, config.elbow2DownPosition, this)
        );
    }

    @Override
    public void periodic(){}



    @Override
    public void initialize(){
        Elbow = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "elbow");
        Elbow2 = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "elbow_2");
    }
}

