<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.Models.Pret" %>
<%@ page import="com.example.bibliotheque.Models.Exemplaire" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des prêts - Admin</title>
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

        .success-message {
            color: #388e3c;
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
        }

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

        .btn-return {
            background-color: #17a2b8;
            color: white;
            padding: 6px 12px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            text-decoration: none;
            font-size: 0.8rem;
        }

        .btn-return:hover {
            background-color: #138496;
        }

        .status-ontime {
            color: #28a745;
            font-weight: bold;
        }

        .status-late {
            color: #dc3545;
            font-weight: bold;
        }

        .status-returned {
            color: #6c757d;
            font-weight: bold;
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
    <!-- Inclusion du header (sidebar) -->
    <jsp:include page="../inc/header.jsp" />

    <!-- Contenu principal -->
    <div class="main-content">
        <div class="card-container">
            <h2>Gestion des prêts</h2>
            
            <% if (request.getAttribute("message") != null) { %>
                <p class="success-message"><%= request.getAttribute("message") %></p>
            <% } %>
            
            <% if (request.getAttribute("error") != null) { %>
                <p class="error-message"><%= request.getAttribute("error") %></p>
            <% } %>
            
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Utilisateur</th>
                        <th>Date prêt</th>
                        <th>Date retour prévue</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Pret> prets = (List<Pret>)request.getAttribute("listPrets");
                        if (prets != null && !prets.isEmpty()) {
                            for (Pret pret : prets) {
                                boolean isReturned = pret.getRetour() != null;
                                boolean isLate = !isReturned && LocalDate.now().isAfter(pret.getDateRetourPrevue());
                                String status = isReturned ? "retourné" : 
                                               isLate ? "en retard" : "en cours";
                                String statusClass = isReturned ? "status-returned" : 
                                                   isLate ? "status-late" : "status-ontime";
                    %>
                        <tr>
                            <td><%= pret.getExemplaire().getLivre().getNom() %></td>
                            <td><%= pret.getUtilisateur().getNom() %> <%= pret.getUtilisateur().getPrenom() %></td>
                            <td><%= pret.getDatePret().toLocalDate() %></td>
                            <td><%= pret.getDateRetourPrevue() %></td>
                            <td class="<%= statusClass %>"><%= status %></td>
                            <td>
                                <% if (!isReturned) { %>
                                    <a href="/retour/<%= pret.getId() %>" class="btn-return" 
                                       onclick="return confirm('Confirmer le retour de ce prêt ?')">
                                        <i class="bi bi-arrow-return-left"></i> Enregistrer retour
                                    </a>
                                <% } else { %>
                                    <span>Retour enregistré le <%= pret.getRetour().getDateRemiseEffective().toLocalDate() %></span>
                                <% } %>
                            </td>
                        </tr>
                    <% 
                            }
                        } else { 
                    %>
                        <tr>
                            <td colspan="6" style="text-align: center;">Aucun prêt actif</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>