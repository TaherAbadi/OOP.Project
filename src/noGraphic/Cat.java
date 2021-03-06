package noGraphic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.AnimalAnim;
import sample.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by user on 29/05/2021.
 */
public class Cat extends Animal {
    private static final int PRICE=150;
    static int numberOfCats=0;
    public Cat() throws FileNotFoundException {
        InputStream stream=new FileInputStream("F:\\image\\cat.png");
        Image image=new Image(stream);
        this.setImage(image);
        this.setFitHeight(60);
        this.setFitWidth(60);

        this.name="Cat"+ String.valueOf(numberOfCats);
        Random random=new Random();
       this.width=0; this.length=0;
        while (this.width<1 || this.length<1){
            this.length=random.nextInt(7);
            this.width=random.nextInt(7);
        }
        AnimalAnim animalAnim=new AnimalAnim(this);
        Main.animalAnims.add(animalAnim);

    }

    public void collect(){
       Manager.pickUp(this.length,this.width);
    }
    public void moveCat() {
        String s = Manager.nearestProduct(this.length, this.width);
        int x = Integer.parseInt(s.substring(0, 1)), y = Integer.parseInt(s.substring(1));
        if (x != 0 && y != 0) {
            if (this.length > x) {
                this.length--;
            } else if (this.length < x) {
                this.length++;
            } else {
                if (this.width < y) {
                    this.width++;
                } else if (this.length > y) {
                    this.width--;
                }
            }
        }
        else
            super.move(1);
    }
    public static boolean purchase(int coin) {
        if(coin>=PRICE){
            try {
                Cat cat=new Cat();
                numberOfCats++;
                Manager.land.fields[cat.length-1][cat.width-1].animals.add(cat);
                Manager.coin-=PRICE;
            }
            catch (Exception FileNotFoundException){

            }

            //Manager.land.fields[cat.length][cat.width].animals.add(cat);
            return true;
        }
        return false;
    }
}
