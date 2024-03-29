# Codecentric Challenge - Decision Records

## Vorüberlegungen

- Spring Boot für den Backend-Teil nutzen um das Hinzufügen von Persistenz (Möglicherweise Teil von Aufgabe 1, Stichwort Datenbasis, siehe OP1) und einer REST-API fürs Web-Frontend (Aufgabe 3) zu erleichtern
- Gemeinsames Repo für FE und BE um konsistenten Stand sicherzustellen
- Spring-Boot-freier Application Code für
    - einfacheres Testen der Applikationslogik
    - weniger Einfluss des Github-Datenmodells auf Applikations-Datenmodell
    - einfachere Option auf Technologiewechsel durch klare Trennung (Ansatz: Hexagonal Architecture)
- für Aufgabe 3 vmtl. React + Next, weil ich damit kürzlich etwas Erfahrung gesammelt habe
- Setup von CI mit github Actions, wenn es nicht allzuviel Zeit benötigt
- Bewußt overengineered (für die Aufgabengröße), um über Methodologie und SW-Design reden zu können
- Regelmäßige Commits, um einen besseren Einblick in meine Arbeitsweise zu ermöglichen
- Im Backend TDD als Standardherangehensweise

## Aufgabe 1 - Application Anteil

- Projekt-Setup:
    - Aufgrund Zeitbeschränkung und geringer Komplexität, kein Multi-Module-Setup
    - SpringWeb für REST-APIs
    - H2 DB für einfache und schnelle DB-Demo (falls ich mich zur Implementierung entscheide)
    - downgrade auf JDK 19 um nicht unnötig Zeit in gradle vs JDK 21 zu stecken
- github API manuell erkunden
    - https://api.github.com/orgs/codecentric/members
    - repos_url -> https://api.github.com/users/denniseffing/repos
    - languages_url -> https://api.github.com/repos/denniseffing/istio-chaos-demo/languages
    - OP 2 (s.u.)
- Datenmodell: Mit Hinblick auf die Aufgabenstellung vorerst einfache Gliederung Mitarbeiter -hat-> Repos -hat-> Sprachen
- Use-Case(s): Mit Hinblick auf Aufgabenstellung (mindestens vorerst) Gliederung des UseCase in Query und Show
- Einschub vor Implementierung der echten github API-Abfrage: CI aufsetzen
- Bauchgefühl: Use-Case degeneriert, wenn kein View generiert wird -> Entscheidung ob erst API implementieren, oder erst Use-Case mit Dummy-Daten
- -> zuerst UseCase, da API schon manuell erkundet und wenig Überraschungen erwartet

## Aufgabe 2 - Application Anteil
- OP3 und OP4 entdeckt
- SourceCodeLanguage als record statt enum, weil Liste der Verfügbaren außerhalb unserer Kontrolle
- kein use-case package in application, um Verwirrung durch tiefe Package-Struktur ohne Inhalte zu 
- Test für github abfrage in möglichst wenigen Testcases wegen Test-Laufzeit und api-Nutzung

## Aufgabe 1 - Adapter Anteil
- OP5 entdeckt
- bei naivem Test in (unauthorized) Rate-Limit der API gelaufen -> Struktur des API-Adapters und der Tests anpassen
    - Trennung der eigentlichen Abfragen und der Übersetzung von Abfragen in Objekte des Datenmodells

## Offene Punkte
- OP1: Sollen die Ergebnisse der Github-Abfrage persistiert werden? (Annahme: Nein, weil sonst Rattenschwanz über Caching-Dauer etc folgt)
- OP2: Anforderung klären: Eigene Repos vs. Commits in andere Repos (Annahme: Wie in Aufgabe beschrieben: Repos der Mitarbeiter)
- OP3: Struktur des Application-Datenmodells hinterfragen -> aktuell Github-Struktur übernommen
- OP4: DisplayDeveloperLanguageProficiency.queryDeveloperProfiles private machen? -> gemacht!
- OP5: Ergebnisse der github-requests liefern je 30 einträge -> Pagination oder Limit der Ergebnisse für nicht eingeloggte?

## Should have
- GetCodecentricDeveloperProfilesFromGithub: use profile display name instead of login

## Nice To Have
- Tests in schnell und langsam auftrennen
- Client/Request pooling in GetCodecentricDeveloperProfilesFromGithub
- Spike eines Webfrontends

