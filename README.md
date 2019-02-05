# Crypt of the JavaDancer

Jeu de type rogue-like/rythme inspiré du célèbre jeu Crypt of the NecroDancer.

Le but était ici de générer des graphes correspondant à la carte (quadrillage du niveau) pour utiliser des algorithmes tels que Dijkstra ou A★ pour ensuite réaliser des intelligences artificielles capables d'effectuer des actions telles que le fait de se diriger vers la sortie en emprunter le plus court chemin, ramasser tous les diamants de la carte ou encore tuer tous les monstres le plus vite possible.

IDE : Netbeans 8.2

Réalisé en 2018 dans le cadre du module M2201 "Graphes et langages" - S2 DUT Informatique à l'IUT de Dijon

## Installation

1. [Télécharger l'archive](http://skydefr.com/projets/crypt_of_the_javadancer/Crypt_of_the_JavaDancer.zip)
2. Extraire les fichiers dans un dossier
3. Vérifier que le fichier __level1.txt__ se situe dans le même dossier que le fichier __Crypt of the JavaDancer.jar__
4. Depuis l'invite de commandes, taper : ```bash
java -jar "Crypt of the JavaDancer.jar"
```

## Captures d'écran

### Pelle avancée
![Pelle](https://raw.githubusercontent.com/SkydeFR/Crypt_of_the_JavaDancer/master/screenshots/pelle.png)

### Diamant
![Diamant](https://raw.githubusercontent.com/SkydeFR/Crypt_of_the_JavaDancer/master/screenshots/diamant.png)

### Utilisation de la pelle avancée
![Action](https://raw.githubusercontent.com/SkydeFR/Crypt_of_the_JavaDancer/master/screenshots/action.png)

### Génération du plus court chemin vers le prochain diamant
![chemin](https://raw.githubusercontent.com/SkydeFR/Crypt_of_the_JavaDancer/master/screenshots/chemin.png)

### Sortie : fin de la partie
![sortie](https://raw.githubusercontent.com/SkydeFR/Crypt_of_the_JavaDancer/master/screenshots/sortie.png)
