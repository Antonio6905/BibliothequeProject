<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.Models.Livre" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Effectuer un pret</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .login-container {
            background-color: #ffffff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 1.5rem;
            font-size: 1.8rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            color: #555;
            font-size: 0.9rem;
            margin-bottom: 0.5rem;
        }

        input[type="text"],
        input[type="password"],
        select {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 1rem;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        input[type="text"]:focus,
        input[type="password"]:focus,
        select:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
        }

        button {
            width: 100%;
            padding: 0.75rem;
            background-color: #007bff;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #0056b3;
        }

        .error-message {
            color: #d32f2f;
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
        }

        .inscription-link {
            display: block;
            text-align: center;
            margin-top: 1rem;
            color: #007bff;
            text-decoration: none;
            font-size: 0.9rem;
        }

        .inscription-link:hover {
            text-decoration: underline;
        }

        @media (max-width: 480px) {
            .login-container {
                padding: 1.5rem;
                margin: 0 1rem;
            }

            h2 {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Effectuer un prêt</h2>
        <form action="/pret" method="post">
            <div class="form-group">
                <label for="numero">Numéro</label>
                <input type="number" id="numero" name="numero" min="1" step="1" required/>
            </div>
            <div class="form-group">
                <label for="livre">Livre</label>
                <select name="livreId" id="livre" required>
                    <option value="" disabled selected>Sélectionnez un livre</option>
                    <%
                        List<Livre> livres = (List<Livre> )request.getAttribute("listLivres");
                        if (livres != null) {
                            for (Livre livre : livres) {
                    %>
                                <option value="<%= livre.getId() %>"><%= livre.getNom() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div>
                <button type="submit">Valider</button>
            </div>
            <a href="/admin" class="inscription-link">Retour</a>
        </form>
        <%
            if (request.getAttribute("error") != null) {
        %>
                <p class="error-message"><%= request.getAttribute("error").toString() %></p>
        <%
            }
        %>
    </div>
</body>
</html>