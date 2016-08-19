# PremieryFilmoweApp
Projekt aplikacji z premierami filmowymi. Serwer wysyła przetworzonego jsona do aplikacji.

## 1.	Założenia projektowe 
Celem projektu było stworzenie aplikacji mobilnej umożliwiającej sprawdzanie informacji na temat premierowych filmów oraz dopiero nadchodzących premier. Użytkownik aplikacji ma możliwość sprawdzenie daty danego filmu, krótkiego opisu, sprawdzenie podstawowych informacji takich jak kraj produkcji, reżyser oraz możliwość obejrzenia zwiastuna danej produkcji. 

## 2.	Diagram przypadków użycia 
Aplikacja została napisana dla systemu Android przy użyciu środowiska Android Studio. Poza aplikacją zostało utworzone api w php i udostępnione na darmowym serwerze. Zadanie API jest parsowanie xml do obiketu Jsona, który zostaje odebrany przez aplikację mobilną, a następnie odpowiednio przetworzony. API zostało napisane przy użyciu biblioteki MVC PHP -> CodeIgniter.
<p align="center">
<img src="https://cloud.githubusercontent.com/assets/9364661/17813755/db3eccd0-662c-11e6-8fb8-1f887787f1fa.jpg" width="700" />
</p>
## 3.	Opis klas projektu. 
![2](https://cloud.githubusercontent.com/assets/9364661/17813746/cd9e7328-662c-11e6-88c1-0ec435ec06e7.jpg)

*	**AsyncTaskParseJson** – klasa odpowiedzialna za asynchroniczne parsowanie JSONA z serwera.
*	**InfoFragment** – klasa odpowiedzialna za fragment, który wyświetla informacje na temat autora aplikacji.
*	**JSONParser** – klasa odpowiedzialna za parsowanie JSONa.
*	**MainActivity** – główna aktywność aplikacji.
*	**MovieObject** – klasa opisująca pojedynczy obiekt filmu. 
*	**MovieObjectAcitivity** – aktywność odpowiedzialna za pojedynczy film.
*	**PremieryFragment** – fragment, który wyświetla listę premier filmowych.
*	**PremieryObjectsAdapter** – adapter aplikacji odpowiedzialny za powiazanie danych.

## 4.	Opis aplikacji.
<p align="center">
 <img src="https://cloud.githubusercontent.com/assets/9364661/17813862/67b1a08e-662d-11e6-9128-133ca6eb9b45.jpg" width="300" /></br>
 Rys. Główne menu aplikacji.
</p>
* Na obrazku poniżej znajduje się strona główna aplikacji. Składa się z listy premier filmowych, które zostały pobrane z sieci przez odpowiednie API napisane w PHP.
<p align="center">
 <img src="https://cloud.githubusercontent.com/assets/9364661/17814115/86d928fa-662e-11e6-8b10-dc8d44487624.jpg" width="300" /></br>
</p>
* poniżej na obrazkach zaprezentowany jest widok informacji na temat konkretnego filmu oraz ekran zwiastuna. Użytkownik może sprawdzić podstawowe informacje na temat filmu oraz przejść do zwiastuna.
<p align="center">
 <img src="https://cloud.githubusercontent.com/assets/9364661/17814158/bdde4632-662e-11e6-95d5-3e452b816841.jpg" width="300" />
  <img src="https://cloud.githubusercontent.com/assets/9364661/17814160/bea49a1c-662e-11e6-90bb-96cf713a41e6.jpg" width="300" />
</p>
<p align="center">
 <img src="https://cloud.githubusercontent.com/assets/9364661/17814162/bff6a626-662e-11e6-8fdd-8c30166f3264.jpg" width="600" />
</p>

* Warto dodać, że w aplikacji została użyta specjalna biblioteka `Pallete`, która umożliwia pobranie wiodących kolorów z obrazka. Jak widać powyżej, ta funkcjonalność biblioteki została użyta od odpowiedniego kolorowania informacji na temat filmów przy użyciu kolorów z danego plakatu. Wykorzystana została także biblioteka `Glide` do asynchronicznego wczytywania plakatów.
<p align="center">
 <img src="https://cloud.githubusercontent.com/assets/9364661/17814406/0e855d7c-6630-11e6-9895-bea6800613cf.jpg" width="200" />
  <img src="https://cloud.githubusercontent.com/assets/9364661/17814407/10abc992-6630-11e6-87d7-d716a4412b4a.jpg" width="200" />
  <img src="https://cloud.githubusercontent.com/assets/9364661/17814409/11d4be50-6630-11e6-92cd-55dc525aec1c.jpg" width="200" />
</p>

## 5.	Wnioski
Założenia projektu zostały w pełni spełnione. Udało się stworzyć aplikację na system Android, która pobiera dane z serwera przy użyciu odpowiednio napisanego API w PHP. 
