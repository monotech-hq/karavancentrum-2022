
(ns boot-loader.frontend.app
    (:require [app.common.frontend.api]
              [app.contents.frontend.api]
              [app.home.frontend.api]
              [app.pages.frontend.api]
              [app.settings.frontend.api]
              [app.storage.frontend.api]
              [app.user.frontend.api]
              [app.vehicles.frontend.api]
              [app.views.frontend.api]
              [app.website-config.frontend.api]
              [app.website-content.frontend.api]

              ; *
              [x.boot-loader.api :as boot-loader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- app-structure
  [ui-structure]
  [ui-structure])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-app!  [] (boot-loader/start-app!  #'app-structure))
(defn render-app! [] (boot-loader/render-app! #'app-structure))
