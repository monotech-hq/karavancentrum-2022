
(ns app.common.backend.prototypes
    (:require [mid-fruits.json   :as json]
              [x.server-user.api :as user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; Az added-document-prototype, updated-document-prototype és duplicated-document-prototype
; függvények a modulok (adatbázisba dokumentumot író) mutation függvényeinek prototípusai.
;
; Amíg funkcionalitásukban ennyire hasonlítanak a moduluk és nem szükséges modulonként,
; vagy mutation függvényenként meghatározni, hogy milyen műveleteket végezzenek a prototípus
; függvények, addig ezek a közös függvények lesznek alkalmazva.

(defn added-document-prototype
  [request namespace document]
  ; - Utolsó modosítás dátumának és a felhasználó azonosítójának aktualizálása
  ; - Hozzáadás dátumának és a felhasználó azonosítójának aktualizálása
  ; - A string típusú értékek vágása
  ; - A kliens-oldali mezők által string típusként tárolt egész számok
  ;   integer típusra alakítása
  (->> document (user/added-document-prototype request namespace)
                (json/trim-values)
                (json/parseint-values)))

(defn updated-document-prototype
  [request namespace document]
  ; - Utolsó modosítás dátumának és a felhasználó azonosítójának aktualizálása
  ; - A string típusú értékek vágása
  ; - A kliens-oldali mezők által string típusként tárolt egész számok
  ;   integer típusra alakítása
  (->> document (user/updated-document-prototype request namespace)
                (json/trim-values)
                (json/parseint-values)))

(defn duplicated-document-prototype
  [request namespace document]
  ; - Utolsó modosítás dátumának és a felhasználó azonosítójának aktualizálása
  ; - A string típusú értékek vágása
  ; - A kliens-oldali mezők által string típusként tárolt egész számok
  ;   integer típusra alakítása
  (->> document (user/duplicated-document-prototype request namespace)
                (json/trim-values)
                (json/parseint-values)))
