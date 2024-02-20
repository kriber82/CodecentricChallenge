# Codecentric Challenge - Decision Records

## Vorüberlegungen

- Spring Boot für den Backend-Teil nutzen um das Hinzufügen von Persistenz (Möglicherweise Teil von Aufgabe 1, Stichwort Datenbasis, siehe OP1) und einer REST-API fürs Web-Frontend (Aufgabe 3) zu erleichtern
- Gemeinsames Repo für FE und BE um Konsistenten Stand sicherzustellen
- Spring-Boot-freier Application Code für einfacheres Testen und einfachere Option auf Technologiewechsel (Hexagonal Architecture light)
- für Aufgabe 3 vmtl. React + Next, weil ich damit kürzlich etwas Erfahrung gesammelt habe
- Setup von CI mit github Actions, wenn es nicht allzuviel Zeit benötigt

## Aufgabe 1

- Aufgrund Zeitbeschränkung und geringer Komplexität, kein Multi-Module-Setup
- SpringWeb für REST-APIs
- H2 DB für einfache und schnelle DB-Demo (falls ich mich zur Implementierung entscheide)
- downgrade auf JDK 19 um nicht unnötig Zeit in gradle vs JDK 21 zu stecken
- github API discovery
    - https://api.github.com/orgs/codecentric/members
    - repos_url -> https://api.github.com/users/denniseffing/repos
    - languages_url -> https://api.github.com/repos/denniseffing/istio-chaos-demo/languages
    - OP 2 (s.u.)
- Mit Hinblick auf die Aufgabenstellung vorerst einfache Gliederung Mitarbeiter -hat-> Repos -hat-> Sprachen

## Offene Punkte
    - OP1: Sollen die Ergebnisse der Github-Abfrage persistiert werden? (Annahme: Nein, weil sonst Rattenschwanz über Caching-Dauer etc folgt)
    - OP2: Anforderung klären: Eigene Repos vs. Commits in andere Repos (Annahme: Wie in Aufgabe beschrieben: Repos der Mitarbeiter)
