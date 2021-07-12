package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import noGraphic.Manager;
import noGraphic.ReadWriteFile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Controller {
    final static String ERROR="Incorrect command!";
    public void signUp() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signUpScene.fxml"));
        Main.window.setScene(new Scene(root , 800 , 495));
    }


    public void logIn(){
        System.out.println("log in pressed ...");
    }
    public void setting(){
        System.out.println("setting pressed ...");
    }

    public void workEggPowderFac(){
        if(! Manager.work("EggPowderPlant"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void workCookieBakery(){
        if(! Manager.work("CookieBakery"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void workBoxedMilk(){
        if(! Manager.work("BoxedMilk"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void workIceCreamFactory(){
        if(! Manager.work("IceCreamFactory"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void workFeatherFactory(){
        if(! Manager.work("FeatherFactory"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void workTailoringFactory(){
        if(! Manager.work("TailoringFactory"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void well(){
        if(Manager.drainge()==false){
            ReadWriteFile.WriteLogger(false,"The water buket is full!");
        }
        else
            ReadWriteFile.WriteLogger(true,"Drainaged successfully");
    }
    public void buildCookieBakery(){
        if(!Manager.buildWorkShop("CookieBakery"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void buildBoxedMilk(){
        if(!Manager.buildWorkShop("BoxedMilk"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void buildIceCreamFactory(){
        if(!Manager.buildWorkShop("IceCreamFactory"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void buildFeatherFactory(){
        if(!Manager.buildWorkShop("FeatherFactory"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }
    public void buildTailoringFactory(){
        if(!Manager.buildWorkShop("TailoringFactory"))
            ReadWriteFile.WriteLogger(false,ERROR);
    }







}