<%--
  Created by IntelliJ IDEA.
  User: Legion
  Date: 2023-10-19
  Time: 오후 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<% Long result = (Long)request.getAttribute("result"); %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
</head>
<body>
    <h2>계산 결과-컨트롤러</h2>
    <hr>
    결과 : EL${result}
</body>
</html>
