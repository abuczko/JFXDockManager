/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.event;

import controller.DockPanel;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
public class DockPanelModificationEvent<T> extends Event {

    public static final EventType DOCKPANEL_NOTIFICATION_EVENT = new EventType<>("DockPanelNotificationEvent");
    public static final EventType GRAPHIC_CHANGED_EVENT = new EventType<>("GraphicChangedEvent");
    public static final EventType CONTENT_CHANGED_EVENT = new EventType<>("ContentChangedEvent");
    private final boolean isVisible;
    private DockState dockState = DockState.FLOAT;

    public DockPanelModificationEvent(EventType<? extends Event> eventType, DockPanel panel) {
        super(eventType);
        this.isVisible = false;
    }

    public DockPanelModificationEvent(EventType<? extends Event> eventType, DockPanel panel, boolean isVisible) {
        super(eventType);
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public DockState getDockState() {
        return dockState;
    }

    public static enum DockState {

        LEFT_DOCKED,
        RIGHT_DOCKED,
        TOP_DOCKED,
        BOTTOM_DOCKED,
        CENTER_DOCKED,
        FLOAT, 
        Hidden
    };
}
