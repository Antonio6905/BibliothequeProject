<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.Models.Utilisateur" %>
<!DOCTYPE html>
<html>
<head>
    <title>Accueil - Bibliothèque</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100 min-h-screen">
    <div class="container mx-auto p-4">
        <div class="bg-white p-6 rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold text-gray-800 mb-4">
                Connecte en tant que bibliothecaire.
            </h2>
            <p class="text-gray-600 mb-4">Vous êtes connecté à la gestion de la bibliothèque.</p>
            <a href="/livre" class="btn btn-primary mb-3">Voir les livres</a>
            <a href="/admin/pret" class="btn btn-primary mb-3">Voir les prets en cours</a>
            <a href="/admin/reservation" class="btn btn-primary mb-3">Voir les reservations a valider</a>
            <a href="/admin/prolongement" class="btn btn-primary mb-3">Voir les prolongements a valider</a>
            <a href="/admin/adherent" class="btn btn-primary mb-3">Voir details Adherent</a>

        </div>
    </div>
</body>
</html>