<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.Models.Reservation" %>
<%@ page import="com.example.bibliotheque.Models.Utilisateur" %>
<%@ page import="com.example.bibliotheque.Models.Exemplaire" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion des réservations</title>
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

        .action-buttons {
            display: flex;
            gap: 8px;
        }

        .btn-validate {
            background-color: #28a745;
            color: white;
            padding: 6px 12px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            text-decoration: none;
            font-size: 0.8rem;
        }

        .btn-validate:hover {
            background-color: #218838;
        }

        .btn-reject {
            background-color: #dc3545;
            color: white;
            padding: 6px 12px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            text-decoration: none;
            font-size: 0.8rem;
        }

        .btn-reject:hover {
            background-color: #c82333;
        }

        .status-pending {
            color: #ffc107;
            font-weight: bold;
        }

        .status-validated {
            color: #28a745;
            font-weight: bold;
        }

        .status-rejected {
            color: #dc3545;
            font-weight: bold;
        }

        .status-expired {
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

    <!-- Contenu principal -->
    <div class="main-content">
        <div class="card-container">
            <h2>Gestion des réservations</h2>
            
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
                        <th>Date demande</th>
                        <th>Date expiration</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Reservation> reservations = (List<Reservation>)request.getAttribute("listReservations");
                        if (reservations != null && !reservations.isEmpty()) {
                            for (Reservation reservation : reservations) {
                                String dernierStatut = "non traite";
                                if (reservation.getSuivisStatut() != null && !reservation.getSuivisStatut().isEmpty()) {
                                    dernierStatut = reservation.getSuivisStatut().get(reservation.getSuivisStatut().size() - 1).getStatut();
                                }
                                
                                // Vérifier si la réservation est expirée
                                boolean isExpired = LocalDate.now().isAfter(reservation.getDateExpiration());
                                
                                String statutClass = "status-pending";
                                if (dernierStatut.equalsIgnoreCase("valide")) {
                                    statutClass = "status-validated";
                                } else if (dernierStatut.equalsIgnoreCase("non valide")) {
                                    statutClass = "status-rejected";
                                } else if (isExpired) {
                                    statutClass = "status-expired";
                                    dernierStatut = "expiré";
                                }
                    %>
                        <tr>
                            <td><%= reservation.getExemplaire().getLivre().getNom() %></td>
                            <td><%= reservation.getUtilisateur().getNom() %> <%= reservation.getUtilisateur().getPrenom() %></td>
                            <td><%= reservation.getDateDemande() %></td>
                            <td><%= reservation.getDateExpiration() %></td>
                            <td class="<%= statutClass %>"><%= dernierStatut %></td>
                            <td>
                                <% if (dernierStatut.equalsIgnoreCase("non traite") && !isExpired) { %>
                                    <div class="action-buttons">
                                        <a href="/reservation/response/<%= reservation.getId() %>/valide" class="btn-validate">
                                            <i class="bi bi-check-circle"></i> Valider
                                        </a>
                                        <a href="/reservation/response/<%= reservation.getId() %>/non valide" class="btn-reject">
                                            <i class="bi bi-x-circle"></i> Refuser
                                        </a>
                                    </div>
                                <% } else if (isExpired) { %>
                                    <span>Réservation expirée</span>
                                <% } else { %>
                                    <span>Action effectuée</span>
                                <% } %>
                            </td>
                        </tr>
                    <% 
                            }
                        } else { 
                    %>
                        <tr>
                            <td colspan="6" style="text-align: center;">Aucune réservation à afficher</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Confirmation avant validation/refus
        document.querySelectorAll('.btn-validate, .btn-reject').forEach(btn => {
            btn.addEventListener('click', function(e) {
                const action = this.classList.contains('btn-validate') ? 'valider' : 'refuser';
                if (!confirm(`Voulez-vous vraiment ${action} cette réservation ?`)) {
                    e.preventDefault();
                }
            });
        });
    </script>
</body>
</html>