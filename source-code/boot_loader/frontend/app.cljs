
(ns boot-loader.frontend.app
    (:require ; Monoset modules
              [pathom.api]
              [developer-tools.api :as developer-tools]
              [x.boot-loader.api   :as x.boot-loader]

              ; App modules
             ;[app.calendar.frontend.api]
              [app.common.frontend.api]
              [app.contents.frontend.api]
              [app.home.frontend.api]
              [app.pages.frontend.api]
              [app.rental-vehicles.frontend.api]
              [app.settings.frontend.api]
              [app.storage.frontend.api]
              [app.user.frontend.api]
              [app.views.frontend.api]
              [app.website-config.frontend.api]
              [app.website-content.frontend.api]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- app-structure
  [ui-structure]
  [:<> [ui-structure]
       [developer-tools/magic-button]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-app!  [] (x.boot-loader/start-app!  #'app-structure))
(defn render-app! [] (x.boot-loader/render-app! #'app-structure))
