JSP View

# Create the result.jsp file below

<%@ page import="java.util.*" %>

<html>
<body>
<h1 align="center">Coffee Recommandation JSP View</h1>
<p>

<%
  List styles = (List) request.getAttribute("dancers");
  Iterator it = styles.iterator();
  while(it.hasNext()) {
    out.print("<br>try: " + it.next());
  }
%>

</body>
</html>