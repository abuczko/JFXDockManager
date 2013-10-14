/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.event;

import controller.DockPanel;
import interfaces.IDockPanel;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
public class DockingDetectEvent extends Event {

    public static final EventType<DockingDetectEvent> DOCKING_DETECT_EVENT = new EventType<>("DOCKING_DETECT_EVENT");
    private final DockPanel dockPanel;

    public DockingDetectEvent(EventType<DockingDetectEvent> eventType, DockPanel dockPanel) {
        super(eventType);
        this.dockPanel = dockPanel;
    }
    
    public DockPanel getDockPanel(){
        return this.dockPanel;
    }
}
