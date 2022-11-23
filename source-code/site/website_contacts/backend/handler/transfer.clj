
(ns site.website-contacts.backend.handler.transfer
    (:require [app.contents.backend.api         :as contents]
              [app.website-contacts.backend.api :as website-contacts]
              [x.core.api                       :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-contacts-f
  ; @param (map) request
  ;
  ; @return (map)
  [request]
  (let [website-contacts (website-contacts/get-website-contacts)]
       (contents/fill-data request website-contacts)))

(x.core/reg-transfer! ::transfer-website-contacts!
  {:data-f      transfer-website-contacts-f
   :target-path [:site :website-contacts]})
