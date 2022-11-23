
(ns site.website-impressum.backend.handler.transfer
    (:require [app.contents.backend.api                     :as contents]
              [app.website-impressum.backend.handler.config :as handler.config]
              [io.api                                       :as io]
              [x.core.api                                   :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-impressum-f
  ; @param (map) request
  [request]
  (let [website-impressum (io/read-edn-file handler.config/WEBSITE-IMPRESSUM-FILEPATH)]
       (assoc website-impressum :address-data-information  (contents/get-content request (:address-data-information  website-impressum))
                                :contacts-data-information (contents/get-content request (:contacts-data-information website-impressum)))))

(x.core/reg-transfer! ::transfer-website-impressum!
  {:data-f      transfer-website-impressum-f
   :target-path [:site :website-impressum]})
