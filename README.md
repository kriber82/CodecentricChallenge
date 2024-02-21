# CodecentricChallenge

In diesem Repo ist die Bearbeitung meiner Codecentric-Aufgabe (siehe Aufgabe.pdf) hinterlegt.

Ich habe bei ca. 4 Stunden einen Commit gemacht (steht im Commit-Kommentar), war aber mit Zustand des Codes und dem Bearbeitungsstand der Aufgabe zu diesem Zeitpunkt nicht zufrieden. Deshalb habe ich noch etwas weiter gearbeitet.

Ich habe mich bewusst zum Overengineering (in Verhältnis zur Problem- und Lösungsgröße) entschieden, um einen besseren Eindruck meiner Arbeitsweise in realistisch großen Projekten zu vermitteln. Die so in SW-Design, Testbarkeit und Infrastruktur gesteckte Zeit hat am Ende zur rechtzeitigen Fertigstellung der verpflichtenden Aufgabenteile gefehlt.

Die Struktur des Backend-Projekts (CodecentricChallengeBackend) orientiert sich an der Hexagonalen Architektur: Der Use-Case mit seinen Ports und Datenklassen ist im ...application Package und seinen Unterpackages hinterlegt. Adapter für die github API und eine Konsolen UI liegen in den Packages (...adapters.githubApi und ...adapters.consoleUi)

Die Bearbeitung der Aufgabe 3 habe ich aufgrund der bereits aufgebrauchten Zeit bisher nicht begonnen.

Interessante Orte im Repo:
- Aufgabe.pdf beinhaltet die Aufgabenstellung
- DecisionRecords.md beinhaltet Aufzeichnungen über getroffene Entscheidungen und offene Punkte
- CodecentricChallengeBackend beinhaltet ein Spring Boot Projekt, das die Aufgaben 1 und 2 löst
- Timestamps.md beinhaltet eine Aufschlüsselung der Zeiten, die ich für verschiedene Teile der Aufgabe gebraucht habe (außerhalb der Bearbeitungszeit erstellt)

