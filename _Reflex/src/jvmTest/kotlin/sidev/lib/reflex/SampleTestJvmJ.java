package sidev.lib.reflex;

import org.junit.Test;
import static sidev.lib.console._ConsoleFunJvm.*;
import static sidev.lib.reflex.full._NewInstanceFunKt.*;


public class SampleTestJvmJ {
/*
    public static void main(String[] args) {
        var ac = new AC<BlaBla2>();
        var ac2 = nativeClone(ac, true, null);

        prin("\n========== Clone ============\n");
        ac.poinLate.setX(143);
        ac2.poinLate.setY(432);
        prin("ac.poinLate.getX()= " +ac.poinLate.getX() +" ac2.poinLate.getX()= " +ac2.poinLate.getX());
        prin("ac.poinLate.gety()= " +ac.poinLate.getY() +" ac2.poinLate.getY()= " +ac2.poinLate.getY());
    }
 */

    @Test
    public void jvmCloneTest(){
        var ac = new AC<BlaBla2>();
        var ac2 = nativeClone(ac, true, null);

        prin("\n========== Clone ============\n");
        ac.poinLate.setX(143);
        ac2.poinLate.setY(432);
        prin("ac.poinLate.getX()= " +ac.poinLate.getX() +" ac2.poinLate.getX()= " +ac2.poinLate.getX());
        prin("ac.poinLate.gety()= " +ac.poinLate.getY() +" ac2.poinLate.getY()= " +ac2.poinLate.getY());
    }
}
