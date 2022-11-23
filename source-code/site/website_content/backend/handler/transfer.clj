
(ns site.website-content.backend.handler.transfer
    (:require [app.contents.backend.api        :as contents]
              [app.website-content.backend.api :as website-content]
              [x.core.api                      :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-content-f
  ; @param (map) request
  ;
  ; @return (map)
  [request]
  (let [website-content (website-content/get-website-content)]
       (contents/fill-data request website-content)))

(x.core/reg-transfer! ::transfer-website-content!
  {:data-f      transfer-website-content-f
   :target-path [:site :website-content]})
