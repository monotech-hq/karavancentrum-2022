
(ns boot-loader.frontend.site
    (:require ; Monoset modules
              [pathom.api]
              [x.app-developer.api :as developer]
              [x.boot-loader.api   :as boot-loader]

              ; Site modules
              [site.karavancentrum.pages.frontend]
              [site.karavancentrum.modules.api]
              [site.karavancentrum.wrapper :as site.wrapper]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- site-structure
  [ui-structure]
  [:<> [site.wrapper/view ui-structure]
       [developer/magic-button]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-site!  [] (boot-loader/start-app!  #'site-structure))
(defn render-site! [] (boot-loader/render-app! #'site-structure))
