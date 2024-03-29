# Zeitstempel für die verschiedenen "Meilensteine"

Diese Rekonstruktion habe ich (außerhalb der gemessenen Zeit) vor allem für mich gemacht, um zu verstehen für welchen Teil der Bearbeitung ich wie viel Zeit investiert habe.

- 0:00 - Start (Uhrzeit: 10:58)
- 0:21 - Aufgabenstellung durchdacht, Vorüberlegungen dokumentiert (21 Minuten, Uhrzeit: 11:19)
    - 0:11 - Aufgabe gelesen, nachdegacht, Repo angelegt (11 Minuten, Uhrzeit: 11:09)
    - 0:21 - Designentscheidungen bis dahin aufgeschrieben (10 Minuten, Uhrzeit: 11:19)
- 0:37 - Backend-Projektsetup (16 Minuten, Uhrzeit: 11:35)
    - 0:28 - Spring Boot Projekt angelegt (7 Minuten, Uhrzeit: 11:26)
    - 0:37 - Gradle und JVM-Versionen miteinander kompatibel gemacht (9 Minuten, Uhrzeit: 11:35)
- 1:22 - Frameworkunabhängige Applikationslogik implementiert - Teil 1 (45 Minuten, Uhrzeit: 12:27)
    - 1:08 - Erster use-case Test implementiert (query ohne Antwort) + Datenmodell-Anfänge + Mail zur Anforderungsklärung (41 Minuten, Uhrzeit: 12:13)
        - beinhaltet nicht gezählte Pause (11:52 - 11:59, Gesamtzeit bis dahin: 0:54)
    - 1:22 - Zweiter use-case Test implementiert (query von 1 developer ohne repos) + (14 Minuten, Uhrzeit: 12:27)
- 1:42 - Github Actions CI funktioniert (20 Minuten, Uhrzeit: 12:47)
- 2:48 - Frameworkunabhängige Applikationslogik implementiert - Teil 2 (zusammen 2 Stunden 11 Minuten, Uhrzeit: 14:39)
    - 1:57 - Datenmodell um Repos und Sprachen erweitert (15 Minuten, Uhrzeit: 13:02)
    - 2:29 - Einbauen der Transformation Datenmodell -> Viewmodell in den use-case (32 Minuten, Uhrzeit: 13:34)
    - nicht gezählte Pause (13:34 - 14:20, Gesamtzeit bis dahin: 2:29)
    - 2:48 - Use-Case finalisiert (19 Minuten, Uhrzeit: 14:39)
- 4:24 - Github-API Adapter implementiert (95 Minuten, Uhrzeit: 16:23)
    - 3:09 - Members-Abfrage implementiert (21 Minuten, Uhrzeit 15:00)
    - 4:09 - Erkenntnis: zu viele API-Calls bei integrativem Test - Implementierung einzelner Methoden für reine API-Queries (60 Minuten, Uhrzeit: 16:08)
        - beinhaltet Pause (15:40 - 15:48, Gesamtzeit bis dahin: 3:49)
    - 4:24 - Umbau abgeschlossen, Query-Limit für integrativen Test der Gesamtabfrage (15 Minuten, Uhrzeit: 16:23)
- Unterbrechung bis 22:32 (Gesamtzeit bis dahin: 4:24)
- 4:41 - Console UI Adapter implementiert (17 Minuten, Uhrzeit: 22:50)
- 4:57 - Konsolen-Applikation mit Console UI Adapter und Github-API implementiert (14 Minuten, Uhrzeit: 23:04, Gesamtzeit 2 Minuten hoher als fortlaufend durch aufsummierte Rundungsfehler)
- 5:15 - Security: Github API Token: von "hardcoded & eingecheckt" zu "Umgebungsvariable" (17 Minuten)
- 5:25 - Code review / cleanup (10 Minuten)