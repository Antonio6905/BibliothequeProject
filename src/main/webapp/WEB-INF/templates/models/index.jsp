<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Gestion Pêche</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            min-height: 100vh;
            background-color: #f8f9fa;
        }
        .sidebar {
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            width: 240px;
            background-color: #0d6efd;
            color: white;
            z-index: 1000;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
            transition: background 0.2s ease-in-out;
        }
        .sidebar a:hover {
            background-color: rgba(255,255,255,0.1);
            padding-left: 10px;
        }
        .main {
            margin-left: 240px;
            padding: 30px;
        }
        .card-icon {
            font-size: 2rem;
            opacity: 0.8;
        }
        .card {
            transition: transform 0.2s;
        }
        .card:hover {
            transform: scale(1.02);
        }
    </style>
</head>
<body>

<jsp:include page="inc/header.jsp" />

<div class="main">
    <div class="container-fluid">
        <h1 class="mb-4"><i class="bi bi-speedometer2 me-2"></i>Tableau de bord</h1>

        <div class="row g-4">
            <div class="col-md-4">
                <div class="card bg-primary text-white shadow">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h5 class="card-title">Total Bateaux</h5>
                            <p class="card-text fs-4"><%= request.getAttribute("totalBateaux") != null ? request.getAttribute("totalBateaux") : 4 %></p>
                        </div>
                        <i class="bi bi-ship card-icon"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card bg-success text-white shadow">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h5 class="card-title">Captures du mois</h5>
                            <p class="card-text fs-4"><%= request.getAttribute("capturesMois") != null ? request.getAttribute("capturesMois") : "28.6" %> t</p>
                        </div>
                        <i class="bi bi-basket3-fill card-icon"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card bg-warning text-dark shadow">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h5 class="card-title">Commandes en attente</h5>
                            <p class="card-text fs-4"><%= request.getAttribute("commandesAttente") != null ? request.getAttribute("commandesAttente") : 7 %></p>
                        </div>
                        <i class="bi bi-clock-history card-icon"></i>
                    </div>
                </div>
            </div>
        </div>

        
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
