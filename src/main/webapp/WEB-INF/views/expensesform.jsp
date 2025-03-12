<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Expense Form</title>
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
        .form-input, .form-select {
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
            width: 100%;
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
        <h2>Add Expense</h2>

        <!-- Display error message if any -->
        <c:if test="${not empty error}">
            <p class="error-messages">${error}</p>
        </c:if>

        <!-- Display success message -->
        <c:if test="${not empty successMsg}">
            <p class="success-message">${successMsg}</p>

        </c:if>

        <!-- Expense Form -->
        <form action="/save-expenses" method="post">
            <label>Title:</label>
            <input type="text" name="title" class="form-input" >

            <label>Details:</label>
            <input type="text" name="details" class="form-input" >

            <label>Amount:</label>
            <input type="number" step="0.01" name="amount" class="form-input" >

            <label>Date:</label>
            <input type="date" name="date" class="form-input" >

            <label>Category:</label>
            <select name="categoryId" class="form-select" >
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.categoryName}</option>
                </c:forEach>
            </select>

            <button type="submit">Add Expense</button>
        </form>

        <!-- Back to Expenses Button -->
        <a href="/expenses" class="back-button">Back to Expenses</a>
    </div>

</body>
</html>
