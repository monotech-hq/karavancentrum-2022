
(ns app.contents.frontend.selector.helpers)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn import-id-f
  ; @param (map) n
  ;  {:content/id (string)}
  ;
  ; @example
  ;  (selector.helpers/import-id-f {:content/id "my-item"})
  ;  =>
  ;  "my-item"
  ;
  ; @return (string)
  [n]
  (:content/id n))

(defn export-id-f
  ; @param (string) n
  ;
  ; @example
  ;  (selector.helpers/export-id-f "my-item")
  ;  =>
  ;  {:content/id "my-item"}
  ;
  ; @return (map)
  ;  {:content/id (string)}
  [n]
  {:content/id n})
