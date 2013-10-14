/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Attila Buczko { email: buczko.attila@gmail.com }
 */
public class DocumentDocPanel extends DockPanel<HideableDockPanel.HideableDockPanelSkin, HideableDockPanel.DockPosition> {

    public DocumentDocPanel() {
        super(null, DockPanelType.DOCUMENT);
    }

    @Override
    public void setDockPosition(HideableDockPanel.DockPosition dockPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HideableDockPanel.DockPosition getDockPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
