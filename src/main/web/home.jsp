<%--
  Created by IntelliJ IDEA.
  User: agustin
  Date: 3/28/18
  Time: 10:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
      <title>Cursago</title>
    </head>
    <body>
    <h1>This is the home page.</h1>

    Welcome <i><%=request.getRemoteUser()%></i>, you are viewing a secure page.
    </body>
</html>
