package dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DialogTemplate extends AcceptDeclineDialog{
    private class ComponentTemplate{
        private String key;
        private JLabel label;
        private JComponent component;

        public ComponentTemplate(String key, String label, JComponent component) {
            this.key = key;
            this.label = new JLabel(label);
            this.component = component;
        }
    }

    private ArrayList<ComponentTemplate> components = new ArrayList<>();

    public void addComponent(String label, JComponent component, String key){
        components.add(new ComponentTemplate(key, label, component));
        updateContentPanel();
    }

    public void addComponent(int index, String label, JComponent component, String key){
        components.add(index, new ComponentTemplate(key, label, component));
        updateContentPanel();
    }

    private void updateContentPanel(){
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0,2,5,5));
        for (ComponentTemplate ct : components){
            contentPanel.add(ct.label);
            contentPanel.add(ct.component);
        }
        addContentPanel(contentPanel);
    }

    public JComponent getComponentByKey(String key){
        ComponentTemplate ct = findByKey(key);
        if(ct != null)
            return ct.component;
        return null;
    }

    public JLabel getLabelByKey(String key){
        ComponentTemplate ct = findByKey(key);
        if(ct != null){
            return ct.label;
        }
        return null;
    }

    private ComponentTemplate findByKey(String key){
        for(ComponentTemplate ct : components){
            if(ct.key.equals(key))
                return ct;
        }
        return null;
    }
}
