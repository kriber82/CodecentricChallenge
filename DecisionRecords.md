# Codecentric Challenge - Decision Records

## Vorüberlegungen

- Spring Boot für den Backend-Teil nutzen um das Hinzufügen von Persistenz (Möglicherweise Teil von Aufgabe 1, Stichwort Datenbasis) und einer REST-API fürs Web-Frontend (Aufgabe 3) zu erleichtern
- Gemeinsames Repo für FE und BE um Konsistenten Stand sicherzustellen
- Spring-Boot-freier Application Code für einfacheres Testen und einfachere Option auf Technologiewechsel (Hexagonal Architecture light)
- für Aufgabe 3 vmtl. React + Next, weil ich damit kürzlich etwas Erfahrung gesammelt habe
- Setup von CI mit github Actions, wenn es nicht allzuviel Zeit benötigt

## Aufgabe 1

- Aufgrund Zeitbeschränkung und geringer Komplexität, kein Multi-Module-Setup
- SpringWeb für REST-APIs
- H2 DB für einfache und schnelle DB-Demo (falls ich mich zur Implementierung entscheide)
- downgrade auf JDK 19 um nicht unnötig Zeit in gradle vs JDK 21 zu stecken