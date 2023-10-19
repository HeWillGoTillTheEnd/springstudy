<%--
  Created by IntelliJ IDEA.
  User: Legion
  Date: 2023-10-19
  Time: 오후 3:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h2>상품 목록</h2>
    <hr>
<table border="1">
    <tr> <th>번호</th><th>상품</th><th>가격</th></tr>
    <c:forEach var="p" varStatus="i" items="${products}">
        <tr>
            <td>${i.count}</td>
            <td><a href="/pcontrol?action=info&id=${p.id}">${p.name}</a> </td>
            <td>${p.price}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
