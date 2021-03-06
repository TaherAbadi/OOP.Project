package noGraphic;

import sample.AnimalAnim;
import sample.Main;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 29/05/2021.
 */
public class Manager {
    static int prize;
    public static int time=0;
    public static int goldTime;
    static Users user;
    static HashMap<String,String> users=new HashMap <String,String>();
    public static HashMap<String,Integer> animals=new HashMap <String,Integer>();
    public static HashMap<String,Integer> missionAnimal=new HashMap <String,Integer>();
    public static HashMap<String,Integer> missionProduct=new HashMap <String,Integer>();
    static HashMap<String,ArrayList<Integer>> wilds=new HashMap <String,ArrayList<Integer>>();
    public static int missionCoin;
    public static Land land=new Land();
    static Well well=new Well();
    public static WareHouse wareHouse=new WareHouse();
    public static MilkBoxing milkBoxing=new MilkBoxing();
    public static IceCreamFactory iceCreamFactory=new IceCreamFactory();
    public static EggPowderPlant eggPowderPlant=new EggPowderPlant();
    public static CookieBakery cookieBakery=new CookieBakery();
    public static FeatherFactory featherFactory=new FeatherFactory();
    public static TailoringFactory tailoringFactory=new TailoringFactory();
    public static WorkShop[] workShops={milkBoxing,iceCreamFactory,eggPowderPlant,cookieBakery,featherFactory,tailoringFactory};
    static Truck truck=new Truck();
    public static int coin=1000;

    static public void addUser(String name, String pass){
        Users user1 = new Users(name , pass,1);
        ReadWriteFile.WriteUsers(user1);
        user = user1;
    }
    static public boolean isUser(String name){
        ArrayList<Users> users = ReadWriteFile.ReadUsers();
        for (Users user : users) {
            if (user.getUserName().equals(name))
                return true;
        }
        return false;
    }
    static public boolean isPass(String name,String pass){
        ArrayList<Users> users = ReadWriteFile.ReadUsers();
        for (Users user1 : users) {
            if (user1.getUserName().equals(name))
                if (user1.getPassWord().equals(pass)){
                    user = user1;
                    return true;}
        }
        return false;
    }

    static public boolean level(int level){
        animals.put("Hen", 0);
        animals.put("Turkey", 0);
        animals.put("Buffalo", 0);
        animals.put("Dog", 0);
        animals.put("Cat", 0);
        if(user.getMaxLevel()>=level){
            missionAnimal=ReadWriteFile.ReadMissionsAnimals(level);
            missionProduct=ReadWriteFile.ReadMissionsProducts(level);
            wilds=ReadWriteFile.ReadMissionWildAnimal(level);
            goldTime=ReadWriteFile.ReadMissionTime(level)[1];
            prize=ReadWriteFile.ReadMissionTime(level)[0];
            missionCoin=ReadWriteFile.ReadMissionsCoin(level);
            return true;
        }
        return false;
    }

    public static void checkNumberOfAnimals(){
        int hen=0;
        int turkey=0;
        int buffalo=0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (Animal animal:land.fields[i][j].animals) {
                    if (animal instanceof Hen)
                        hen++;
                    else if (animal instanceof Turkey)
                        turkey++;
                    else if (animal instanceof Buffalo)
                        buffalo++;

                }
            }
        }
        animals.put("Hen" , hen);
        animals.put("Turkey" , turkey);
        animals.put("Buffalo", buffalo);
    }

    static public void buyAnimal(String animal) {
        if(animal.equals("Hen")){
            if(Hen.purchase(coin)==false){
                ReadWriteFile.WriteLogger(false,"not enough coin");
            }
            else{
                animals.put("Hen", animals.get("Hen")+1);
                ReadWriteFile.WriteLogger(true,"purchased a hen");
            }
        }
        else if(animal.equals("Turkey")){
            if(Turkey.purchase(coin)==false){
                ReadWriteFile.WriteLogger(false,"not enough coin");
            }
            else{
                animals.put("Turkey", animals.get("Turkey")+1);
                ReadWriteFile.WriteLogger(true,"purchased a turkey");
            }
        }
        else if(animal.equals("Buffalo")){
            if(Buffalo.purchase(coin)==false){
                ReadWriteFile.WriteLogger(false,"not enough coin");
            }
            else{
                animals.put("Buffalo", animals.get("Buffalo")+1);
                ReadWriteFile.WriteLogger(true,"purchased a buffalo");
            }
        }
        else if(animal.equals("Cat")){
            if(Cat.purchase(coin)==false){
                ReadWriteFile.WriteLogger(false,"not enough coin");
            }
            else{
                animals.put("Cat", animals.get("Cat")+1);
                ReadWriteFile.WriteLogger(true,"purchased a cat");
            }
        }
        else if(animal.equals("Dog")){
            if(Dog.purchase(coin)==false){
                ReadWriteFile.WriteLogger(false,"not enough coin");
            }
            else{
                animals.put("Dog", animals.get("Dog")+1);
                ReadWriteFile.WriteLogger(true,"purchased a buffalo");
            }
        }
        else
            ReadWriteFile.WriteLogger(false, "Incorrect command!");
    }
    static public int pickUp(int x,int y){
        int returnn=3;
        ArrayList<Product> toRemove=new ArrayList <Product>();
        if(!land.fields[x-1][y-1].products.isEmpty()){
            for (Product product:land.fields[x-1][y-1].products) {
                if(wareHouse.AddProduct(product.type())){
                    product.grab();
                    toRemove.add(product);
                    //land.fields[x-1][y-1].products.remove(product);
                    returnn=1;
                }
                else
                returnn=2;
            }
        }
        land.fields[x-1][y-1].products.removeAll(toRemove);
        return returnn;
    }
    public static boolean drainge(){
        return well.drainage();
    }
    static public boolean plantGrass(double x, double y){
        return land.fields[(int)x/61][(int)y/52].plantGrass(x,y);
    }
    public static boolean cage(int x, int y){
        boolean caged=false;
        ArrayList<WildAnimal> toRemove=new ArrayList <WildAnimal>();
        for (Animal animal:land.fields[x][y].animals) {
            if(animal instanceof Bear ){
                ((Bear) animal).caged(toRemove);
                ReadWriteFile.WriteLogger(true,"A bear caged successfully in "+String.valueOf(x)+" "+String.valueOf(y));
                //toRemove.add((WildAnimal) animal);
                caged=true;
            }
            else if(animal instanceof Tiger ){
                ((Tiger) animal).caged(toRemove);
                ReadWriteFile.WriteLogger(true,"A tiger caged successfully in "+String.valueOf(x)+" "+String.valueOf(y));
                //toRemove.add((WildAnimal) animal);
                caged=true;
            }
            else if(animal instanceof Lion){
                ((Lion) animal).caged(toRemove);
                ReadWriteFile.WriteLogger(true,"A lion caged successfully in "+String.valueOf(x)+" "+String.valueOf(y));
                //toRemove.add((WildAnimal) animal);
                caged=true;
            }
        }
        land.fields[x][y].animals.removeAll(toRemove);

        if(caged==false)
            ReadWriteFile.WriteLogger(false,"There is no wild animal in");
        return caged;
    }
    public static int update(int n){
        for (int i = 0; i <n ; i++) {
            time++;
            produceWild();
            milkBoxing.update();
            iceCreamFactory.update();
            eggPowderPlant.update();
            cookieBakery.update();
            featherFactory.update();
            tailoringFactory.update();
            land.updateLand();
            coin+=truck.updateTruck();
            checkNumberOfAnimals();
            if(win()){
                inquiry();
                if(time<=goldTime) {
                    Manager.user.addToSavedCoins(prize);
                return 1;
                }
                else
                    return -1;
            }
        }
        inquiry();
        ReadWriteFile.WriteLogger(true,"turned "+String.valueOf(n));
        return 0;
    }
    public static boolean work(String name){
        for (int i = 0; i < 6; i++) {
           if( workShops[i].getName().equals(name)){
               if(workShops[i].getLevel()>=1){
                   if (WorkShop.produce(workShops[i]))
                   ReadWriteFile.WriteLogger(true,name+" started working!");
                   else
                       ReadWriteFile.WriteLogger(false,name+" not enough product!");
               }
               else
                   ReadWriteFile.WriteLogger(false,name+" isn't build yet!");

               return true;
           }
        }
        return false;
    }
    public static boolean truckIsOnWay(){
        return truck.isOnWay();
    }
    public static int truckCapacityGetter(){
        return truck.getCapacity();
    }
    public static int truckCAPACITYGetter(){
        return truck.getCAPACITY();
    }
    public static void loadTruck(String product){
        truck.truckLoadProduct(product);
    }
    public static void truckLoadAnimal(String animal){
        truck.truckLoadAnimal(animal);
    }
    public static void unloadTruck(String product) {
        truck.truckUnloadProduct(product);
    }
    public static boolean truckGo(){
        return truck.go();
    }
    public static boolean win() {
        for (String animal : missionAnimal.keySet())
            if (missionAnimal.get(animal) != 0)
                for (String animalAvailable : animals.keySet())
                    if (animalAvailable.equals(animal))
                        if (animals.get(animalAvailable) < missionAnimal.get(animal)) {
                            System.out.println("Animal");
                            return false;
                        }


        for (String product:missionProduct.keySet()) {
            if(missionProduct.get(product)!=0){
                for (String productAvailable:wareHouse.getProductsStorage().keySet()) {
                    if(productAvailable.equals(product))
                        if(wareHouse.getProductsStorage().get(productAvailable)<missionProduct.get(product)){
                            System.out.println("Product");
                            return false;

                    }
                }
            }
        }
        if(missionCoin>coin) {
            System.out.println("coin");
            return false;
        }

        ReadWriteFile.WriteLogger(true,"You win level"+user.getMaxLevel());
        user.addMaxLevel();
        ReadWriteFile.addMaxLevel(user);
        return true;
    }
    public static  String nearestProduct(int x,int y){
        double d=Double.POSITIVE_INFINITY;
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <6; j++) {
                if(!land.fields[i][j].products.isEmpty())
                    d= Math.min(d,(Math.abs(x-i-1)+Math.abs(y-j-1)));
            }
        }
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <6; j++) {
                if(!land.fields[i][j].products.isEmpty())
                    if((Math.abs(x-i-1)+Math.abs(y-j-1))==d)
                        return String.valueOf(i+1)+String.valueOf(j+1);
            }
        }
        return "00";
    }
    public static String nearestGrass(int x,int y){
        double d=Double.POSITIVE_INFINITY;
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <6; j++) {
                if(!land.fields[i][j].grasses.isEmpty())
                    d= Math.min(d,(Math.abs(x-i-1)+Math.abs(y-j-1)));
            }
        }
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <6; j++) {
                if(!land.fields[i][j].grasses.isEmpty())
                    if((Math.abs(x-i-1)+Math.abs(y-j-1))==d)
                        return String.valueOf(i+1)+String.valueOf(j+1);
            }
        }
        return "00";
    }
    public static  void inquiry(){
        System.out.println("Unit of time elapsed: "+time);
        System.out.println("coin : " + coin);
        System.out.print("Grasses: \n");
        for (int i = 0; i <6 ; i++) {
            System.out.print("{");
            for (int j = 0; j <6 ; j++) {
                System.out.print( land.fields[i][j].grasses.size()+",");
            }
            System.out.println("}");
        }
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <6 ; j++) {
                for (Animal animal:land.fields[i][j].animals) {
                  if(animal instanceof DomesticAnimal)
                      System.out.println(animal.getName()+" "+((DomesticAnimal) animal).getAge()+"["+String.valueOf(i+1)+"  "+String.valueOf(j+1)+"]");
                  else if(animal instanceof Dog)
                      System.out.println(animal.getName()+" "+"["+String.valueOf(i+1)+"  "+String.valueOf(j+1)+"]");
                  else if(animal instanceof Cat)
                      System.out.println(animal.getName()+" "+"["+String.valueOf(i+1)+"  "+String.valueOf(j+1)+"]");
                  else if(animal instanceof WildAnimal)
                      System.out.println(animal.getName()+" cage needed: "+((WildAnimal) animal).getCaged()+" "+"["+String.valueOf(i+1)+"  "+String.valueOf(j+1)+"]");
                }
            }
        }
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <6 ; j++) {
                for (Product product:land.fields[i][j].products) {
                    System.out.println(product.name+"["+String.valueOf(i+1)+"  "+String.valueOf(j+1)+"]");
                }
            }
        }
        for (String animal:missionAnimal.keySet()) {
            if(missionAnimal.get(animal)!=0){
                for (String animalAvailable:animals.keySet()) {
                    if(animalAvailable.equals(animal))
                        System.out.println(animal+ String.valueOf(animals.get(animal)+"/"+ String.valueOf(missionAnimal.get(animal))));
                }
            }
        }
        for (String product:missionProduct.keySet()) {
            if(missionProduct.get(product)!=0){
                for (String productAvailable:wareHouse.getProductsStorage().keySet()) {
                    if(productAvailable.equals(product))
                        System.out.println(product+String.valueOf(wareHouse.getProductsStorage().get(product))+"/"+String.valueOf(missionProduct.get(product)));
                }
            }
        }
    }
    public static boolean buildWorkShop(String name){
        for (int i = 0; i <6 ; i++) {
            if(workShops[i].getName().equals(name)){
                if(workShops[i].getLevel()==0) {
                    workShops[i].setLevel();
                    ReadWriteFile.WriteLogger(true,name+"built!");
                    Manager.coin-=workShops[i].buildingCost;
                }
                else
                    ReadWriteFile.WriteLogger(false,"this workshop already exist!");
                return true;
            }
        }
        return false;
    }
    public static void produceWild(){
        for (String name:wilds.keySet()) {
            for (Integer timeOfWild:wilds.get(name)) {
                if(time==timeOfWild) {
                    if (name.equals("Lion"))
                        Lion.produce();
                    else if(name.equals("Tiger"))
                        Tiger.produce();
                    else if(name.equals("Bear")){
                        Bear.produce();
                    }
                }
            }
        }
    }
    public static void henProducer(){
        if(wareHouse.getProductsStorage().get("Egg")>0){
            wareHouse.getProductsStorage().put("Egg",wareHouse.getProductsStorage().get("Egg")-1);
            try {
                Hen newHen=new Hen();
                AnimalAnim animalAnim=new AnimalAnim(newHen);
                Main.animalAnims.add(animalAnim);
            }
            catch (Exception FileNotFoundException){

            }
        }
    }
    /*public static boolean lose(){
        if(time>=missionTime[0])
            return true;
        else
            return false;
    }*/



}
