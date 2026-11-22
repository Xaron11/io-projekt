# Projekt IO: JSON Tools

## Opis

Aplikacja dla programistów, którzy potrzebują przeformatować lub filtrować struktury danych zapisane w formacie JSON a także porównać ze soba struktury. JSON tools pozwala zarówno na zminifikowanie niezminifikowanej reprezentacji JSON, a także na operację odwrotną (z dodaniem wszelkich odstępów i nowych linii). Aplikacja będzie dostępna poprzez GUI, a także jako zdalne API, dzieki czemu można ją zintegrować z istniejącymi narzędziami.

## Zakres projektu

Termin realizacji Sprintu \#1: 17 listopada do 15 grudnia (4 tygodnie)

Zasady punktowania sprintów (DoD): https://docs.google.com/spreadsheets/d/e/2PACX-1vTn6j3M8pmGEzrsQk8mXse7lVHUdhYWkfxbkQiYI23rBtwM4N3bWw0qtupW-gesfCkcYasnZ-eEXl-F/pubhtml

Product Backlog (Arkusz kalkulacyjny): https://docs.google.com/spreadsheets/d/e/2PACX-1vTn6j3M8pmGEzrsQk8mXse7lVHUdhYWkfxbkQiYI23rBtwM4N3bWw0qtupW-gesfCkcYasnZ-eEXl-F/pubhtml

Product Backlog i Sprint Backlog (GitHub Project): https://github.com/users/Xaron11/projects/6

Daily Scrum: wtorki, piątki i niedziele ok. godz. 11:00 (czas trwania: 15 minut)

## Zespół (4 osoby, gr. LAB 10)

- Scrum Master: Dawid Kaczmarzyk 164084 (*Xaron11*)
- Proxy Product Owner (opcjonalnie): Piotr Czajka 164036 (*Czajson34*)
- Deweloper: Damian Golubski 164002 (*Lokej23*)
- Deweloper: Bartosz Borucki 164023 (*Bartecki5*)

### Capacity zespołu

Pracochłonność: 27h

Przewidywany czas/capacity: 64h

**Capacity tygodniowo:**

- Dawid: 4h
- Piotr: 4h
- Damian: 4h
- Bartosz: 4h

## Uruchomienie

### Wymagania

- JDK 11
- Maven 3

Alternatywnie z Nixem: `nix develop` albo `direnv allow` daje gotowe środowisko deweloperskie.

### Budowanie i uruchamianie

```sh
mvn spring-boot:run
```

lub

```sh
mvn package
java -jar target/json-tools-0.1.0-SNAPSHOT.jar
```

Aplikacja startuje na porcie 8080 (inny port: `--server.port=18080`).

### Testy

```sh
mvn test
```

### Dokumentacja (Javadoc)

Dokumentacja generuje się automatycznie przy każdym budowaniu (`mvn package`, `mvn verify`):

- `target/apidocs/index.html` - dokumentacja HTML
- `target/json-tools-<wersja>-javadoc.jar` - spakowana dokumentacja

Błędne komentarze Javadoc przerywają build.

### Hooki gita (pre-commit)

Jednorazowo po sklonowaniu repo (`pre-commit` jest w środowisku Nix, bez Nixa: `uv tool install pre-commit`):

```sh
pre-commit install
```

### Wydania (releases)

Między wydaniami wersja w `pom.xml` ma sufiks `-SNAPSHOT` (np. `0.1.0-SNAPSHOT`). Tag musi odpowiadać wersji w `pom.xml`, więc najpierw ustawiamy wersję, potem tagujemy:

```sh
git switch main && git pull
mvn versions:set -DnewVersion=0.1.0 -DgenerateBackupPoms=false
git commit -am "chore: release 0.1.0"
git tag -a v0.1.0 -m "Release 0.1.0"
git push origin main v0.1.0
```

Workflow `Release` sprawdza, czy tag zgadza się z wersją w `pom.xml`, buduje projekt i tworzy GitHub Release z plikami `json-tools-0.1.0.jar` i `json-tools-0.1.0-javadoc.zip` (dokumentacja HTML).

Po wydaniu przechodzimy na kolejną wersję rozwojową:

```sh
mvn versions:set -DnewVersion=0.2.0-SNAPSHOT -DgenerateBackupPoms=false
git commit -am "chore: prepare 0.2.0-SNAPSHOT"
git push
```

### Przykładowe zapytania

curl:

```sh
curl -X POST 'localhost:8080/api/json?transforms=minify' \
     -H 'Content-Type: application/json' \
     -d '{"a": 1, "b": [1, 2]}'
```

HTTPie:

```sh
http POST localhost:8080/api/json transforms==minify a:=1 b:='[1, 2]'
http POST localhost:8080/api/json transforms==minify < data.json
```

Kilka transformacji podaje się po przecinku: `transforms==minify,sort`.
