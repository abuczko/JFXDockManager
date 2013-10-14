/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
public class DockManagerPositionPanel extends BorderPane {

    @FXML
    private ImageView topButton;
    @FXML
    private ImageView leftButton;
    @FXML
    private ImageView rightButton;
    @FXML
    private ImageView bottomButton;
    private final DockManager dockManager;

    public DockManagerPositionPanel(DockManager dockManager) {
        this.dockManager = dockManager;
        try {
            FXMLLoader loader = new FXMLLoader(DockManagerPositionPanel.class.getResource("/view/DockManagerPositionPanel.fxml"));
            loader.setRoot(DockManagerPositionPanel.this);
            loader.setController(DockManagerPositionPanel.this);
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DockManagerPositionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void topSideDragDetect(MouseEvent t) {
        try {
            //draw selected side
        } finally {
            t.consume();
        }
    }
}
