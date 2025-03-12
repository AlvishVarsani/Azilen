<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 500px;
            text-align: left;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 14px;
        }
        .form-input, .form-textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            width: 100%;
            margin-bottom: 10px;
        }
        .back-button {
            background-color: #007bff;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            padding: 10px;
            color: white;
            border-radius: 4px;
        }
        .error-messages {
            color: red;
            margin-bottom: 15px;
            font-size: 14px;
        }
        .success-message {
            color: green;
            font-weight: bold;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create Category</h2>

        <!-- Display error message if any -->
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <!-- Display success message -->
        <c:if test="${not empty successMsg}">
            <p class="success-message">${successMsg}</p>

        </c:if>

        <!-- Form using Spring Form Tags -->
        <form:form modelAttribute="categoryDto" action="/save-category" method="post">
            <label>Category Name:</label>
            <form:input path="categoryName" cssClass="form-input" />
            <form:errors path="categoryName" cssClass="error-messages" />

            <label>Description:</label>
            <form:textarea path="description" cssClass="form-textarea" />
            <form:errors path="description" cssClass="error-messages" />

            <button type="submit">Submit</button>
        </form:form>

        <!-- Back to Dashboard Button -->
        <a href="/expenses" class="back-button">Back to Dashboard</a>
    </div>
</body>
</html>
