package noGraphic;
/**
 * Created by user on 30/05/2021.
 */
public class Tiger extends WildAnimal {
    static int tigers=0;
    public Tiger() {
        super(500,"Tiger"+tigers,4);
        tigers++;
    }
    public static void produce(){
        Tiger tiger=new Tiger();
    }

    public void move(){
        super.move(1);
        super.move(1);
    }

}