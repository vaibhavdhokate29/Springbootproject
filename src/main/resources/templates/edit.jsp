<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h1>Edit User</h1>
<form action="/user" method="post">
    <input type="hidden" name="id" value="${user.id}">
    Name: <input type="text" name="name" value="${user.name}"><br>
    Email: <input type="text" name="email" value="${user.email}"><br>
    <input type="submit" value="Update User">
</form>
</body>
</html>
