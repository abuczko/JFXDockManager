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
    private final HideableDockPanel leftSide = new HideableDockPanel();
    private final HideableDockPanel rightSide = new HideableDockPanel();
    private final HideableDockPanel topSide = new HideableDockPanel();
    private final HideableDockPanel bottomSide = new HideableDockPanel();
    private final DocumentDocPanel centerSide = new DocumentDocPanel();
    
    /*
     * *************************************************************************
     * PRIVATE VARIABLES********************************************************
     * *************************************************************************
     */
    private ObservableList<DockPanel> dockPanels = FXCollections.observableArrayList();
    private ColumnConstraints leftSideColumnConstraint = new ColumnConstraints();
    private ColumnConstraints rightSideColumnConstraint = new ColumnConstraints();
    private ColumnConstraints centerSideColumnConstraint = new ColumnConstraints();
    private RowConstraints topSideRowConstraint = new RowConstraints();
    private RowConstraints bottomSideRowConstraint = new RowConstraints();
    private RowConstraints centerSideRowConstraint = new RowConstraints();
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
//        this.dockPanels.addListener(dockPanelChangeListener);        
        
        this.parentProperty().addListener(parentProeprtyChangeListener);
        this.getColumnConstraints().add(leftSideColumnConstraint);
        this.getColumnConstraints().add(rightSideColumnConstraint);
        this.getColumnConstraints().add(centerSideColumnConstraint);
        this.getRowConstraints().add(topSideRowConstraint);
        this.getRowConstraints().add(bottomSideRowConstraint);
        this.getRowConstraints().add(centerSideRowConstraint);
        this.add(leftSide.graphicProperty().get(), 0, 0,1,3);
        this.add(rightSide.graphicProperty().get(), 2, 0,1,3);
        this.add(topSide.graphicProperty().get(), 0, 0,3,1);
        this.add(bottomSide.graphicProperty().get(), 0, 2,3,1);
        //this.add(centerSide.graphicProperty().get(), 1, 2,1,1);
        this.add(positionPanel, 0, 0, 3, 3);
        this.positionPanel.toBack();
        
//        
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
//    private ListChangeListener<DockPanel> dockPanelChangeListener = new ListChangeListener<DockPanel>() {
//        @Override
//        public void onChanged(ListChangeListener.Change<? extends DockPanel> change) {
//            while (change.next()) {
//                if (change.wasAdded()) {
//                    for (DockPanel dockPanel : change.getAddedSubList()) {
//                        DockManager.this.getChildren().add((Node) dockPanel.graphicProperty().get());
//                    }
//                }
//            }
//        }
//    };
}
