<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Livre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Nouveau Livre</h2>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="/livre">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>
        
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>

        <div class="mb-3">
            <label for="nbExemplaire" class="form-label">NbExemplaire</label>
            <textarea class="form-control" id="nbExemplaire" name="nbExemplaire" value="1" min="1" step="1" rows="3"></textarea>
        </div>
        
        <div class="mb-3">
            <label for="typeLivre" class="form-label">Type de livre</label>
            <select class="form-select" id="typeLivre" name="typeLivre.id" required>
                <option value="">Sélectionnez un type</option>
                <c:forEach items="${typesLivre}" var="type">
                    <option value="${type.id}">${type.nomType}</option>
                </c:forEach>
            </select>
        </div>
        
        <div class="mb-3">
            <label for="dateEdition" class="form-label">Date d'édition</label>
            <input type="date" class="form-control" id="dateEdition" name="dateEdition">
        </div>
        
        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="/livre" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html>