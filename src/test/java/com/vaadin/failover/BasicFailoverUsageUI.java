package com.vaadin.failover;

import com.vaadin.ui.*;
import org.vaadin.addonhelpers.AbstractTest;

import java.util.Arrays;
import java.util.List;

/**
 * Add many of these with different configurations,
 * combine with different components, for regressions
 * and also make them dynamic if needed.
 */
public class BasicFailoverUsageUI extends AbstractTest {

    public BasicFailoverUsageUI() {
        setDescription("A basic failover to another server. Just kill the server and click the button.");
    }

    private final Label status = new Label();

    @Override
    public Component getTestComponent() {
        final List<String> urls = Arrays.asList("http://197.100.100.100", "http://197.100.100.101", "http://197.100.100.102", "https://vaadin.com");
        final FailoverReconnectExtension reconnectExtension = FailoverReconnectExtension.addTo(UI.getCurrent());
        reconnectExtension.setUrls(urls);
        reconnectExtension.setStatusLabel(status);
        reconnectExtension.setInfinite(false);
        reconnectExtension.setRandomRobin(false);
        reconnectExtension.setPingMillis(2000);
        getReconnectDialogConfiguration().setDialogText("Can't connect to the server. The network may be down, or the server has crashed. Press the 'Reconnect' button to try to connect to fallback server.");
        final VerticalLayout vl = new VerticalLayout();
        vl.addComponent(new Label("Kill the server and click the button: the browser should automatically redirect to " + urls));
        vl.addComponent(new Button("Click me"));
        vl.addComponent(new Label("The button below will invoke the reconnect functionality directly, no need to kill the server."));
        vl.addComponent(status);
        vl.addComponent(new Button("Start FailOver", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                reconnectExtension.startFailOver();
            }
        }));
        vl.addComponent(new Button("Cancel FailOver", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                reconnectExtension.cancelFailOver();
            }
        }));
        return vl;
    }
}
