
(ns project.ui.backend.main
    (:require [app.views.backend.api          :as views]
              [app.website-config.backend.api :as website-config]
              [x.server-ui.api                :as ui]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  ; @param (map) request
  [request]
  (let [website-config (website-config/get-website-config)
        loading-screen (views/loading-screen request)]
       (ui/html (ui/head request website-config)
                (ui/body request {:loading-screen loading-screen}))))
               ;(ui/head request {:css-paths       [{:uri ""}]})
               ;(ui/body request {:plugin-js-paths [{:uri ""}]})
