package kumm.stackcarousel;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.vaadin.virkki.carousel.ComponentSelectListener;
import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

    private int n = 0, depth = 0;
    private HorizontalCarousel c = new HorizontalCarousel();
    	
    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        layout.setSizeFull();
        c.setArrowKeysMode(ArrowKeysMode.DISABLED);
        c.setButtonsVisible(false);
        c.setLoadMode(CarouselLoadMode.EAGER);
        c.setMouseDragEnabled(false);
        c.setMouseWheelEnabled(false);
        c.setTouchDragEnabled(false);
        c.setTransitionDuration(1000);
        layout.addComponent(c);
        c.addComponentSelectListener(new ComponentSelectListener() {
            @Override
            public void componentSelected(Component component) {
                Iterator<Component> iterator = c.iterator();
                Component i = null;
                while (iterator.hasNext() && i != component) {
                    i = iterator.next();
                }
                List<Component> toRemove = new ArrayList<Component>();
                while (iterator.hasNext()) {
                    i = iterator.next();
                    toRemove.add(i);
                }
                for (Component tobeRemoved : toRemove) {
                    c.removeComponent(tobeRemoved);
                }
            }
        });
        c.addComponent(buildChild());
        c.setSizeFull();
        layout.setExpandRatio(c, 1.0f);
        layout.addComponent(new Button("Push", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                push(buildChild());
            }
        }));
        
        layout.addComponent(new Button("Pop", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (depth == 0) {
                    return;
                }
                c.scroll(-1);
                depth--;
            }
        }));

    }

    private void push(Component child) {
        depth++;
        if (c.getComponentCount() > depth) {
            Iterator<Component> iterator = c.iterator();
            Component toReplace = null;
            int d = 0;
            while (iterator.hasNext() && d <= depth) {
                toReplace = iterator.next();
                d++;
            }
            c.replaceComponent(toReplace, child);
        } else {
            c.addComponent(child);
        }
        c.requestWidgets(depth);
        c.scroll(1);
    }
    
    private Panel buildChild() {
        String str = "Num " + (++n);
        Panel p = new Panel(str);
        VerticalLayout layout = new VerticalLayout();
        p.setContent(layout);
        layout.setSizeFull();
        p.setSizeFull();
        layout.addComponent(new Button(str, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                System.out.println("TALÁLT " + n);
            }
        }));
        layout.addComponent(new Label("Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum "));
        return p;
    }
}
