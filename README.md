LabelA Android Application - Lucasfilm Application

Description
The aim of the application is displaying information about some of the most remarkable characters of the Starwars saga.

Packages
- Controller - Contains classes which used to get content data of application.
- Fragments - Contains classes which used to keep and move between pages.
- Models - Contains entity classes
- Presenter - Contains adapter classes which used to provide dynamic views.
- Utils - Contains helper 

Actions
CharactersFragment - Represents characters in circumsitances of network connection is available or not.If it is, characters are retrieved from given api. Otherwise, characters are received from cache via using Sharedpreferences. By clicking each item separately, detailed information of characters will be shown in CharacterDetailedFragment. Addition to that sorting by birthday and name were also implemented in menu which is placed in at the right top of the page.

CharacterDetailedFragment- Represents information of a character. Information contains films, vehicles and homeworld of the character. For getting those information related urls are used as api service.

FavoriteCharacterFragment- Represents favorite characters of user who signed those items at the begining. Favorite characters will be taken from cache and it is being updated if there is any update in favorites list.

Completed facilites
● Get a list of the characters from http://swapi.co/api/people API. (For each requests 10 result are receiving. Paging algorithm was used to provide partial loadings. But somehow after retrieving 2. page, 3. page was not received from the same api which is used to get 2. page)
● Show a list of characters sorted by default on name.
● Add one button that allows the user to change the sort method between:
	○ Character name.
	○ Character birth year.
● Add one button to each cell to mark characters as favourite.
● If you press the cell you have to show another view with the character detailed
information:
	○ Films.
	○ Vehicles.
	○ Homeworld.
●  Your GUI interface should contain a text box to search characters by name or homeworld.
Once the user stop typing, your list will be updated with users that matches with the search
term.
●  Create another view, similar to the first one, to show users marked as favourite or add it as
another filter in the main view.
●  In case of any network issue, Sharedpreferences was used to get characters data.
●  Prefered VCS is Github. Related github url:
https://github.com/SinemKalay/LabelA_Lucasfilm

