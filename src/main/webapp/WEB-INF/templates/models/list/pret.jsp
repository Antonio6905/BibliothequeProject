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
    <title>Mes prêts</title>
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
            <h2>Mes prêts</h2>
            
            <% if (request.getAttribute("error") != null) { %>
                <p class="error-message"><%= request.getAttribute("error").toString() %></p>
            <% } %>
            
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Exemplaire</th>
                        <th>Date de retour prévue</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Pret> prets = (List<Pret>)request.getAttribute("listPrets");
                        if (prets != null && !prets.isEmpty()) {
                            for (Pret pret : prets) {
                                String statut = pret.getDateRetourPrevue().toEpochDay()-LocalDate.now().toEpochDay()>0 
                                    ? "dans les temps" 
                                    : "en retard";
                                String statutClass = statut.equals("dans les temps") ? "status-ontime" : "status-late";
                                boolean canExtend = pret.getProlongement() == null 
                                    && pret.getDateRetourPrevue().toEpochDay()-LocalDate.now().toEpochDay() > 0;
                    %>
                        <tr>
                            <td><%= pret.getExemplaire().getLivre().getNom() %></td>
                            <td>#<%= pret.getExemplaire().getId() %></td>
                            <td><%= pret.getDateRetourPrevue() %></td>
                            <td class="<%= statutClass %>"><%= statut %></td>
                            <td>
                                <% if (canExtend) { %>
                                    <a href="/prolongement/demand/<%= pret.getId() %>" class="btn-prolong" 
                                       onclick="return confirm('Voulez-vous vraiment demander un prolongement pour ce prêt ?')">
                                        <i class="bi bi-clock-history"></i> Prolonger
                                    </a>
                                <% } else if (pret.getProlongement() != null) { %>
                                    <button class="btn-prolong" disabled>
                                        <i class="bi bi-check-circle"></i> Déjà prolongé
                                    </button>
                                <% } else { %>
                                    <button class="btn-prolong" disabled>
                                        <i class="bi bi-exclamation-circle"></i> Non prolongeable
                                    </button>
                                <% } %>
                            </td>
                        </tr>      
                    <% 
                            }
                        } else { 
                    %>
                        <tr>
                            <td colspan="5" style="text-align: center;">Aucun prêt à afficher</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            <a href="/home" class="btn btn-primary mb-3">Retour</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>