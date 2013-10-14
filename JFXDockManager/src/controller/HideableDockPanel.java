/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfaces.IDockPanel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
public class HideableDockPanel extends DockPanel<HideableDockPanel.HideableDockPanelSkin, HideableDockPanel.DockPosition> {

    public HideableDockPanel() {
        super(new HideableDockPanelSkin());

    }
    private ObjectProperty<HideableDockPanelState> hideableDockPanelStateProperty = new SimpleObjectProperty<>();

    public void setHideableDockPanelState(HideableDockPanelState state) {
        this.hideableDockPanelStateProperty.set(state);
    }

    public HideableDockPanelState getHideableDockPanelState() {
        return this.hideableDockPanelStateProperty.get();
    }

    public final ObjectProperty<HideableDockPanelState> hideableDockPanelStateProperty() {
        return this.hideableDockPanelStateProperty;
    }

    @Override
    public void setDockPosition(DockPosition dockPosition) {
        HideableDockPanel.super.dockPositionProperty().set(dockPosition);
    }

    @Override
    public DockPosition getDockPosition() {
        return HideableDockPanel.super.dockPositionProperty().get();
    }

    public static enum HideableDockPanelState {

        HIDDEN,
        FLOATING,
        DOCKED
    };

    public static enum DockPosition {

        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    };

    public static class HideableDockPanelSkin extends GridPane implements IDockPanel {

        @FXML
        private SplitPane content;
        @FXML
        private Label titleLabel;

        public HideableDockPanelSkin() {
            try {
                FXMLLoader loader = new FXMLLoader(HideableDockPanel.class.getResource("/view/HideableDockPanel.fxml"));
                loader.setRoot(HideableDockPanelSkin.this);
                loader.setController(HideableDockPanelSkin.this);
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(HideableDockPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public ImageView getIcon() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setHeaderText(String title) {
            titleLabel.textProperty().set(title);
        }

        @Override
        public String getHeaderText() {
            return titleLabel.getText();
        }

        @Override
        public void changeContent(Node content) {
            this.content.getItems().clear();
            this.content.getItems().add(content);
        }
    }
}
