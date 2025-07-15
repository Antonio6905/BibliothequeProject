<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Livres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        #livreDetails {
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            display: none;
        }
        .exemplaire-status {
            display: inline-block;
            width: 15px;
            height: 15px;
            border-radius: 50%;
            margin-right: 5px;
        }
        .disponible {
            background-color: green;
        }
        .indisponible {
            background-color: red;
        }
    </style>
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
                <td>
                    <a href="#" class="livre-link" data-livre-id="${livre.id}">${livre.nom}</a>
                </td>
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
    <a href="/admin" class="btn btn-primary mb-3">Retour</a>
    
    <!-- Container pour afficher les détails du livre -->
    <div id="livreDetails" class="mt-4">
        <h3>Détails du livre</h3>
        <div id="livreContent">
            <p class="debug" id="debugInfo">Aucune donnée chargée</p>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {

        // Fonction principale
        document.querySelectorAll('.livre-link').forEach(function(link) {
            link.addEventListener('click', async function(e) {
                e.preventDefault();
                const livreId = this.dataset.livreId;
                
                try {
                    const response = await fetch('/api/livre/' + livreId);
                    if (!response.ok) throw new Error('Erreur HTTP: ' + response.status);
                    
                    const data = await response.json();
                    console.log("Données API reçues:", data);
                    afficherDetailsLivre(data);
                } catch (error) {
                    console.error("Erreur fetch:", error);
                    document.getElementById('debugInfo').textContent = 
                        "Erreur: " + error.message;
                    document.getElementById('livreDetails').style.display = 'block';
                }
            });
        });

        // Fonction d'affichage commune
        function afficherDetailsLivre(data) {
            console.log("Affichage des données:", data);
            
            if (!data) {
                document.getElementById('debugInfo').textContent = "Aucune donnée reçue";
                document.getElementById('livreDetails').style.display = 'block';
                return;
            }

            let html = '<p><strong>Nom:</strong> <span id="livreNom">' + data.nom + '</span></p>' +
                        '<p><strong>Description:</strong> <span id="livreDesc">' + data.description + '</span></p>' +
                        '<p><strong>Date d\'édition:</strong> <span id="livreDate">' + data.dateEdition + '</span></p>';

            if (data.exemplaire && data.exemplaire.length > 0) {
                html += '<h4>Exemplaires:</h4><ul class="list-group" id="exemplairesList">';
                
                data.exemplaire.forEach(function(ex) {
                    const statusClass = ex.disponible ? 'disponible' : 'indisponible';
                    const statusText = ex.disponible ? 'Disponible' : 'Indisponible';
                    
                    html += '<li class="list-group-item">' +
                            '<span class="exemplaire-status ' + statusClass + '"></span>' +
                            'Exemplaire #' + ex.numero + ' - ' + statusText +
                            '</li>';
                });
                
                html += '</ul>';
            } else {
                html += '<p>Aucun exemplaire disponible</p>';
            }

            document.getElementById('livreContent').innerHTML = html;
            document.getElementById('livreDetails').style.display = 'block';
        }
    });
</script>
</body>
</html>