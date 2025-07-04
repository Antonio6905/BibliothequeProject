<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Livres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Liste des Livres</h2>
    <a href="/livre/form" class="btn btn-primary mb-3">Ajouter un livre</a>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Type</th>
            <th>Date d'édition</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${livres}" var="livre">
            <tr>
                <td>${livre.id}</td>
                <td>${livre.nom}</td>
                <td>${livre.typeLivre.nomType}</td>
                <td>${livre.dateEdition}</td>
                <td>
                    <a href="/livre/delete/${livre.id}" class="btn btn-danger btn-sm" 
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce livre?')">
                        Supprimer
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>