<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Signup</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f4f4f4;
        }
        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 320px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
        }
        label {
            display: block;
            text-align: left;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input, button {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #28a745; /* Green color */
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }
        button:hover {
            background-color: #218838;
        }
        .error-message {
            color: red;
            margin-top: 10px;
        }
        .register-link {
            margin-top: 15px;
            font-size: 14px;
        }
        .name-container {
            display: flex;
            gap: 10px;
        }
        .name-container input {
            width: 48%;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>New to ETS?</h2>
        <form:form action="save-register" modelAttribute="registerDTO" method="post">
            <div class="name-container">
                <form:input path="firstName" placeholder="First Name"/>
                <form:input path="lastName" placeholder="Last Name"/>
            </div>
            <form:errors path="firstName" cssClass="error-message"/>
            <form:errors path="lastName" cssClass="error-message"/>

            <form:input path="username" placeholder="Username"/>
            <form:errors path="username" cssClass="error-message"/>

            <form:input path="email" placeholder="Email"/>
            <form:errors path="email" cssClass="error-message"/>

            <form:password path="password" placeholder="Password"/>
            <form:errors path="password" cssClass="error-message"/>

            <form:password path="confirmPassword" placeholder="Confirm Password"/>
            <form:errors path="confirmPassword" cssClass="error-message"/>

            <button type="submit">Register</button>
        </form:form>

        <!-- Display general error messages -->
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <!-- Link to login if already have an account -->
        <p class="register-link">Already have an account? <a href="/">Login here</a></p>
    </div>
</body>
</html>
