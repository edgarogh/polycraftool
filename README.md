# Polycraftool

## Installation

Polycraftool est un mod Fabric. Vous pouvez [installer Fabric manuellement](https://fabricmc.net/use/installer/) ou utiliser un launcher alternatif au launcher officiel, qui fait ça pour vous, comme [Prism Launcher](https://prismlauncher.org/).

Vous devez installer deux dépendances requises, [Fabric API](https://modrinth.com/mod/fabric-api) et [Cloth Config](https://modrinth.com/mod/cloth-config) (beaucoup d'autres mods en dépendent).

C'est aussi l'occasion d'installer une petite selection de mods qui n'ont rien à voir, en plus, si vous le souhaitez :
  * **[Mod menu](https://modrinth.com/mod/modmenu) (très recommandé)** pour pouvoir configurer graphiquement vos mods (y compris Polycraftool)
  * [Bobby](https://modrinth.com/mod/bobby) pour avoir une grande distance de rendu simulée
  * [Sodium](https://modrinth.com/mod/sodium), [Lithium](https://modrinth.com/mod/lithium), [Indium](https://modrinth.com/mod/indium), [Enhanced Block Entities](https://modrinth.com/mod/ebe), [LazyDFU](https://modrinth.com/mod/lazydfu) et [Starlight](https://modrinth.com/mod/starlight) pour des optimisations diverses
  * [Continuity](https://modrinth.com/mod/continuity), [Falling Leaves](https://modrinth.com/mod/fallingleaves), [Visuality](https://modrinth.com/mod/visuality), [LambdaBetterGrass](https://modrinth.com/mod/lambdabettergrass), [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) pour des améliorations graphiques diverses
  * [Iris](https://modrinth.com/mod/iris) pour charger des shaders (j'aime bien [Complementary](https://www.complementary.dev/shaders-v4/))
  * [Puzzle](https://modrinth.com/mod/puzzle) pour simplifier les menus d'options graphiques de tous les mods ci-dessus
  * [Xaero's Minimap](https://www.curseforge.com/minecraft/mc-mods/xaeros-minimap)

## Fonctionnalités

### Connexion

  * Auto-login : Le mod propose d'enregistrer votre mot de passe au premier `/login`, et vous connectera alors automatiquement les fois futures.
  * Le mod bloque l'avertissement (le message toast en haut à droite) comme quoi les messages ne sont pas signés (uniquement sur Polycraft)

![capture d'écran de l'auto-login](https://cdn.discordapp.com/attachments/762636492421857300/1040378574584168538/image.png)

### Raccourcis claviers (configurables)

  * Ouvrir le chat avec `/tm ` prérempli (Par défaut <kbd>y</kbd>, juste à côté de <kbd>t</kbd>)
  * Téléportation au `/home`
  * `/sethome`
  * Téléportation à chaque ville (x15)

![](https://cdn.discordapp.com/attachments/762636492421857300/1040381284016463924/image.png)

### Rendu des pseudos préfixés

  * Possibilité de remplacer les noms des joueurs `[Grenoble] MrAnimaM` par <code><img alt="Logo polytech" title="Logo polytech" src="src/main/resources/assets/polycraftool/textures/font/polytech_logo.png" style="display: inline; height: 1em"> MrAnimaM</code> pour épurer l'interface, si les couleurs vous suffisent pour identifier les villes
  * Possibilité de remplacer les couleurs des pseudos pour mettre [les vraies couleurs Pantone officielles du réseau](https://gist.github.com/edgarogh/74c543f32405e3625ab26641a960b3dc#file-polytech_couleurs-csv) à la place
  * Ces deux fonctionnalités marchent très bien ensemble et sont activées par défaut

![capture d'écran du chat modifié](https://media.discordapp.net/attachments/762636492421857300/1040379054114734160/image.png)
![capture d'écran du menu tab modifié](https://media.discordapp.net/attachments/762636492421857300/1040378890394284042/image.png)

_Effectif dans : chat (messages normaux, `/tm`, déblocage de succès), menu tab, étiquette au-dessus des joueurs_

### Fonctionnalités futures

  * Affichage des limites des villes visuellement dans le monde, un peu comme des bordures de chunks (et désactivation du message "vous entrez/sortez de \<ville\>")
  * Messages quand qqun de notre ville se connecte/déconnecte ?
  * Je suis ouvert aux suggestions et retours divers
  * Je peux implémenter des fonctionnalités sur-mesure pour améliorer des trucs construits par les joueur·euses. La condition est que ça ne doit pas fournir d'avantages autres que visuels, et ce, de façon mesurée. Ce mod est un mod de quality-of-life, pas un cheat.
    * Par exemple, si une ville construit un jeu de spleef, je peux faire s'afficher un HUD avec les noms joueur·euses restant·es, activé uniquement lorsqu'on est dans l'arène
    * Pour un jeu de course, il peut s'agir d'une minimap type mario kart et/ou d'un classement en live (dans les limites de ce qui peut être implémenté côté client ; ici en pratique je risque d'être limité par la distance d'affichage des entités)
    * _et ça n'est pas limité à des jeux..._
