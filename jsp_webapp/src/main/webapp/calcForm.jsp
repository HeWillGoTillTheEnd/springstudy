<%--
  Created by IntelliJ IDEA.
  User: Legion
  Date: 2023-10-18
  Time: 오후 4:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>계산기</title>
</head>
<body>
<form action="/calc" method="post">
    <input type="text" name="n1">
    <select name="op">
        <option>+</option>
        <option>-</option>
        <option>/</option>
        <option>*</option>
    </select>
    <input type="text" name="n2">
    <input type="submit" name="계산">
</form>

</body>
</html>
