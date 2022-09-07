
(ns frontend
    (:require ; App modules
              [app.common.frontend.api]
              [app.home.frontend.api]
              [app.settings.frontend.api]
              [app.site-config.frontend.api]
              [app.storage.frontend.api]
              [app.user.frontend.api]
              [app.views.frontend.api]
              [app.contents.frontend.api]

              ; Site modules
              [site.frontend.api]
              [site.frontend.wrapper :as site-wrapper]

              ; monoset/x
              [x.boot-loader.api :as boot-loader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- site-app
  [ui-structure]
  [site-wrapper/view ui-structure])

(defn- admin-app
  [ui-structure]
  [ui-structure])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn site-start-app!  [] (boot-loader/start-app!  #'site-app))
(defn site-render-app! [] (boot-loader/render-app! #'site-app))

(defn admin-start-app!  [] (boot-loader/start-app!  #'admin-app))
(defn admin-render-app! [] (boot-loader/render-app! #'admin-app))
