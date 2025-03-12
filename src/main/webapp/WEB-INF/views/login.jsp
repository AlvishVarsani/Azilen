<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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
           width: 320px; /* Increased width for better alignment */
           text-align: center;
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
           box-sizing: border-box; /* Ensures proper width */
       }

       button {
           background-color: #28a745;
           color: white;
           border: none;
           cursor: pointer;
           font-weight: bold;
       }
       .error-message {
           color: red;
           margin-top: 10px;
       }
       .success-message {
                  color: green;
                  margin-top: 10px;
              }

       .register-link {
           margin-top: 15px;
           font-size: 14px;
       }

    </style>
    <script>
        window.onload = function () {
            var success = "${success}";
            if (success === "true") {
                history.replaceState({}, '', '/expenses');
            } else {
                history.replaceState({}, '', '/');
            }
        };
    </script>
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <form action="save-login" method="post">
            <label>Username:</label>
            <input type="text" name="username"  />

            <label>Password:</label>
            <input type="password" name="password"  />

            <button type="submit">Login</button>
        </form>

        <!-- Error message from model -->
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>
         <c:if test="${not empty successMsg}">
                    <p class="success-message">${successMsg}</p>
                </c:if>

        <!-- Register link -->
        <p class="register-link">Don't have an account? <a href="/register">Register here</a></p>
    </div>
</body>
</html>
