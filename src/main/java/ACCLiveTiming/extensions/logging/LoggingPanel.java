/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCLiveTiming.extensions.logging;

import ACCLiveTiming.client.ExtensionPanel;
import ACCLiveTiming.utility.VisualUtils;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import processing.core.PGraphics;

/**
 *
 * @author Leonard
 */
public class LoggingPanel extends ExtensionPanel {

    private LoggingExtension extension;

    private int scroll;

    public LoggingPanel(LoggingExtension extension) {
        this.extension = extension;

        this.displayName = "LOGGING";
    }

    @Override
    public void drawPanel(PGraphics context) {
        int lineHeight = VisualUtils.LINE_HEIGHT;
        int tabSize = 130;

        List<String> messages = new LinkedList<>();
        messages.addAll(extension.getMessages());
        Collections.reverse(messages);

        if (scroll < 0) {
            scroll = 0;
        }
        int visibleLines = context.height / lineHeight;
        if (scroll > messages.size() - visibleLines) {
            scroll = messages.size() - visibleLines;
        }

        int n = 0;
        int scrollCount = scroll;
        for (String msg : messages) {
            if (scrollCount-- > 0) {
                continue;
            }

            int y = lineHeight * n++;

            context.fill((n % 2 == 0) ? 50 : 40);
            context.noStroke();
            context.rect(0, y, context.width, lineHeight);

            context.fill(255);
            context.textAlign(LEFT, CENTER);

            float x = 40;
            String[] partials = msg.split("\t");
            for (String partial : partials) {
                VisualUtils.text(context, partial, x, y + lineHeight / 2);
                float msgWidth = context.textWidth(partial);
                x += (msgWidth - (msgWidth % tabSize) + tabSize);
            }
        }
        VisualUtils.drawScrollBar(context, messages.size(), visibleLines, scroll);
    }

    @Override
    public void mouseWheel(int count) {
        scroll += count;
    }

}