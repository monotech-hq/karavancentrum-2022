
(ns boot-loader.frontend.site
    (:require ; Monoset modules
              [pathom.api]
              [developer-tools.api :as developer-tools]
              [x.boot-loader.api   :as boot-loader]

              ; Site modules
              [site.karavancentrum.pages.frontend]
              [site.karavancentrum.modules.api]
              [site.karavancentrum.wrapper.views :as site.wrapper.views]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- site-structure
  [ui-structure]
  [:<> [site.wrapper.views/view ui-structure]
       [developer-tools/magic-button]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-site!  [] (boot-loader/start-app!  #'site-structure))
(defn render-site! [] (boot-loader/render-app! #'site-structure))
