<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bibliotheque.Models.Utilisateur" %>
<!DOCTYPE html>
<html>
<head>
    <title>Accueil - Bibliothèque</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
    <div class="container mx-auto p-4">
        <div class="bg-white p-6 rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold text-gray-800 mb-4">
                Connecte en tant que bibliothecaire.
            </h2>
            <p class="text-gray-600 mb-4">Vous êtes connecté à la gestion de la bibliothèque.</p>
            <a href="/pret">Ajouter un pret</a>
        </div>
    </div>
</body>
</html>