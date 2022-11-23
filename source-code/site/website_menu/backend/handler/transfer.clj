
(ns site.website-menu.backend.handler.transfer
    (:require [app.website-menu.backend.api :as website-menu]
              [x.core.api                   :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-menu-f
  ; @param (map) request
  ;
  ; @return (map)
  [request]
  (website-menu/get-website-menu))

(x.core/reg-transfer! ::transfer-website-menu!
  {:data-f      transfer-website-menu-f
   :target-path [:site :website-menu]})
