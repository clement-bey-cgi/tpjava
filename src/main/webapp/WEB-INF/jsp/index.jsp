<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="tags/header.jsp" %>
<div class="container">
    <div class="jumbotron">
        <h1>Bienvenue dans l'interface de gestion des employ�s !</h1>
        <p>Cette application web est param�tr�e pour communiquer avec une BD MySql accessible � l'adresse <code>http://localhost:3306</code>.</p>
        <ul class="list-group">
            <li class="list-group-item">
                <h4 class="list-group-item-heading">1 - Compter le nombre d'employ�s</h4>
                <p class="list-group-item-text">A c�t� du lien <em>Liste des employ�s</em>, on doit voir appara�tre le nombre d'employ�s. L'appel qui est effectu� est <code>GET /employes/count</code>.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">2 - Afficher un employ�</h4>
                <p class="list-group-item-text">En cliquant <a href="/employes/5">ici</a>, on peut afficher les informations basiques de l'employ� d'identifiant 5 (matricule M00001). On consulte l'URL <code>/employes/5</code>. En cliquant <a href="/employes/0">ici</a>, on essaye d'afficher l'employ� d'identifiant 0 mais on doit obtenir une erreur 404 car il n'existe pas.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">3 - Recherche par matricule</h4>
                <p class="list-group-item-text">Lorsqu'on recherche le matricule <em>C00019</em> dans la barre de recherche, on tombe sur <em>Sarah Renault</em>. L'URL auquel on acc�de est <code>/?matricule=C00019</code>. Lorsqu'on recherche un matricule inexistant commme <em>ABCDEF</em>, on obtient une erreur 404.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">4 - Liste des employ�s</h4>
                <p class="list-group-item-text">En cliquant <a href="/employes?page=0&size=10&sortDirection=ASC&sortProperty=matricule">ici</a>, tous les employ�s sont affich�s, de mani�re pagin�e. Il est possible de changer de page en utilisant les boutons. L'URL utilis� est <code>/employes?page=0&size=10&sortProperty=matricule&sortDirection=ASC</code></p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">5 - Cr�ation d'un Commercial</h4>
                <p class="list-group-item-text">En cliquant <a href="/employes/new/commercial">ici</a> ou via le bouton <em>Nouvel employ�</em>, <em>Commercial</em>, pr�sent dans la liste des employ�s, on acc�de au formulaire de cr�ation d'un commercial. L'appel qui est effectu� est <code>POST /commercials/save</code> avec les donn�es du formulaire qui sont envoy�es.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">6 - Modification d'un Commercial</h4>
                <p class="list-group-item-text">En cliquant <a href="/employes/8">ici</a> ou en consultant les d�tails du commercial de matricule <em>C00002</em> (id 8), il est possible de modifier les informations du commercial d'identifiant 8 qui sont persist�es en base de donn�e lorsqu'on clique sur le bouton <em>Enregistrer</em>. L'URL qui est appel� est <code>POST /commercials/8</code> avec les donn�es du formulaire qui sont envoy�es.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">7 - Suppression d'un Commercial</h4>
                <p class="list-group-item-text">En cliquant <a href="/employes/22">ici</a> ou en consultant les d�tails du commercial de matricule <em>C00018</em> (id 22), il est possible de supprimer ce dernier lorsqu'on clique sur le bouton <em>Supprimer</em>. L'appel qui est effectu� est <code>GET /employes/22/delete</code>.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">8 - Cr�ation, modification et suppression d'un Technicien</h4>
                <p class="list-group-item-text">Faire la m�me chose que les trois questions pr�c�dentes pour les techniciens. Le chemin de l'API est <code>/techniciens</code>.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">9 - Cr�ation, modification et suppression d'un Manager</h4>
                <p class="list-group-item-text">Faire la m�me chose que la question pr�c�dente pour les managers. Le chemin de l'API est <code>/managers</code>.</p>
            </li>
            <li class="list-group-item">
                <h4 class="list-group-item-heading">10 - EDIT BY CLEMENT BEY : Ajouter ou supprimer un technicien dans l'�quipe d'un manager et inversement </h4>
                <p class="list-group-item-text">  Je me suis permis de modifier l�g�rement cet ajout / suppression afin de factoriser un peu le code. En effet,
                j'ai trouv� que faire une m�thode dans chaque controller (supprimer un tec d'une equipe manager et supprimer manager d'une equipe technicien) �tait trop lourd. 
                D'autant plus qu'en base, c'est le technicien qui est le "propri�taire" de cette relation.<br/><br/>
              
                Je propose un mod�le de type : <code>/{id}/removemanager/{requestStartId}</code> ou "id" est l'id du technicien dont on veut enlever le manager et ou 
                "requestStartId" est l'id du technicien / manager dont la supression s'est faite (necessaire pour la redirection qui renvoie � la page d'ou a demarr� la requete).<br/><br/>
                
                Ainsi je n'ai pu impl�menter que 3 m�thodes au lieu de 4.</p>
            </li>
        </ul>
    </div>
</div>
<%@ include file="tags/footer.jsp" %>