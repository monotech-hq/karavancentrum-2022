
(ns project.ui.backend.main
    (:require [app.website-config.backend.api    :as website-config]
              [project.ui.backend.loading-screen :as loading-screen]
              [x.server-ui.api                   :as ui]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  ; @param (map) request
  [request]
  (let [website-config (website-config/get-website-config)
        loading-screen (loading-screen/view request)]
       (ui/html (ui/head request website-config)
                (ui/body request {:loading-screen loading-screen}))))
