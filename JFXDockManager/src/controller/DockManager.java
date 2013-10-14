/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.event.DockingDetectEvent;
import interfaces.IPositionPanelOperations;
import javafx.beans.DefaultProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
@DefaultProperty(value = "dockablePanels")
public final class DockManager extends GridPane implements IPositionPanelOperations {
    /*
     * *************************************************************************
     * PRIVATE FINAL VARIABLES**************************************************
     * *************************************************************************
     */
    private final DockManagerPositionPanel positionPanel;    
    private final ObservableList<DockPanel> dockPanels = FXCollections.observableArrayList();
    private final ColumnConstraints leftSideColumnConstraint = new ColumnConstraints();
    private final ColumnConstraints rightSideColumnConstraint = new ColumnConstraints();
    private final ColumnConstraints centerSideColumnConstraint = new ColumnConstraints();
    private final RowConstraints topSideRowConstraint = new RowConstraints();
    private final RowConstraints bottomSideRowConstraint = new RowConstraints();
    private final RowConstraints centerSideRowConstraint = new RowConstraints();
    /*
     * *************************************************************************
     * PUBLIC CONSTRUCTOR*******************************************************
     * *************************************************************************
     */

    public DockManager() {
        super();
        this.positionPanel = new DockManagerPositionPanel(DockManager.this);
        super.addEventHandler(DockingDetectEvent.DOCKING_DETECT_EVENT, showDockingPositions);
        initialize();
    }
    /*
     * *************************************************************************
     * PUBLIC OVERRIDE METHODS**************************************************
     * *************************************************************************
     */

    @Override
    public void showPositions() {
        this.positionPanel.visibleProperty().set(true);
        this.positionPanel.toFront();
    }

    @Override
    public void hidePositions() {
        this.positionPanel.visibleProperty().set(false);
        this.positionPanel.toBack();
    }
    /*
     * *************************************************************************
     * PUBLIC GETTERS AND SETTERS***********************************************
     * *************************************************************************
     */

    public ObservableList<DockPanel> getDockPanels() {
        return dockPanels;
    }
    /*
     * *************************************************************************
     * PRIVATE METHODS**********************************************************
     * *************************************************************************
     */
    private void initialize() {        
        this.positionPanel.prefHeightProperty().bind(this.prefHeightProperty());
        this.positionPanel.prefWidthProperty().bind(this.prefWidthProperty());
        this.dockPanels.addListener(dockPanelChangeListener);  
        this.parentProperty().addListener(parentProeprtyChangeListener);
        this.getColumnConstraints().add(leftSideColumnConstraint);
        this.getColumnConstraints().add(rightSideColumnConstraint);
        this.getColumnConstraints().add(centerSideColumnConstraint);
        this.getRowConstraints().add(topSideRowConstraint);
        this.getRowConstraints().add(bottomSideRowConstraint);
        this.getRowConstraints().add(centerSideRowConstraint);
        this.add(positionPanel, 0, 0, 3, 3);
        this.positionPanel.toBack();
    }
    /*
     * *************************************************************************
     * PRIVATE EVENTHANDLERS****************************************************
     * *************************************************************************
     */
    private EventHandler<DockingDetectEvent> showDockingPositions = new EventHandler<DockingDetectEvent>() {
        @Override
        public void handle(DockingDetectEvent t) {
            try {
                showPositions();
            } finally {
                t.consume();
            }
        }
    };
    private ChangeListener<Node> parentProeprtyChangeListener = new ChangeListener<Node>() {
        @Override
        public void changed(ObservableValue<? extends Node> ov, Node t, Node t1) {
            if (t1 != null) {
                DockManager.super.setPrefWidth(t1.getBoundsInLocal().getWidth());
                DockManager.super.setPrefHeight(t1.getBoundsInLocal().getHeight());
            }
        }
    };
    private ListChangeListener<DockPanel> dockPanelChangeListener = new ListChangeListener<DockPanel>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends DockPanel> change) {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (DockPanel dockPanel : change.getAddedSubList()) {
                        
                        DockManager.this.getChildren().add((Node) dockPanel.graphicProperty().get());
                    }
                }
            }
        }
    };
}
