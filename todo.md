
vehicle types:
  - "Alkóvos Lakóautó"       - :alcove
  - "Részintegrált Lakóautó" - :semi-integrated
  - "Kempingbusz"            - :caravan
  - "Lakókocsi"              - :van
  - "Utánfutó"               - :trailer

on-app-init: check vehicle type if empty dont display the filter button


kérdések:
  1.  a css file-k hozzá adása route alapján ne js file alapján. (ez lehetővé tenné hogy oldalakra specifikus css filekat írjunk)
  2.  mongodb functionben projection pl.: get-document-by-id, get-document-by-query  


vehicles:
  - display-button (megjelnejen az oldalon) vehicle/visibility? :public :private 
  - content-picker
link
  "berelheto-" (normalize vehicle-name) "-" constraction-year
