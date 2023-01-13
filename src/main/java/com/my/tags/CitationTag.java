package com.my.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CitationTag extends SimpleTagSupport {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Method prints out 42: (The answer to life, the universe and everything) with text using tag
     *
     * @throws IOException if exception occur during printing
     */

    @Override
    public void doTag() throws IOException {
        getJspContext().getOut().print("42: " + text);
    }
}
