
(ns site.common.frontend.fragment-sensor.helpers
    (:require [mid-fruits.string :as string]
              [re-frame.api      :as r]
              [x.app-environment.api :as environment]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-fragment
  ; @param (keyword) fragment-id
  ;
  ; @usage
  ;  (remove-fragment :my-fragment)
  ;  =>
  ;  "/my-route"
  ;
  ; @return (string)
  [_]
  (let [route-string @(r/subscribe [:router/get-current-route-string])]
       (if-let [fragment (string/after-last-occurence route-string "#")]
               (string/before-last-occurence route-string "#")
               (string/not-ends-with!        route-string "#"))))

(defn swap-fragment
  ; @param (keyword) fragment-id
  ;
  ; @usage
  ;  (swap-fragment :my-fragment)
  ;  =>
  ;  "/my-route#my-fragment"
  ;
  ; @return (string)
  [fragment-id]
  (let [route-string @(r/subscribe [:router/get-current-route-string])]
       (if-let [fragment (string/after-last-occurence route-string "#")]
               (-> route-string (string/before-last-occurence "#")
                                (str "#" (name fragment-id)))
               (-> route-string (string/not-ends-with! "#")
                                (str "#" (name fragment-id))))))

(defn intersect-f
  ; @param (keyword) fragment-id
  ;
  ; @return (function)
  [fragment-id]
  ; BUG#0561
  ; Ha az egyes route-fragment navigátorok használata után a felhasználó az oldal
  ; más részére görget, majd újra ugyanazt a route-fragment navigátort szeretné használni,
  ; ami az utolsó használat óta az útvonal része, akkor az útvonal-kezelő nem érzékel
  ; változást, ezért nem történik meg a navigálás!
  ;
  ; Megoldás 1.
  ; Az egyes szekciók, a viewport-ba kerülésük pillanatában aktualizálják az útvonalat,
  ; a szekcióhoz tartozó route-fragment értékével
  ;
  ; Megoldás 2.
  ; Az egyes szekciók, a viewport-ba kerülésük pillanatában eltávolítják az útvonalból
  ; a route-fragment értékét
  (fn [intersecting?]
      (if intersecting? (r/dispatch [:router/swap-to! (remove-fragment fragment-id)]))))
