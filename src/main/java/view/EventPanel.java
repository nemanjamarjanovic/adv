/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author nemanja.marjanovic
 */
public class EventPanel
{
     private VBox vbox = new VBox();

    public EventPanel()
    {
        vbox.setPrefWidth(600);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setSpacing(20);
         vbox.getStyleClass().add("panel");

        Text notImpl = new Text("To be concluded!");

        vbox.getChildren().addAll(notImpl);
    }

    public VBox getVbox()
    {
        return vbox;
    }
}
