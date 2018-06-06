package com.ksundaysky.model.wrapper;

import com.ksundaysky.model.Component;

public class SelectableComponent extends Component {
    boolean selected;

    public SelectableComponent() {

    }

    public SelectableComponent(Component component) {
        this.setId(component.getId());
        this.setComponent_name(component.getComponent_name());
        this.setPrice(component.getPrice());
        this.setType(component.getType());

        this.setSelected(false);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}