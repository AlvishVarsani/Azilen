<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Expense List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            display: flex;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 1200px;
        }
        .sidebar {
            width: 200px;
            padding: 10px;
            border-right: 1px solid #ccc;
            margin-right: 20px;
        }
        .sidebar button {
            display: block;
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            text-align: left;
        }
        .sidebar button a {
            text-decoration: none;
            color: white;
        }
        .main-content {
            flex-grow: 1;
            padding: 10px;
        }
        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 20px;
            margin-bottom: 20px;
            border-bottom: 1px solid #ccc;
        }
        h2 {
            color: #333;
            margin: 0;
        }
        .error-messages {
            color: red;
            margin-bottom: 15px;
            font-size: 14px;
        }
        .filter-form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            align-items: flex-end;
            margin-bottom: 20px;
        }
        .filter-form label {
            display: block;
            text-align: left;
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 14px;
        }
        .filter-form input, .filter-form select, .filter-form button {
            padding: 8px;
            margin: 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .filter-form input, .filter-form select {
            width: 150px;
        }
        .filter-form button {
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
            padding: 8px 15px;
            width: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .no-expense {
            color: red;
            margin-top: 10px;
            font-weight: bold;
        }
        .pagination {
            margin-top: 20px;
        }
        .pagination a {
            padding: 10px 15px;
            text-decoration: none;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
            margin-right: 5px;
        }
        .pagination a.disabled {
            background-color: #ccc;
            pointer-events: none;
        }
        .username {
            font-size: 22px;
            font-weight: bold;
            color: #333;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <button type="button"><a href="add-expenses">Add Expenses</a></button>
            <button type="button"><a href="category">Category</a></button>
            <button type="button"><a href="/download-report">Reports</a></button>
        </div>
        <div class="main-content">
            <header>
                <div>
                    <h2>Dashboard</h2>
                </div>
                <div class="profile-icon">
                    <a href="/profile">
                        <span class="username">${user.username}</span>
                    </a>
                </div>
            </header>

            <!-- Display Model Errors -->
            <c:if test="${not empty error}">
                <div class="error-messages">
                    <c:forEach var="error" items="${error}">
                        <p>${error}</p>
                    </c:forEach>
                </div>
            </c:if>

            <form action="/expenses" modelAttribute="filterDto" method="get" class="filter-form">
                <div>
                    <label>Search:</label>
                    <input type="text" name="searchTitle" placeholder="Enter at least 3 characters" value="${filter.searchTitle}">
                    <form:errors path="searchTitle" cssClass="error-message"/>

                </div>
                <div>
                    <label>Min Amount:</label>
                    <input type="number" name="minAmount" step="0.01" value="${filter.minAmount}">
                    <form:errors path="minAmount" cssClass="error-message"/>

                </div>
                <div>
                    <label>Max Amount:</label>
                    <input type="number" name="maxAmount" step="0.01" value="${filter.maxAmount}">
                    <form:errors path="maxAmount" cssClass="error-message"/>

                </div>
                <div>
                    <label>Start Date:</label>
                    <input type="date" name="startDate" value="${filter.startDate}">
                    <form:errors path="startDate" cssClass="error-message"/>

                </div>
                <div>
                    <label>End Date:</label>
                    <input type="date" name="endDate" value="${filter.endDate}">
                    <form:errors path="endDate" cssClass="error-message"/>

                </div>
                <div>
                    <label>Category:</label>
                    <select name="categoryId">
                        <option value="">Select Category</option>
                        <c:forEach var="entry" items="${categoryMap}">
                            <option value="${entry.key}" ${entry.key == filter.categoryId ? 'selected' : ''}>${entry.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <input type="hidden" name="filtered" value="true">
                    <button type="submit">Apply Filters</button>
                </div>
            </form>

            <!-- Clear filters button -->
            <c:if test="${filter.filtered}">
                <a href="/expenses?filtered=false" style="text-decoration: none; padding: 5px 10px; background-color: #f44336; color: white; border-radius: 4px; display: inline-block; margin-top: 10px;">
                    Clear Filters
                </a>
            </c:if>

            <!-- Download report form -->
            <c:if test="${not empty expenses}">
                <form action="/download-report" method="get">
                    <input type="hidden" name="minAmount" value="${filter.minAmount}">
                    <input type="hidden" name="maxAmount" value="${filter.maxAmount}">
                    <input type="hidden" name="startDate" value="${filter.startDate}">
                    <input type="hidden" name="endDate" value="${filter.endDate}">
                    <input type="hidden" name="categoryId" value="${filter.categoryId}">
                    <input type="hidden" name="filtered" value="${filter.filtered != null ? filter.filtered : 'false'}">
                    <button type="submit" style="margin-top: 10px;">Download Report</button>
                </form>
            </c:if>

            <br><br>

            <!-- Display Expense Table -->
            <c:choose>
                <c:when test="${empty expenses}">
                    <div class="no-expense">No expenses available.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Sr. No.</th>
                                <th>Title</th>
                                <th>Details</th>
                                <th>Amount</th>
                                <th>Date</th>
                                <th>Category</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="expense" items="${expenses}" varStatus="loop">
                                <tr>
                                    <td>${(currentPage * 10) + loop.index + 1}</td>
                                    <td>${expense.title}</td>
                                    <td>${expense.details}</td>
                                    <td>${expense.amount}</td>
                                    <td>${expense.date}</td>
                                    <td>${categoryMap[expense.categoryId]}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination Controls -->
                    <div class="pagination">
                        <c:if test="${currentPage > 0}">
                            <a href="expenses?page=${currentPage - 1}&filtered=${filter.filtered}&minAmount=${filter.minAmount}&maxAmount=${filter.maxAmount}&startDate=${filter.startDate}&endDate=${filter.endDate}&categoryId=${filter.categoryId}">
                                Previous
                            </a>
                        </c:if>

                        <c:forEach var="i" begin="0" end="${totalPages - 1}">
                            <a href="expenses?page=${i}&filtered=${filter.filtered}&minAmount=${filter.minAmount}&maxAmount=${filter.maxAmount}&startDate=${filter.startDate}&endDate=${filter.endDate}&categoryId=${filter.categoryId}"
                               class="${i == currentPage ? 'disabled' : ''}">
                                ${i + 1}
                            </a>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages - 1}">
                            <a href="expenses?page=${currentPage + 1}&filtered=${filter.filtered}&minAmount=${filter.minAmount}&maxAmount=${filter.maxAmount}&startDate=${filter.startDate}&endDate=${filter.endDate}&categoryId=${filter.categoryId}">
                                Next
                            </a>
                        </c:if>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>