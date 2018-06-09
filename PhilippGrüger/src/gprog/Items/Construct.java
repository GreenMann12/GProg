package gprog.Items;

import gprog.ItemStack;

import java.util.ArrayList;

/**
 * Created by Patrick on 29.05.2018.
 */
public abstract class Construct extends Item implements Cloneable {

    private ArrayList<ItemStack> items;

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }

    public Construct clone () {
        return this.clone();
    }
}

/*
Die Idee ist, dass man die Constructs bereits im ConstructMenu erstellt hat und diese dann nur noch clonen und der Welt
hinzufügen muss (platzieren). Wenn diese nun wieder verschwinden sollen, kann man die "abbauen" das heist die Ressourcen
wieder dem inventar hinzufügen, da jedes Construct die Objekte enthält aus dem es besteht. Was nun noch gemacht werden
muss ist das ConstructMenu als Grafik und die selbe Funktion wie das Inventar implementieren.
Also:
Das ConstructMenu rechts an der Seite dauerhaft mit den Constructs anzeigen lassen
Drag and Drop zum erstellen (beim drücken soll die Prüfung laufen die implementiert ist)
Rechtsklick zum "abbauen" (Funktion dazu kann ich dann noch implementieren)


Aus was jedes Construct besteht, kann im Konstructor jeder Construct Klasse geändert werden.

 */
