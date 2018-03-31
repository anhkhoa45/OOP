<%-- 
    Document   : LoginPage
    Created on : Mar 17, 2018, 9:53:39 PM
    Author     : Ngoc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="LoginServlet">
        <table>
        <tr>
        <td>LOGIN INFORMATION</td>
        <td></td>
        </tr>
        <tr>
        <td>Username : </td>
        <td><input type="text" name="txtUsername"></td>
        </tr>
        <tr>
        <td>Password : </td>
        <td><input type="password" name="txtPassword"></td>
        </tr>
        <tr>
        <td></td>
        <td><input type="Submit" value="Login"></td>
        </tr>
        </table>
        </form>
        <h1>Hello World!</h1>
    </body>
</html>
