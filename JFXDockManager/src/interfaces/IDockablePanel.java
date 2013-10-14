/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Attila
 */
public interface IDockablePanel<T extends Enum> {
    ObjectProperty<T> dockPositionProperty();
    void setDockPosition(T dockPosition);
    T getDockPosition();
}
