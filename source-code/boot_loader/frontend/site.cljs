
(ns boot-loader.frontend.site
    (:require [site.karavancentrum.pages.frontend]
              [site.karavancentrum.modules.api :as site.modules]
              [site.karavancentrum.wrapper     :as site.wrapper]
              [x.app-developer.api             :as developer]
              [x.boot-loader.api               :as boot-loader]))

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
