package nextFTC.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import nextFTC.CommandGroups.autoRoutines;
import nextFTC.CommandGroups.trajectoryBuilder;
import nextFTC.pedroPathing.constants.FConstants;
import nextFTC.pedroPathing.constants.LConstants;
import nextFTC.subsystems.Claw;
import nextFTC.subsystems.Elbows;
import nextFTC.subsystems.Slides;


@Autonomous(name = "Efcgvhj", group = "Avyugiho")
public class testAuto extends PedroOpMode {

    public testAuto(){
        super(Slides.INSTANCE, Claw.INSTANCE, Elbows.INSTANCE);
    }

    public static Follower follower;
    public static Pose telePose;
    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(trajectoryBuilder.startPose);


        Claw.INSTANCE.close();
        Elbows.INSTANCE.elbowStraight();
    }
    @Override
    public void onUpdate(){
        follower.update();
        telePose = follower.getPose();
    }

    @Override
    public void onStartButtonPressed() {
        trajectoryBuilder.buildPaths();
        CommandManager.INSTANCE.scheduleCommand(
                new SequentialGroup(
                        autoRoutines.scorePreload(),
                        autoRoutines.scoreSample2()
                )
        );
    }




}

