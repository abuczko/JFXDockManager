/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.event.EventHandlerManager;
import controller.event.DockPanelModificationEvent;
import controller.event.DockingDetectEvent;
import interfaces.IDockPanel;
import interfaces.IDockablePanel;
import interfaces.IPositionPanelOperations;
import javafx.beans.DefaultProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
@DefaultProperty(value = "content")
public abstract class DockPanel<T extends Node & IDockPanel, K extends Enum> implements EventTarget, IPositionPanelOperations, IDockablePanel<K> {

    private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);
    private ReadOnlyObjectWrapper<DockPanel> parent = new ReadOnlyObjectWrapper<>(this, "parent");
    private ObjectProperty<T> graphic = null;
    private ObjectProperty<Node> content = null;
    private DoubleProperty prefWidth = null;
    private DoubleProperty prefHeight = null;
    private ObjectProperty<K> dockPosition = null;
    private DockPanelType dockPanelType;
    public StringProperty title = null;

    public final StringProperty titleProperty() {
        if (title == null) {
            title = new StringPropertyBase() {
                @Override
                protected void invalidated() {
                    DockPanel.this.graphicProperty().get().setHeaderText(get());
                }

                @Override
                public Object getBean() {
                    return DockPanel.this;
                }

                @Override
                public String getName() {
                    return "title";
                }
            };
        }
        return title;
    }

    public final ObjectProperty<T> graphicProperty() {
        if (graphic == null) {
            graphic = new ObjectPropertyBase<T>() {
                @Override
                protected void invalidated() {
                    fireEvent(new DockPanelModificationEvent<T>(DockPanelModificationEvent.GRAPHIC_CHANGED_EVENT, DockPanel.this));
                }

                @Override
                public Object getBean() {
                    return DockPanel.this;
                }

                @Override
                public String getName() {
                    return "graphic";
                }
            };
        }
        return graphic;
    }

    public final ObjectProperty<Node> contentProperty() {
        if (content == null) {
            content = new ObjectPropertyBase<Node>() {
                @Override
                protected void invalidated() {
                    fireEvent(new DockPanelModificationEvent<T>(DockPanelModificationEvent.GRAPHIC_CHANGED_EVENT, DockPanel.this));
                }

                @Override
                public Object getBean() {
                    return DockPanel.this;
                }

                @Override
                public String getName() {
                    return "content";
                }
            };
        }
        return content;
    }

    @Override
    public final ObjectProperty<K> dockPositionProperty() {
        if (dockPosition == null) {
            dockPosition = new ObjectPropertyBase<K>() {
                @Override
                protected void invalidated() {
                    fireEvent(new DockPanelModificationEvent<T>(DockPanelModificationEvent.DOCKPANEL_NOTIFICATION_EVENT, DockPanel.this));
                }

                @Override
                public Object getBean() {
                    return DockPanel.this;
                }

                @Override
                public String getName() {
                    return "dockPosition";
                }
            };
        }
        return dockPosition;
    }

    public void setContent(Node content) {
        this.contentProperty().set(content);
    }

    public Node getContent() {
        return this.contentProperty().get();
    }

    public void setPrefWidth(double d) {
        this.prefWidthProperty().set(d);
    }

    public double getPrefWidth() {
        return this.prefWidthProperty().get();
    }

    public final DoubleProperty prefWidthProperty() {
        if (prefWidth == null) {
            prefWidth = new DoublePropertyBase() {
                @Override
                public Object getBean() {
                    return DockPanel.this;
                }

                @Override
                public String getName() {
                    return "prefWidth";
                }
            };
        }
        return prefWidth;
    }

    public void setPrefHeight(double d) {
        this.prefHeightProperty().set(d);
    }

    public double getPrefHeight() {
        return this.prefHeightProperty().get();
    }

    public final DoubleProperty prefHeightProperty() {
        if (prefHeight == null) {
            prefHeight = new DoublePropertyBase() {
                @Override
                public Object getBean() {
                    return DockPanel.this;
                }

                @Override
                public String getName() {
                    return "prefHeight";
                }
            };
        }
        return prefHeight;
    }

    public void setTitle(String title) {
        this.titleProperty().set(title);
    }

    public String getTitle() {
        return this.titleProperty().get();
    }

    private void setParent(DockPanel value) {
        parent.setValue(value);
    }

    public final DockPanel getParent() {
        return parent.getValue();
    }
    private EventHandler<MouseEvent> dragDetectEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            fireEvent(new DockingDetectEvent(DockingDetectEvent.DOCKING_DETECT_EVENT, DockPanel.this));
        }
    };

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain edc) {
        if (parent.get() != null) {
            parent.get().buildEventDispatchChain(edc);
        }
        return edc.append(eventHandlerManager);
    }

    private void fireEvent(DockPanelModificationEvent evt) {
        Event.fireEvent(this, evt);
    }

    private void fireEvent(DockingDetectEvent evt) {
        Event.fireEvent(this, evt);
    }

    public DockPanel(T graphic, DockPanelType dockPanelType) {
        this();
        this.dockPanelType = dockPanelType;
        graphicProperty().set(graphic);
        contentProperty().addListener(contentChangeListener);
    }

    private DockPanel() {
        eventHandlerManager.addEventFilter(MouseEvent.DRAG_DETECTED, dragDetectEvent);
        prefWidthProperty().addListener(prefWidthChangedListener);
        prefHeightProperty().addListener(prefHeightChangedListener);
    }

    @Override
    public void showPositions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hidePositions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private ChangeListener<Node> contentChangeListener = new ChangeListener<Node>() {
        @Override
        public void changed(ObservableValue<? extends Node> ov, Node t, Node t1) {
            if (t1 != null) {
                t1.prefHeight(DockPanel.this.getPrefHeight());
                t1.prefWidth(DockPanel.this.getPrefWidth());
            }
            graphic.get().changeContent(t1);
        }
    };
    private ChangeListener<Number> prefWidthChangedListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            if (DockPanel.this.graphic.get() != null) {
                DockPanel.this.graphic.get().prefWidth(t1.doubleValue());
                System.out.println("PrefWidth changed");
            }
        }
    };
    private ChangeListener<Number> prefHeightChangedListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            if (DockPanel.this.graphic.get() != null) {
                DockPanel.this.graphic.get().prefHeight(t1.doubleValue());
            }
        }
    };
    
    public DockPanelType getDockPanelType(){
        return dockPanelType;
    }

    public static enum DockPanelType {

        HIDEABLE, FLOAT, DOCUMENT;
        
        public boolean isHideable(){
            return this.equals(HIDEABLE);
        }
        
        public boolean isFloat(){
            return this.equals(FLOAT);
        }
        
        public boolean isDocument(){
            return this.equals(DOCUMENT);
        }
    }
}
