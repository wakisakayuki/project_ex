<%@ tag body-content="empty" import="util.HtmlEscaper" %>
<%@ attribute name="value" required="false" type="java.lang.Object" %>
<% out.write(HtmlEscaper.escape(value)); %>
