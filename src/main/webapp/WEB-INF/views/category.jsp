<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Category Dashboard</title>
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
        .action-buttons {
            margin-bottom: 20px;
        }
        .action-buttons a {
            padding: 10px 15px;
            text-decoration: none;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
            margin-right: 10px;
            display: inline-block;
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
        .no-category {
            color: #ff0000;
            font-weight: bold;
            margin-top: 20px;
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
    </style>
</head>
<body>
    <h2>Category List</h2>

    <div class="action-buttons">
        <a href="/add-category">Add Category</a>
        <a href="/expenses">Back to Expenses</a>
    </div>

    <c:choose>
        <c:when test="${empty categories}">
            <div class="no-category">No categories available.</div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>Index</th>
                        <th>Category Name</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${categories}" varStatus="loop">
                        <tr>
                            <td>${(currentPage * 10) + loop.index + 1}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.description}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="pagination">
                <c:if test="${hasPrevious}">
                    <a href="/category?page=${currentPage - 1}">Previous</a>
                </c:if>
                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                    <a href="/category?page=${i}" class="${i == currentPage ? 'disabled' : ''}">${i + 1}</a>
                </c:forEach>
                <c:if test="${hasNext}">
                    <a href="/category?page=${currentPage + 1}">Next</a>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>

    <c:if test="${not empty successMsg}">
        <script>
            alert("${successMsg}");
            history.replaceState({}, '', '/category');
        </script>
    </c:if>
</body>
</html>
