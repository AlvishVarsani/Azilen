<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        .profile-container {
            margin-top: 20px;
        }
        .profile-container table {
            border-collapse: collapse;
            width: 50%;
        }
        .profile-container th, .profile-container td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        .profile-container th {
            background-color: #f2f2f2;
        }
        .btn-group {
            margin-top: 20px;
        }
        .btn-group a {
            display: inline-block;
            padding: 10px 15px;
            margin-right: 10px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .btn-group a:hover {
            background-color: #0056b3;
        }
        .logout-btn {
            display: inline-block;
            padding: 10px 15px;
            background-color: #dc3545;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .logout-btn:hover {
            background-color: #c82333;
        }
        .back-link {
            margin-top: 20px;
            display: inline-block;
            color: #007bff;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <h2>User Profile</h2>
    <div class="profile-container">
        <table>
            <tr>
                <th>First Name</th>
                <td>${user.userDetails.firstName}</td>
            </tr>
            <tr>
                <th>Last Name</th>
                <td>${user.userDetails.lastName}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${user.userDetails.email}</td>
            </tr>
        </table>
    </div>
    <div class="btn-group">
        <a href="/profile/edit">Change User Details</a>
        <a href="/profile/change-password">Change Password</a>
        <form action="/profile/logout" method="post" style="display:inline;">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
    </div>
    <br>
    <a class="back-link" href="/expenses">Back to Dashboard</a>
</body>
</html>
