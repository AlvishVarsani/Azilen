<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .form-container {
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            width: 50%;
            max-width: 400px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            text-align: left;
        }
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .error {
            color: red;
            font-size: 14px;
            text-align: left;
            margin-bottom: 10px;
        }
        .message {
            color: green;
            font-weight: bold;
            margin-bottom: 10px;
        }
        input[type="submit"] {
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-weight: bold;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .back-link {
            display: inline-block;
            margin-top: 10px;
            color: #007bff;
            text-decoration: none;
            font-size: 14px;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Change Password</h2>

        <!-- Display error message -->
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <!-- Display success message -->
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>

        <!-- Change Password Form -->
        <form:form modelAttribute="changePasswordDTO" action="change-password" method="post">
            <div>
                <form:label path="oldPassword">Old Password:</form:label>
                <form:password path="oldPassword" />
                <form:errors path="oldPassword" cssClass="error" />
            </div>

            <div>
                <form:label path="newPassword">New Password:</form:label>
                <form:password path="newPassword" />
                <form:errors path="newPassword" cssClass="error" />
            </div>

            <div>
                <form:label path="confirmPassword">Confirm New Password:</form:label>
                <form:password path="confirmPassword" />
                <form:errors path="confirmPassword" cssClass="error" />
            </div>

            <input type="submit" value="Change Password" />
        </form:form>

        <!-- Back Link -->
        <a href="/profile" class="back-link">Back to Profile</a>
    </div>
</body>
</html>
