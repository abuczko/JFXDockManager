/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
public interface IDockPanel {
    ImageView getIcon();
    void setHeaderText(String title);
    String getHeaderText();    
    void changeContent(Node content);
}
