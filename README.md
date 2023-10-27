# Turmite+

A “turmesz” egy 2D Turing-gép (angolul “Turmite”), itt tehát nem egy 1D szalag van, hanem egy 2D rács, amelynek minden cellája 2 állapotú (0 vagy 1). Ebben a programban 2 is lehet, ha akadályt teszünk le. Van egy aktuális pozíció és irány – ez reprezentálható egy hangyával (vagy termesszel). Minden lépésben a hangya az aktuális állapotának és a cella tartalmának megfelelően csinálja a következőket:

    Fordul 90 fok(geometriától függően) valamilyen sokszorosával → L[eft], R[ight], N[o turn], U[-turn]
    Az aktuális cellába beleír valamit (0 vagy 1)
    Előre lép egyet
    Átmegy egy másik állapotba

Egy lehetséges hangya mozgási minta pl.:  
[állapot-cellaérték-irány-újérték-újállapot]  
0-0-R-1-0  
0-1-R-1-1  
1-0-N-0-0  
1-1-N-0-1  
Tehát 0-s állapotban, ha 0-s cellán áll, akkor jobbra fordul, 1-est ír, és marad 0-s állapotban. Ha 1-es cellán áll, akkor jobbra fordul és átmegy 1-es állapotba. Amikor 1-es állapotban van, ha 0-át talál, akkor átmegy 0-s állapotba; ha 1-et talál, akkor azt 0-ra javítja, de marad 1-es állapotban.

A rács egy 2D-s tömbben lesz megvalósítva. A játékállást el lehet menteni fájlba. Ehhez a turmesz példányokat és a pálya értékeit kell lementeni.
A pálya értékei az alábbi módon lesznek elmentve:  
[cella érték],[cella érték],...  
[cella érték],[cella érték],...  
.  
.  
.  

A turmeszek pedig az alábbi módon lesznek(jelenlegi pozíciója és a hozzá tartozó mozgási minta):
[pozíció x koordinátája],[pozíció y koordinátája],[állapot],[cellaérték],[irány],[újérték],[újállapot]

A GUI a Java Swing környezetével lesz megvalósítva. Négyzetrácsos pályán lesz látható a hangyák mozgása. Kattintással a pályán le lehet tenni akadályokat és több hangyát is le lehet tenni a pályán.
Az akadályokon keresztül értelemszerűen nem mehet át a hangya. Ha két hangya találkozik egy cellában, akkor eltérítik egymást az ellenkező irányba.
A szimuláció sebességét lehet állítani a GUI-n belül és meg is lehet állítani/újraindítani.
