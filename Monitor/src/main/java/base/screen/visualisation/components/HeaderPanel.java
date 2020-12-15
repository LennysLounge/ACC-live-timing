/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.screen.visualisation.components;

import base.screen.client.SessionId;
import base.screen.networking.PrimitivAccBroadcastingClient;
import base.screen.utility.TimeUtils;
import base.screen.visualisation.LookAndFeel;
import base.screen.visualisation.gui.LPComponent;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

/**
 *
 * @author Leonard
 */
public class HeaderPanel extends LPComponent {

    private final PrimitivAccBroadcastingClient client;

    public HeaderPanel(PrimitivAccBroadcastingClient client) {
        this.client = client;
    }

    @Override
    public void draw() {
        applet.fill(LookAndFeel.COLOR_DARK_DARK_GRAY);
        applet.noStroke();
        applet.rect(0, 0, getWidth(), getHeight());
        int y = 0;

        String sessionTimeLeft = TimeUtils.asDurationShort(client.getModel().getSessionInfo().getSessionEndTime());
        String sessionName = sessionIdToString(client.getSessionId());
        String conId = "CON-ID: " + client.getModel().getConnectionID();
        String packetsReceived = "Packets received: " + client.getPacketCount();
        applet.fill(255);
        applet.textAlign(LEFT, CENTER);
        applet.textFont(LookAndFeel.font());
        applet.text(conId, 10, y + LookAndFeel.LINE_HEIGHT * 0.5f);
        applet.text(packetsReceived, 200, y + LookAndFeel.LINE_HEIGHT * 0.5f);
        applet.textAlign(RIGHT, CENTER);
        applet.text(sessionName, applet.width - 10, y + LookAndFeel.LINE_HEIGHT * 0.5f);
        applet.text(sessionTimeLeft,
                applet.width - applet.textWidth(sessionName) - 40,
                y + LookAndFeel.LINE_HEIGHT / 2f);

        applet.fill(LookAndFeel.COLOR_RACE);
        applet.rect(applet.width - applet.textWidth(sessionName) - 30,
                y + LookAndFeel.LINE_HEIGHT * 0.1f,
                10, LookAndFeel.LINE_HEIGHT * 0.8f);
    }

    private String sessionIdToString(SessionId sessionId) {
        String result = "";
        switch (sessionId.getType()) {
            case PRACTICE:
                result = "PRACTICE";
                break;
            case QUALIFYING:
                result = "QUALIFYING";
                break;
            case RACE:
                result = "RACE";
                break;
            default:
                result = "NOT SUPPORTED";
                break;
        }
        return result;
    }

}