<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.Models.Livre" %>
<%@ page import="com.example.bibliotheque.Models.Exemplaire" %>
<%@ page import="com.example.bibliotheque.Models.Pret" %>
<%@ page import="com.example.bibliotheque.Models.Prolongement" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <title>Effectuer un prêt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background-color: #f0f2f5;
            min-height: 100vh;
        }

        .main-content {
            margin-left: 280px;
            padding: 2rem;
            width: calc(100% - 280px);
        }

        .form-container {
            background-color: #ffffff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            margin: 0 auto;
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
        input[type="number"],
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
        input[type="number"]:focus,
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

        .back-link {
            display: block;
            text-align: center;
            margin-top: 1rem;
            color: #007bff;
            text-decoration: none;
            font-size: 0.9rem;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        @media (max-width: 992px) {
            .main-content {
                margin-left: 0;
                width: 100%;
            }
            
            .sidebar {
                display: none;
            }
        }

        @media (max-width: 480px) {
            .form-container {
                padding: 1.5rem;
            }

            h2 {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <!-- Inclusion du header (sidebar) -->
    <jsp:include page="../inc/header.jsp" />

    <!-- Contenu principal -->
    <div class="main-content">
        <div class="form-container">
            <h2>Liste des Prets</h2>
                <table>
                    <tr>
                        <th>Livre(Exemplaire)</th>
                        <th>Date de retour prevue</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                    <%
                        List<Pret> prets = (List<Pret>)request.getAttribute("listPrets");
                        if (prets != null) {
                            for (Pret pret : prets) {
                                String statut = pret.getDateRetourPrevue().toEpochDay()-LocalDate.now().toEpochDay()>0 ?"dans les temps":"en retard";
                                %>
                                    <tr>
                                        <td><%= pret.getExemplaire().getLivre().getNom().concat(pret.getExemplaire().getId().toString()) %></td>
                                        <td><%= pret.getDateRetourPrevue() %></td>
                                        <td><%= statut %></td>
                                        <td>
                                            <a href="/prolongement/demand/<%= pret.getId() %>" onclick=confirm("Voulez-vous vraiment prolonger ce pret?")>Prolonger?</a>
                                        </td>
                                    </tr>      
                                <%
                            }
                        }
                    %>
                </table>
            <%
                if (request.getAttribute("error") != null) {
            %>
                    <p class="error-message"><%= request.getAttribute("error").toString() %></p>
            <%
                }
            %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>