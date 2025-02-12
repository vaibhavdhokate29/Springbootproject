<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create User</title>
</head>
<body>
<h1>Create User</h1>
<form action="/user" method="post">
    Name: <input type="text" name="name"><br>
    Email: <input type="text" name="email"><br>
    <input type="submit" value="Create User">
</form>
</body>
</html>
