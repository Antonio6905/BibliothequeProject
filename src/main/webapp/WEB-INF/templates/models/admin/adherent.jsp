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
    <label for="Adherent">Numero de l'adherent:</label>
    <input type="number" name="adherentId" id="adherentId" value="1" min="1" step="1" >
    <button id="submitButton">Voir</button>
    <a href="/admin" class="btn btn-primary mb-3">Retour</a>
    
    <!-- Container pour afficher les détails du livre -->
    <div id="livreDetails" class="mt-4">
        <h3>Détails de l'adherent</h3>
        <div id="livreContent">
            <p class="debug" id="debugInfo">Aucune donnée chargée</p>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {

        // Fonction principale
        document.getElementById("submitButton").addEventListener('click', async function(e) {
                e.preventDefault();
                const adherentId = document.getElementById("adherentId").value;
                
                try {
                    const response = await fetch('/api/adherent/' + adherentId);
                    if (!response.ok) throw new Error('Erreur HTTP: ' + response.status);
                    
                    const data = await response.json();
                    console.log("Données API reçues:", data);
                    afficherDetailsAdherent(data);
                } catch (error) {
                    console.error("Erreur fetch:", error);
                    document.getElementById('livreContent').innerHTML = 
                        "Erreur: " + error.message;
                    document.getElementById('livreDetails').style.display = 'block';
                }
            });
    });

        // Fonction d'affichage commune
        function afficherDetailsAdherent(data) {
            console.log("Affichage des données:", data);
            
            if (!data) {
                document.getElementById('debugInfo').textContent = "Aucune donnée reçue";
                document.getElementById('livreDetails').style.display = 'block';
                return;
            }

            let html = '<p><strong>Nom:</strong> <span >' + data.nom + '</span></p>' +
                        '<p><strong>Prenom:</strong> <span >' + data.prenom + '</span></p>' +
                        '<p><strong>Date de naissance:</strong> <span >' + data.dtn + '</span></p>' +
                        '<p><strong>Quota:</strong> <span >' + data.quota + '</span></p>' +
                        '<p><strong>Nb prets en cours:</strong> <span >' + data.nbPret + '</span></p>'+
                        '<p><strong>Sanctionne:</strong> <span >' + data.isSanctionne + '</span></p>' +
                        '<p><strong>Abonne:</strong> <span >' + data.isAbonne + '</span></p>';

            document.getElementById('livreContent').innerHTML = html;
            document.getElementById('livreDetails').style.display = 'block';
        }
</script>
</body>
</html>