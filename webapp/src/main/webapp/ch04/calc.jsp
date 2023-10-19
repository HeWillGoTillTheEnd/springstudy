<%--
  Created by IntelliJ IDEA.
  User: Legion
  Date: 2023-10-19
  Time: 오후 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
    <%
        int n1 = Integer.parseInt(request.getParameter("n1"));
        int n2 = Integer.parseInt(request.getParameter("n2"));
        String op = request.getParameter("op");

        long result = 0L;
        switch (op){
            case "+" : result = n1 + n2; break;
            case "-" : result = n1 - n2; break;
            case "*" : result = n1 * n2; break;
            case "/" : result = n1 / n2; break;
        }
    %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h2>계산 결과 JSP</h2>
    <hr>
    결과 : <%= result %>
</body>
</html>
