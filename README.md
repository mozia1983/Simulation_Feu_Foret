# Simulation_Feu_Foret
La première étape 'dossier : first step' consiste à programmer une simple page web où la solution du problème est implémentée dans un script.Une fois la grille changera de logique en introduisant de nouveaux comportement ou de nouveaux types de cellules cette approche devient difficile à gérer.

La deuxième étape 'dossier : second step' consiste à transférer la partie logique de la solution vers une structure orientée objet implémentée avec java.
La partie algorithmie sera confiée à une classe Grid qui manipule l'état des objets appelés cellules. Le resultat sera affiché dans la console.

Mon approche pour la troisième étape 'non fournie' sera de communiquer les résultats à un tier front end développé en vue où j'estime que chaque cellule sera une composante qui adaptera son rendu à chaque étape de la propagation sans avoir à adapter tout le rendu de la grille.

Note :
les paramètres de la simulation sont stockés dans le fichier ''simulation_parameters.json''.
la librairie gson version 2.11.0 a été utilisée pour parser ce fichier.
https://mvnrepository.com/artifact/com.google.code.gson/gson
