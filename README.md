# tpjava

Bonjour à vous,

Vous trouverez dans cette pull request ma solution pour le tp 330. Quelques remarques et questions :
    
1) je me suis permis de changer le lien pour ajouter supprimer manager d'un technicien (et inversement) justification sur la 
page d'index que j'ai mis a jour.
    
2) solution pour le count employes : le nombre d'employe étant affiché dans le header (commun a toutes les jsp), j'ai souhaité 
qu'il soit disponible dans toutes les vues. Plutot que de le faire passer dans chaque méthode de chaque controller (avec l'appel 
en base que cela implique), j'ai préféré le mettre dans le contexte de l'application. Il est initialisé a l'accueil (http://localhost:8080). 
Puis ++ ou -- selon creation / suppression. Y avait-il une solution plus élégante ?