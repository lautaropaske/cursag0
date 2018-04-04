<%--
  Created by IntelliJ IDEA.
  User: agustin
  Date: 4/3/18
  Time: 4:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>The user: <%=request.getRemoteUser()%> has logged out</h1>
<% session.invalidate(); %>
You have been logged out of the SecurityFilter example application.<p>
    This operation was achieved with a simple call to <code>session.invalidate()</code>.
</body>
</html>
