<%@ tag body-content="empty" %>
<%@ attribute name="value" required="false" type="java.lang.Object" %>
<%@ page import="util.HtmlEscaper" %><% out.write(HtmlEscaper.escape(value)); %>
