<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.Models.Sanction" %>
<%@ page import="com.example.bibliotheque.Models.Utilisateur" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Sanctions</title>
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

        .card-container {
            background-color: #ffffff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            margin: 0 auto;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 1.5rem;
            font-size: 1.8rem;
        }

        .error-message {
            color: #d32f2f;
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
        }

        /* Style amélioré pour les tables */
        .styled-table {
            width: 100%;
            border-collapse: collapse;
            margin: 25px 0;
            font-size: 0.9em;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }

        .styled-table thead tr {
            background-color: #007bff;
            color: #ffffff;
            text-align: left;
        }

        .styled-table th,
        .styled-table td {
            padding: 12px 15px;
        }

        .styled-table tbody tr {
            border-bottom: 1px solid #dddddd;
        }

        .styled-table tbody tr:nth-of-type(even) {
            background-color: #f3f3f3;
        }

        .styled-table tbody tr:last-of-type {
            border-bottom: 2px solid #007bff;
        }

        .styled-table tbody tr:hover {
            background-color: #f1f7ff;
        }

        .status-ontime {
            color: #28a745;
            font-weight: bold;
        }

        .status-late {
            color: #dc3545;
            font-weight: bold;
        }

        .btn-prolong {
            background-color: #17a2b8;
            color: white;
            padding: 6px 12px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            text-decoration: none;
            font-size: 0.8rem;
        }

        .btn-prolong:hover {
            background-color: #138496;
        }

        .btn-prolong:disabled {
            background-color: #6c757d;
            cursor: not-allowed;
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

        @media (max-width: 768px) {
            .styled-table {
                display: block;
                overflow-x: auto;
            }
        }
    </style>
</head>
<body>

    <!-- Contenu principal -->
    <div class="main-content">
        <div class="card-container">
            <h2>All Sanction</h2>
            <form action="/admin/sanctions" method="get">
                <input type="date" name="date" id="date">
                <button type="submit">Voir</button>
            </form>
            
            <% if (request.getAttribute("error") != null) { %>
                <p class="error-message"><%= request.getAttribute("error").toString() %></p>
            <% } %>
            
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>DateDebut</th>
                        <th>DateFin</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Sanction> sanctions = (List<Sanction>)request.getAttribute("listSanctions");
                        if (sanctions != null && !sanctions.isEmpty()) {
                            for (Sanction sanction : sanctions) {
                                
                    %>
                        <tr>
                            <td><%= sanction.getUtilisateur().getNom() %></td>
                            <td>#<%= sanction.getDateDebutSanction() %></td>
                            <td><%= sanction.getDateFinSanction() %></td>
                        </tr>      
                    <% 
                            }
                        } else { 
                    %>
                        <tr>
                            <td colspan="5" style="text-align: center;">Aucunes sanctions à afficher</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            <a href="/admin" class="btn btn-primary mb-3">Retour</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>